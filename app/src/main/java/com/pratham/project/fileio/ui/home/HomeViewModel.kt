package com.pratham.project.fileio.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pratham.project.fileio.R
import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.data.remote.models.Item
import com.pratham.project.fileio.data.remote.models.UsernameInfo
import com.pratham.project.fileio.utils.base.BaseViewModel
import com.pratham.project.fileio.data.utils.ResultWrapper
import com.pratham.project.fileio.data.utils.toLocalUser
import com.pratham.project.fileio.utils.SpannableModel
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

    private fun getAllFollowers(maxId: String? = null) {
        viewModelScope.launch {
            when (val response = repo.getAllFollowers(maxId)) {
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getAllFollowers: ${response.error}")
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val userDetails = response.value.body()
                        repo.addFollowersToLocal(userDetails?.users)
                        if (userDetails?.bigList == true){
                            getAllFollowers(userDetails.nextMaxId)
                        }else{
                            val increaseInFollowers = repo.getDifferenceOfNewFollowers(userDetails?.users)
                        }
                    } else {
                        Log.e("TAG", "getAllFollowers: ${response.value.message()}")
                    }
                }
            }
        }
    }

    private fun getAllFollowings(maxId: String? = null) {
        viewModelScope.launch {
            when (val response = repo.getAllFollowings()) {
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getAllFollowings: ${response.error}")
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful || response.value.body() != null) {
                        val userDetails = response.value.body()
                        repo.addFollowingsToLocal(userDetails?.users.toLocalUser())
                        if (userDetails?.bigList == true){
                            getAllFollowings(userDetails.nextMaxId)
                        }else{

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
                        updatePosts(userDetails?.items)
                        updateLikes(userDetails?.items)
                        updateComments(userDetails?.items)
                    } else {
                        Log.e("TAG", "getLikesFromFeeds: ${response.value.message()}")
                    }
                }
            }
        }
    }

    private fun updatePosts(itemList: List<Item>?){
        val list = mutableListOf<SpannableModel>()
        list.add(
                SpannableModel(
                        "Posts\n",
                        textColor = R.color.white,
                        typeFace = "normal",
                        textSize = R.dimen.sp20
                )
        )
        list.add(
                SpannableModel(
                        "${itemList?.size ?: 0}",
                        textColor = R.color.white,
                        typeFace = "bold",
                        textSize = R.dimen.sp40
                )
        )
        _userPostsCount.postValue(list)
    }

    private fun updateLikes(itemList: List<Item>?){
        val list = mutableListOf<SpannableModel>()
        val likesCount = itemList?.sumBy { it.likeCount ?: 0 } ?: 0
        list.add(
                SpannableModel(
                        "Likes\n",
                        textColor = R.color.white,
                        typeFace = "normal",
                        textSize = R.dimen.sp20
                )
        )
        list.add(
                SpannableModel(
                        "$likesCount",
                        textColor = R.color.white,
                        typeFace = "bold",
                        textSize = R.dimen.sp40
                )
        )
        _userLikesCount.postValue(list)
    }

    private fun updateComments(itemList: List<Item>?){
        val list = mutableListOf<SpannableModel>()
        val commentsCount = itemList?.sumBy { it.commentCount ?: 0 } ?: 0
        list.add(
                SpannableModel(
                        "Comment\n",
                        textColor = R.color.white,
                        typeFace = "normal",
                        textSize = R.dimen.sp20
                )
        )
        list.add(
                SpannableModel(
                        "$commentsCount",
                        textColor = R.color.white,
                        typeFace = "bold",
                        textSize = R.dimen.sp40
                )
        )
        _userCommentsCount.postValue(list)
    }

}
