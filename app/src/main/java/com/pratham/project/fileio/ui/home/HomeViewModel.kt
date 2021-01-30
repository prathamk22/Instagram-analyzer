package com.pratham.project.fileio.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.pratham.project.fileio.data.PreferenceManager
import com.pratham.project.fileio.data.local.models.FeedsEntity
import com.pratham.project.fileio.data.local.models.FollowersDifferenceModel
import com.pratham.project.fileio.data.local.models.HashtagsCountModel
import com.pratham.project.fileio.data.local.models.LocationCountModel
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
import com.pratham.project.fileio.utils.widgits.CustomGraphView
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(
    private val repo: HomeRepository,
    private val prefsManager: PreferenceManager
) : BaseViewModel() {

    val loadingDone: LiveData<Boolean>
        get() = _loadingDone
    private val _loadingDone = MutableLiveData<Boolean>()

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

    val likesPointMapLD: LiveData<CustomGraphView.GraphDataModel>
        get() = _likesPointMapLD
    private val _likesPointMapLD = MutableLiveData<CustomGraphView.GraphDataModel>()

    val hashtagsListLD: LiveData<List<HashtagsCountModel>>
        get() = _hashtagsLD
    private val _hashtagsLD = MutableLiveData<List<HashtagsCountModel>>()

    val locationListLD: LiveData<List<LocationCountModel>>
        get() = _locationList
    private val _locationList = MutableLiveData<List<LocationCountModel>>()

    private val userCountsObserver = Observer<List<FeedsEntity>> {
        val likesEntryList = mutableListOf<Entry>()
        val commentsEntryList = mutableListOf<Entry>()
        if (it.isNullOrEmpty()){
            _likesPointMapLD.postValue(CustomGraphView.GraphDataModel(
                    Description().apply { text = "" },
                    emptyList(),
                    emptyList()
            ))
            return@Observer
        }
        it.reversed().forEachIndexed { index, feed ->
            val calender = Calendar.getInstance()
            calender.time = Date(feed.deviceTimeStamp ?: 0L)

            likesEntryList.add(Entry(index.toFloat(), feed.likeCount?.toFloat() ?: 0f))
            commentsEntryList.add(Entry(index.toFloat(), feed.commentCount?.toFloat() ?: 0f))
        }

        val pointMap = CustomGraphView.GraphDataModel(
            Description().apply { text = "" },
            likesEntryList,
            commentsEntryList
        )
        _likesPointMapLD.postValue(pointMap)
    }

    init {
        getUsernameDetails()
        repo.getUserPosts().observeForever(userCountsObserver)
    }

    private fun getUsernameDetails() {
        backgroundThread.launch {
            when (val response = repo.allowUserDetails()) {
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getUserDetails: ${response.error}")
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val responseValue = response.value.body()
                        with(responseValue?.user) {
                            this?.fullName?.let { prefsManager.fullName = it }
                            this?.username?.let { prefsManager.userName = it }
                            this?.profilePicUrl?.let { prefsManager.userProfileImg = it }
                            this?.pk?.let { prefsManager.userProfileId = it }
                        }
                        repo.dropAllFeeds()
                        refreshUserDetails()
                    } else {
                        Log.e("TAG", "getUserDetails: ${response.value.message()}")
                    }
                }
            }
        }
    }

    private fun refreshUserDetails() {
        backgroundThread.launch {
            when (val response = repo.getUserDetails()) {
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getUserDetails: ${response.error}")
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val responseValue = response.value.body()
                        repo.addUserToLocal(responseValue?.user)
                        getAllFollowers()
                        getAllFollowings()
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
        backgroundThread.launch {
            when (val response = repo.getAllFollowers(maxId)) {
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getAllFollowers: ${response.error}")
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val responseValue = response.value.body()
                        if (responseValue?.bigList == true) {
                            getAllFollowers(responseValue.nextMaxId, responseValue.users)
                        } else {
                            val newList = responseValue?.users?.toMutableList()
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
        backgroundThread.launch {
            when (val response = repo.getAllFollowings(maxId)) {
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getAllFollowings: ${response.error}")
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful || response.value.body() != null) {
                        val responseValue = response.value.body()
                        if (responseValue?.bigList == true) {
                            getAllFollowings(responseValue.nextMaxId, responseValue.users)
                        } else {
                            val newList = responseValue?.users?.toMutableList()
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

    private fun getUserFeed(maxId: String? = null, previousList: List<Item>? = null) {
        backgroundThread.launch {
            when (val response = repo.getUserFeeds(maxId)) {
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getUserFeed: ${response.error}")
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        val responseValue = response.value.body()
                        if (responseValue?.moreAvailable == true){
                            getUserFeed(responseValue.nextMaxId, responseValue.items)
                        }else{
                            val allUserPosts = (responseValue?.items ?: emptyList()) + (previousList ?:  emptyList())
                            repo.addUserFeedToLocal(allUserPosts)
                            _userCommentsCount.postValue(allUserPosts.getCommentsSpannableList())
                            _userLikesCount.postValue(allUserPosts.getLikesSpannableList())
                            _userPostsCount.postValue(allUserPosts.getPostsSpannableList())
                            _hashtagsLD.postValue(repo.analyzeHastags())
                            _locationList.postValue(repo.analyzeLocations())
                            _loadingDone.postValue(true)
                            repo.saveUserVariousCounts(
                                    userFollowesCount = _userDetails.value?.user?.followerCount ?: 0,
                                    userFollowingsCount = _userDetails.value?.user?.followingCount ?: 0,
                                    userLikesCount = allUserPosts.sumBy { it.likeCount ?: 0 },
                                    userCommentsCount = allUserPosts.sumBy { it.commentCount ?: 0 },
                                    userPostsCount = allUserPosts.size
                            )
                        }
                    } else {
                        Log.e("TAG", "getUserFeed: ${response.value.message()}")
                    }
                }
            }
        }
    }

}
