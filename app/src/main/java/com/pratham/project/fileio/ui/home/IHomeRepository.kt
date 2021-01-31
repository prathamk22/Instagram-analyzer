package com.pratham.project.fileio.ui.home

import androidx.lifecycle.LiveData
import com.pratham.project.fileio.data.local.models.*
import com.pratham.project.fileio.data.remote.models.*
import com.pratham.project.fileio.data.utils.ResultWrapper
import retrofit2.Response

interface IHomeRepository {

    suspend fun getUserDetails(): ResultWrapper<Response<UsernameInfo>>

    suspend fun allowUserDetails(): ResultWrapper<Response<UsernameInfo>>

    suspend fun dropAllFollowers()

    suspend fun dropAllFollowings()

    suspend fun dropAllFeeds()

    suspend fun getAllFollowers(maxId: String?): ResultWrapper<Response<FollowersModel>>

    suspend fun getAllFollowings(maxId: String?): ResultWrapper<Response<FollowersModel>>

    suspend fun getLikesFromFeeds(maxId: String?): ResultWrapper<Response<LikesModel>>

    suspend fun getUserFeeds(maxId: String?): ResultWrapper<Response<LikesModel>>

    fun getUserPosts(): LiveData<List<FeedsEntity>>

    suspend fun addFollowersToLocal(followersList: List<UserXX>?)

    suspend fun addFollowingsToLocal(followingList: List<UserXXX>?)

    suspend fun addUserToLocal(userItem: User?)

    suspend fun addUserFeedToLocal(items: List<Item>?)

    suspend fun getDifferenceOfNewFollowers(newFollowers: List<UserXX>?): FollowersDifferenceModel

    suspend fun getDifferenceOfNewFollowings(newFollowers: List<UserXX>?): FollowersDifferenceModel

    suspend fun saveUserVariousCounts(
        userLikesCount: Int,
        userCommentsCount: Int,
        userFollowesCount: Int,
        userFollowingsCount: Int,
        userPostsCount: Int
    )

    suspend fun analyzeHastags(): List<HashtagsCountModel>

    suspend fun analyzeLocations(): List<LocationCountModel>
}