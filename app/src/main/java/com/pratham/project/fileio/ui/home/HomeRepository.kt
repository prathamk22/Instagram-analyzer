package com.pratham.project.fileio.ui.home

import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.data.remote.InstagramAPICalls
import com.pratham.project.fileio.utils.network.safeApiCall

class HomeRepository(
        private val instagramAPICalls: InstagramAPICalls,
        private val prefsManager: PreferenceManager
) {

    suspend fun getUserDetails() = safeApiCall { instagramAPICalls.getUserDetails(prefsManager.userName) }

    suspend fun allowUserDetails() = safeApiCall { instagramAPICalls.allowUserEdit() }

    suspend fun getAllFollowers() = safeApiCall { instagramAPICalls.getAllFollowers(prefsManager.userProfileId) }

    suspend fun getAllFollowings() = safeApiCall { instagramAPICalls.getAllFollowings(prefsManager.userProfileId) }

    suspend fun getLikesFromFeeds() = safeApiCall { instagramAPICalls.getLikesFromFeeds() }

    suspend fun getUserFeeds() = safeApiCall { instagramAPICalls.getUserFeed(prefsManager.userProfileId) }

}
