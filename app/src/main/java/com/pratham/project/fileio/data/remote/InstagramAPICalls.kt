package com.pratham.project.fileio.data.remote

import com.pratham.project.fileio.data.remote.models.UsernameInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InstagramAPICalls {

    @GET("users/{username}/usernameinfo/")
    suspend fun getUserDetails(
        @Path("username") username: String
    ): Response<UsernameInfo>

}