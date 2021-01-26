package com.pratham.project.fileio.ui.home

import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.data.local.dao.FollowersDao
import com.pratham.project.fileio.data.local.dao.FollowingsDao
import com.pratham.project.fileio.data.local.dao.UsernameDao
import com.pratham.project.fileio.data.local.models.UserXXX
import com.pratham.project.fileio.data.remote.InstagramAPICalls
import com.pratham.project.fileio.data.remote.models.FollowersModel
import com.pratham.project.fileio.data.remote.models.User
import com.pratham.project.fileio.data.remote.models.UserXX
import com.pratham.project.fileio.data.utils.safeApiCall
import com.pratham.project.fileio.data.utils.toLocalModel

class HomeRepository(
        private val instagramAPICalls: InstagramAPICalls,
        private val prefsManager: PreferenceManager,
        private val usernameDao: UsernameDao,
        private val followersDao: FollowersDao,
        private val followingsDao: FollowingsDao,
) {

    suspend fun getUserDetails() = safeApiCall { instagramAPICalls.getUserDetails(prefsManager.userName) }

    suspend fun allowUserDetails() = safeApiCall { instagramAPICalls.allowUserEdit() }

    suspend fun getAllFollowers() = safeApiCall { instagramAPICalls.getAllFollowers(prefsManager.userProfileId) }

    suspend fun getAllFollowings() = safeApiCall { instagramAPICalls.getAllFollowings(prefsManager.userProfileId) }

    suspend fun getLikesFromFeeds() = safeApiCall { instagramAPICalls.getLikesFromFeeds() }

    suspend fun getUserFeeds() = safeApiCall { instagramAPICalls.getUserFeed(prefsManager.userProfileId) }

    suspend fun addFollowersToLocal(followersList: List<UserXX>?){
        followersList?.let { followersDao.insertAll(it) }
    }

    suspend fun addFollowingsToLocal(followingList: List<UserXXX>?){
        followingList?.let { followingsDao.insertAll(it) }
    }

    suspend fun addUserToLocal(userItem: User?){
        userItem.toLocalModel(prefsManager)?.let { usernameDao.insert(it) }
    }

}
