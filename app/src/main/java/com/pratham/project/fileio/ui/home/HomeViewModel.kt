package com.pratham.project.fileio.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.data.remote.models.UsernameInfo
import com.pratham.project.fileio.utils.base.BaseViewModel
import com.pratham.project.fileio.utils.network.ResultWrapper
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: HomeRepository,
    private val prefsManager: PreferenceManager
) : BaseViewModel() {

    val userDetails: LiveData<UsernameInfo>
        get() = _userDetails

    private val _userDetails = MutableLiveData<UsernameInfo>()

    init {
        getUsernameDetails()
    }

    private fun getUsernameDetails(){
        viewModelScope.launch {
            when(val response = repo.allowUserDetails()){
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getUserDetails: ${response.error}" )
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val userDetails = response.value.body()
                        with(userDetails?.user){
                            this?.fullName?.let { prefsManager.fullName = it }
                            this?.username?.let { prefsManager.userName = it }
                            this?.profilePicUrl?.let { prefsManager.userProfileImg = it }
                            this?.pk?.let { prefsManager.userProfileId = it }
                        }
                        getAllFollowers()
                        getAllFollowings()
                        getLikesFromFeeds()
                        getUserFeed()
                        _userDetails.postValue(response.value.body())
                    } else {
                        Log.e("TAG", "getUserDetails: ${response.value.message()}" )
                    }
                }
            }
        }
    }

    private fun getAllFollowers(){
        viewModelScope.launch {
            when(val response = repo.getAllFollowers()){
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getAllFollowers: ${response.error}" )
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val userDetails = response.value.body()
                        Log.e("TAG", "getAllFollowers: $userDetails")
                    } else {
                        Log.e("TAG", "getAllFollowers: ${response.value.message()}")
                    }
                }
            }
        }
    }

    private fun getAllFollowings(){
        viewModelScope.launch {
            when(val response = repo.getAllFollowings()){
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getAllFollowings: ${response.error}" )
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val userDetails = response.value.body()
                        Log.e("TAG", "getAllFollowings: $userDetails")
                    } else {
                        Log.e("TAG", "getAllFollowings: ${response.value.message()}")
                    }
                }
            }
        }
    }

    private fun getLikesFromFeeds(){
        viewModelScope.launch {
            when(val response = repo.getLikesFromFeeds()){
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getLikesFromFeeds: ${response.error}" )
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val userDetails = response.value.body()
                        Log.e("TAG", "getLikesFromFeeds: $userDetails")
                    } else {
                        Log.e("TAG", "getLikesFromFeeds: ${response.value.message()}")
                    }
                }
            }
        }
    }

    private fun getUserFeed(){
        viewModelScope.launch {
            when(val response = repo.getUserFeeds()){
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getLikesFromFeeds: ${response.error}" )
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val userDetails = response.value.body()
                        Log.e("TAG", "getLikesFromFeeds: $userDetails")
                    } else {
                        Log.e("TAG", "getLikesFromFeeds: ${response.value.message()}")
                    }
                }
            }
        }
    }

}
