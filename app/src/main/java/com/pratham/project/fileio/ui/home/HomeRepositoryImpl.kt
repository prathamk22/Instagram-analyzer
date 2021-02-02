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

class HomeRepositoryImpl(
        private val instagramAPICalls: InstagramAPICalls,
        private val prefsManager: PreferenceManager,
        private val usernameDao: UsernameDao,
        private val followersDao: FollowersDao,
        private val followingsDao: FollowingsDao,
        private val userLocalCountsDao: UserLocalCountsDao,
        private val feedsDao: FeedsDao
): IHomeRepository {

    companion object{
        const val HASHTAGS_SIZE = 6
        const val LOCATIONS_SIZE = 6
    }

    override suspend fun getUserDetails() = safeApiCall { instagramAPICalls.getUserDetails(prefsManager.userName) }

    override suspend fun allowUserDetails() = safeApiCall { instagramAPICalls.allowUserEdit() }

    override suspend fun dropAllFollowers() = followersDao.deleteAll()

    override suspend fun dropAllFollowings() = followingsDao.deleteAll()

    override suspend fun dropAllFeeds() = feedsDao.deleteAll()

    override suspend fun getAllFollowers(maxId: String?) =
            safeApiCall { instagramAPICalls.getAllFollowers(prefsManager.userProfileId, maxId = maxId) }

    override suspend fun getAllFollowings(maxId: String?) = safeApiCall {
        instagramAPICalls.getAllFollowings(
                prefsManager.userProfileId,
                maxId = maxId
        )
    }

    override suspend fun getLikesFromFeeds(maxId: String?) = safeApiCall { instagramAPICalls.getLikesFromFeeds(maxId) }

    override suspend fun getUserFeeds(maxId: String?) =
            safeApiCall { instagramAPICalls.getUserFeed(prefsManager.userProfileId, maxId) }

    override fun getUserPosts() = feedsDao.getUserPostsLD(prefsManager.userProfileId)

    override suspend fun addFollowersToLocal(followersList: List<UserXX>?) {
        followersList?.forEach { it.connectedToUserPk = prefsManager.userProfileId }
        followersList?.let { followersDao.insertAll(it) }
    }

    override suspend fun addFollowingsToLocal(followingList: List<UserXXX>?) {
        followingList?.let { followingsDao.insertAll(it) }
    }

    override suspend fun addUserToLocal(userItem: User?) {
        userItem.toLocalModel(prefsManager)?.let { usernameDao.insert(it) }
    }

    override suspend fun addUserFeedToLocal(items: List<Item>?) {
        items.toLocalUserFeed()?.let { feedsDao.insertAll(it) }
    }

    override suspend fun getDifferenceOfNewFollowers(newFollowers: List<UserXX>?): FollowersDifferenceModel {
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

    override suspend fun getDifferenceOfNewFollowings(newFollowers: List<UserXX>?): FollowersDifferenceModel {
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

    override suspend fun saveUserVariousCounts(
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

    override suspend fun analyzeHastags(): List<HashtagsCountModel> {
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

        return map.map { HashtagsCountModel(it.key, it.value) }.subList(0, if (map.size > HASHTAGS_SIZE) HASHTAGS_SIZE else map.size)
    }

    override suspend fun analyzeLocations(): List<LocationCountModel> {
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

        return map.map { LocationCountModel(it.key, it.value) }.subList(0, if (map.size > LOCATIONS_SIZE) LOCATIONS_SIZE else map.size)
    }

    override fun saveUserDetailsToLocal(user: User?) {
        with(user) {
            this?.fullName?.let { prefsManager.fullName = it }
            this?.username?.let { prefsManager.userName = it }
            this?.profilePicUrl?.let { prefsManager.userProfileImg = it }
            this?.pk?.let { prefsManager.userProfileId = it }
        }
    }

}
