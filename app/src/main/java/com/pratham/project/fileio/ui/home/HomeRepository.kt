package com.pratham.project.fileio.ui.home

import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.data.remote.InstagramAPICalls
import com.pratham.project.fileio.utils.network.safeApiCall

class HomeRepository(
    private val instagramAPICalls: InstagramAPICalls,
    private val prefsManager: PreferenceManager
) {

    suspend fun getUserDetails() = safeApiCall { instagramAPICalls.getUserDetails("new_amoled_wallpapers") }

}