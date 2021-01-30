package com.pratham.project.fileio.ui.home

import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.data.local.dao.*
import com.pratham.project.fileio.data.local.models.*
import com.pratham.project.fileio.data.remote.InstagramAPICalls
import com.pratham.project.fileio.data.remote.models.*
import com.pratham.project.fileio.data.utils.safeApiCall
import com.pratham.project.fileio.data.utils.toLocalModel
import com.pratham.project.fileio.data.utils.toLocalUserFeed
import com.pratham.project.fileio.data.utils.toUserXX
import java.util.*
import kotlin.collections.HashMap

class HomeRepository(
        private val instagramAPICalls: InstagramAPICalls,
        private val prefsManager: PreferenceManager,
        private val usernameDao: UsernameDao,
        private val followersDao: FollowersDao,
        private val followingsDao: FollowingsDao,
        private val userLocalCountsDao: UserLocalCountsDao,
        private val feedsDao: FeedsDao
) {

    suspend fun getUserDetails() = safeApiCall { instagramAPICalls.getUserDetails(prefsManager.userName) }

    suspend fun allowUserDetails() = safeApiCall { instagramAPICalls.allowUserEdit() }

    suspend fun dropAllFollowers() = followersDao.deleteAll()

    suspend fun dropAllFollowings() = followingsDao.deleteAll()

    suspend fun dropAllFeeds() = feedsDao.deleteAll()

    suspend fun getAllFollowers(maxId: String?) =
            safeApiCall { instagramAPICalls.getAllFollowers(prefsManager.userProfileId, maxId = maxId) }

    suspend fun getAllFollowings(maxId: String?) = safeApiCall {
        instagramAPICalls.getAllFollowings(
                prefsManager.userProfileId,
                maxId = maxId
        )
    }

    suspend fun getLikesFromFeeds(maxId: String?) = safeApiCall { instagramAPICalls.getLikesFromFeeds(maxId) }

    suspend fun getUserFeeds(maxId: String?) =
            safeApiCall { instagramAPICalls.getUserFeed(prefsManager.userProfileId, maxId) }

    fun getUserPosts() = feedsDao.getUserPostsLD(prefsManager.userProfileId)

    suspend fun addFollowersToLocal(followersList: List<UserXX>?) {
        followersList?.forEach { it.connectedToUserPk = prefsManager.userProfileId }
        followersList?.let { followersDao.insertAll(it) }
    }

    suspend fun addFollowingsToLocal(followingList: List<UserXXX>?) {
        followingList?.let { followingsDao.insertAll(it) }
    }

    suspend fun addUserToLocal(userItem: User?) {
        userItem.toLocalModel(prefsManager)?.let { usernameDao.insert(it) }
    }

    suspend fun addUserFeedToLocal(items: List<Item>?) {
        items.toLocalUserFeed()?.let { feedsDao.insertAll(it) }
    }

    suspend fun getDifferenceOfNewFollowers(newFollowers: List<UserXX>?): FollowersDifferenceModel {
        if (newFollowers.isNullOrEmpty()) {
            return FollowersDifferenceModel(0, 0, emptyList(), emptyList())
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
            if (previousFollowers.contains(it!![0])) {
                newlyRemoved.add(it[0])
            } else {
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

    suspend fun getDifferenceOfNewFollowings(newFollowers: List<UserXX>?): FollowersDifferenceModel {
        if (newFollowers.isNullOrEmpty()) {
            return FollowersDifferenceModel(0, 0, emptyList(), emptyList())
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
            if (previousFollowers.contains(it!![0])) {
                newlyRemoved.add(it[0])
            } else {
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

    suspend fun saveUserVariousCounts(
            userLikesCount: Int,
            userCommentsCount: Int,
            userFollowesCount: Int,
            userFollowingsCount: Int,
            userPostsCount: Int
    ) {
        val latestCounts = userLocalCountsDao.getLatestAddedCounts()

        if (latestCounts == null) {
            userLocalCountsDao.insert(
                    UserLocalCountsEntity(
                            userLikesCount,
                            userCommentsCount,
                            userFollowesCount,
                            userFollowingsCount,
                            userPostsCount
                    )
            )
            return
        }

        val now = Calendar.getInstance()
        now.time = Date(System.currentTimeMillis())

        val previousDate = Calendar.getInstance()
        previousDate.time = latestCounts.timeStamp

        if (previousDate.get(Calendar.DATE) == now.get(Calendar.DATE)) {
            userLocalCountsDao.delete(latestCounts)
        }
        userLocalCountsDao.insert(
                UserLocalCountsEntity(
                        userLikesCount,
                        userCommentsCount,
                        userFollowesCount,
                        userFollowingsCount,
                        userPostsCount
                )
        )
    }

    suspend fun analyzeHastags(): List<HashtagsCountModel> {
        val userPosts = feedsDao.getUserPosts(prefsManager.userProfileId)
        val map: HashMap<String, Int> = HashMap()

        val userCaptionsLists = userPosts.map { it.caption }
        userCaptionsLists.forEach { caption ->
            val hashtags = Regex("(#+[a-zA-Z0-9(_)]+)").findAll(caption?.text ?: "")
            hashtags.forEach {
                if (map.containsKey(it.value)) {
                    val hashtagCount = map[it.value] ?: 0 + 1
                    map[it.value] = hashtagCount
                } else {
                    map[it.value] = 1
                }
            }
        }

        return map.map { HashtagsCountModel(it.key, it.value) }
    }

    suspend fun analyzeLocations(): List<LocationCountModel> {
        val userPosts = feedsDao.getUserPosts(prefsManager.userProfileId)
        val map: HashMap<Location, Int> = HashMap()

        val userLocationsLists = userPosts.map { it.location }
        userLocationsLists.forEach { location ->
            if (location != null) {
                if (map.containsKey(location)) {
                    val locationCount = map[location] ?: 1
                    map[location] = locationCount + 1
                } else {
                    map[location] = 1
                }
            }
        }

        return map.map { LocationCountModel(it.key, it.value) }
    }

}
