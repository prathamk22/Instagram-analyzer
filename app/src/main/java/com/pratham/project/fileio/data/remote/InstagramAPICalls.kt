package com.pratham.project.fileio.data.remote

import com.pratham.project.fileio.data.remote.models.FollowersModel
import com.pratham.project.fileio.data.remote.models.LikesModel
import com.pratham.project.fileio.data.remote.models.UsernameInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface InstagramAPICalls {

    @GET("users/{username}/usernameinfo/")
    suspend fun getUserDetails(
        @Path("username") username: String
    ): Response<UsernameInfo>

    @POST("accounts/current_user")
    suspend fun allowUserEdit(
            @Query("edit") edit: Boolean = true
    ): Response<UsernameInfo>

    @GET("friendships/{userId}/followers/")
    suspend fun getAllFollowers(
            @Path("userId") userId: Long,
            @Query("rank_token") randToken: String? = null
    ): Response<FollowersModel>

    @GET("friendships/{userId}/following/")
    suspend fun getAllFollowings(
            @Path("userId") userId: Long,
            @Query("rank_token") randToken: String? = null,
            @Query("ig_sig_key_version") igSigKeyVer: Int = 4
    ): Response<FollowersModel>

    @GET("feed/liked/")
    suspend fun getLikesFromFeeds(
            @Query("max_id") maxId: Long? = null
    ): Response<LikesModel>

    @GET("feed/user/{userId}/")
    suspend fun getUserFeed(
            @Path("userId") userId: Long,
            @Query("max_id") maxId: Long? = null,
            @Query("min_timestamp") minTimestamp: Long? = null,
            @Query("rank_token") randToken: String? = null,
            @Query("ranked_content") rankedContent: Boolean? = true,
    ): Response<LikesModel>
}