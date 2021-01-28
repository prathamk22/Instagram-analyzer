package com.pratham.project.fileio.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pratham.project.fileio.R
import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.data.local.models.FollowersDifferenceModel
import com.pratham.project.fileio.data.remote.models.Item
import com.pratham.project.fileio.data.remote.models.UserXX
import com.pratham.project.fileio.data.remote.models.UsernameInfo
import com.pratham.project.fileio.utils.base.BaseViewModel
import com.pratham.project.fileio.data.utils.ResultWrapper
import com.pratham.project.fileio.data.utils.toUserXXX
import com.pratham.project.fileio.utils.SpannableModel
import com.pratham.project.fileio.utils.getCommentsSpannableList
import com.pratham.project.fileio.utils.getLikesSpannableList
import com.pratham.project.fileio.utils.getPostsSpannableList
import kotlinx.coroutines.launch

class HomeViewModel(
        private val repo: HomeRepository,
        private val prefsManager: PreferenceManager
) : BaseViewModel() {

    val userDetails: LiveData<UsernameInfo>
        get() = _userDetails
    private val _userDetails = MutableLiveData<UsernameInfo>()

    val userPostsCount: LiveData<List<SpannableModel>>
        get() = _userPostsCount
    private val _userPostsCount = MutableLiveData<List<SpannableModel>>()

    val userLikesCount: LiveData<List<SpannableModel>>
        get() = _userLikesCount
    private val _userLikesCount = MutableLiveData<List<SpannableModel>>()

    val userCommentsCount: LiveData<List<SpannableModel>>
        get() = _userCommentsCount
    private val _userCommentsCount = MutableLiveData<List<SpannableModel>>()

    val userFollowersDifference: LiveData<FollowersDifferenceModel>
        get() = _userFollowersDifference
    private val _userFollowersDifference = MutableLiveData<FollowersDifferenceModel>()

    val userFollowingsDifference: LiveData<FollowersDifferenceModel>
        get() = _userFollowingsDifference
    private val _userFollowingsDifference = MutableLiveData<FollowersDifferenceModel>()

    init {
        getUsernameDetails()
    }

    private fun getUsernameDetails() {
        viewModelScope.launch {
            when (val response = repo.allowUserDetails()) {
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getUserDetails: ${response.error}")
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val userDetails = response.value.body()
                        with(userDetails?.user) {
                            this?.fullName?.let { prefsManager.fullName = it }
                            this?.username?.let { prefsManager.userName = it }
                            this?.profilePicUrl?.let { prefsManager.userProfileImg = it }
                            this?.pk?.let { prefsManager.userProfileId = it }
                        }
                        refreshUserDetails()
                    } else {
                        Log.e("TAG", "getUserDetails: ${response.value.message()}")
                    }
                }
            }
        }
    }

    private fun refreshUserDetails() {
        viewModelScope.launch {
            when (val response = repo.getUserDetails()) {
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getUserDetails: ${response.error}")
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val userDetails = response.value.body()
                        repo.addUserToLocal(userDetails?.user)
                        getAllFollowers()
                        getAllFollowings()
                        getLikesFromFeeds()
                        getUserFeed()
                        _userDetails.postValue(response.value.body())
                    } else {
                        Log.e("TAG", "getUserDetails: ${response.value.message()}")
                    }
                }
            }
        }
    }

    private fun getAllFollowers(maxId: String? = null, previousList: List<UserXX>? = emptyList()) {
        viewModelScope.launch {
            when (val response = repo.getAllFollowers(maxId)) {
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getAllFollowers: ${response.error}")
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val userDetails = response.value.body()
                        if (userDetails?.bigList == true) {
                            getAllFollowers(userDetails.nextMaxId, userDetails.users)
                        } else {
                            val newList = userDetails?.users?.toMutableList()
                            newList?.addAll(previousList ?: emptyList())
                            val increaseInFollowers = repo.getDifferenceOfNewFollowers(newList)
                            repo.dropAllFollowers()
                            repo.addFollowersToLocal(newList)
                            _userFollowersDifference.postValue(increaseInFollowers)
                        }
                    } else {
                        Log.e("TAG", "getAllFollowers: ${response.value.message()}")
                    }
                }
            }
        }
    }

    private fun getAllFollowings(maxId: String? = null, previousList: List<UserXX>? = emptyList()) {
        viewModelScope.launch {
            when (val response = repo.getAllFollowings(maxId)) {
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getAllFollowings: ${response.error}")
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful || response.value.body() != null) {
                        val userDetails = response.value.body()
                        if (userDetails?.bigList == true){
                            getAllFollowings(userDetails.nextMaxId, userDetails.users)
                        }else{
                            val newList = userDetails?.users?.toMutableList()
                            newList?.addAll(previousList ?: emptyList())
                            val increaseInFollowings = repo.getDifferenceOfNewFollowings(newList)
                            repo.dropAllFollowings()
                            repo.addFollowingsToLocal(newList.toUserXXX())
                            _userFollowingsDifference.postValue(increaseInFollowings)
                        }
                    } else {
                        Log.e("TAG", "getAllFollowings: ${response.value.message()}")
                    }
                }
            }
        }
    }

    private fun getLikesFromFeeds() {
        viewModelScope.launch {
            when (val response = repo.getLikesFromFeeds()) {
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getLikesFromFeeds: ${response.error}")
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val userDetails = response.value.body()
                    } else {
                        Log.e("TAG", "getLikesFromFeeds: ${response.value.message()}")
                    }
                }
            }
        }
    }

    private fun getUserFeed() {
        viewModelScope.launch {
            when (val response = repo.getUserFeeds()) {
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getLikesFromFeeds: ${response.error}")
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val userDetails = response.value.body()
                        _userCommentsCount.postValue(userDetails?.items.getCommentsSpannableList())
                        _userLikesCount.postValue(userDetails?.items.getLikesSpannableList())
                        _userPostsCount.postValue(userDetails?.items.getPostsSpannableList())
                        repo.saveUserVariousCounts(
                                userFollowesCount = _userDetails.value?.user?.followerCount ?: 0,
                                userFollowingsCount = _userDetails.value?.user?.followingCount ?: 0,
                                userLikesCount = userDetails?.items?.sumBy { it.likeCount ?: 0 } ?: 0,
                                userCommentsCount = userDetails?.items?.sumBy { it.commentCount ?: 0 } ?: 0,
                                userPostsCount = userDetails?.items?.size ?: 0
                        )
                    } else {
                        Log.e("TAG", "getLikesFromFeeds: ${response.value.message()}")
                    }
                }
            }
        }
    }

}
