package com.pratham.project.fileio.ui.home

import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.data.local.dao.FollowersDao
import com.pratham.project.fileio.data.local.dao.FollowingsDao
import com.pratham.project.fileio.data.local.dao.UsernameDao
import com.pratham.project.fileio.data.local.models.FollowersDifferenceModel
import com.pratham.project.fileio.data.local.models.UserXXX
import com.pratham.project.fileio.data.remote.InstagramAPICalls
import com.pratham.project.fileio.data.remote.models.User
import com.pratham.project.fileio.data.remote.models.UserXX
import com.pratham.project.fileio.data.utils.safeApiCall
import com.pratham.project.fileio.data.utils.toLocalModel
import com.pratham.project.fileio.data.utils.toUserXX

class HomeRepository(
        private val instagramAPICalls: InstagramAPICalls,
        private val prefsManager: PreferenceManager,
        private val usernameDao: UsernameDao,
        private val followersDao: FollowersDao,
        private val followingsDao: FollowingsDao,
) {

    suspend fun getUserDetails() = safeApiCall { instagramAPICalls.getUserDetails(prefsManager.userName) }

    suspend fun allowUserDetails() = safeApiCall { instagramAPICalls.allowUserEdit() }

    suspend fun dropAllFollowers() = followersDao.deleteAll()

    suspend fun dropAllFollowings() = followingsDao.deleteAll()

    suspend fun getAllFollowers(maxId: String?) = safeApiCall { instagramAPICalls.getAllFollowers(prefsManager.userProfileId, maxId = maxId) }

    suspend fun getAllFollowings(maxId: String?) = safeApiCall { instagramAPICalls.getAllFollowings(prefsManager.userProfileId, maxId = maxId) }

    suspend fun getLikesFromFeeds() = safeApiCall { instagramAPICalls.getLikesFromFeeds() }

    suspend fun getUserFeeds() = safeApiCall { instagramAPICalls.getUserFeed(prefsManager.userProfileId) }

    suspend fun addFollowersToLocal(followersList: List<UserXX>?){
        followersList?.forEach { it.connectedToUserPk = prefsManager.userProfileId }
        followersList?.let { followersDao.insertAll(it) }
    }

    suspend fun addFollowingsToLocal(followingList: List<UserXXX>?){
        followingList?.let { followingsDao.insertAll(it) }
    }

    suspend fun addUserToLocal(userItem: User?){
        userItem.toLocalModel(prefsManager)?.let { usernameDao.insert(it) }
    }

    suspend fun getDifferenceOfNewFollowers(newFollowers: List<UserXX>?): FollowersDifferenceModel{
        if (newFollowers.isNullOrEmpty()){
            return FollowersDifferenceModel(0,0, emptyList(), emptyList())
        }
        val previousFollowers = followersDao.getAllFollowers()

        val sum = previousFollowers + newFollowers
        val groupBy = sum.groupBy { it.pk }
        val nonCommon = mutableListOf<List<UserXX>?>()
        groupBy.forEach {
            if (it.value.size == 1)
                nonCommon.add(it.value)
        }

        val newlyAdded = mutableListOf<UserXX>()
        val newlyRemoved = mutableListOf<UserXX>()

        nonCommon.forEach {
            if (previousFollowers.contains(it!![0])){
                newlyRemoved.add(it[0])
            }else{
                newlyAdded.add(it[0])
            }
        }

        return FollowersDifferenceModel(
                increaseDifference = newlyAdded.size,
                decreaseDifference = newlyRemoved.size,
                increaseDiffList = newlyAdded,
                decreaseDiffList = newlyRemoved
        )
    }

    suspend fun getDifferenceOfNewFollowings(newFollowers: List<UserXX>?): FollowersDifferenceModel{
        if (newFollowers.isNullOrEmpty()){
            return FollowersDifferenceModel(0,0, emptyList(), emptyList())
        }
        val previousFollowers = followingsDao.getAllFollowings().toUserXX() ?: emptyList()

        val sum = previousFollowers + newFollowers
        val groupBy = sum.groupBy { it.pk }
        val nonCommon = mutableListOf<List<UserXX>?>()
        groupBy.forEach {
            if (it.value.size == 1)
                nonCommon.add(it.value)
        }

        val newlyAdded = mutableListOf<UserXX>()
        val newlyRemoved = mutableListOf<UserXX>()

        nonCommon.forEach {
            if (previousFollowers.contains(it!![0])){
                newlyRemoved.add(it[0])
            }else{
                newlyAdded.add(it[0])
            }
        }
        return FollowersDifferenceModel(
                increaseDifference = newlyAdded.size,
                decreaseDifference = newlyRemoved.size,
                increaseDiffList = newlyAdded,
                decreaseDiffList = newlyRemoved
        )
    }

}
