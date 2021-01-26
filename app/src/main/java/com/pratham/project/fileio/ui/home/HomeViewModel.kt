package com.pratham.project.fileio.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pratham.project.fileio.data.remote.models.UsernameInfo
import com.pratham.project.fileio.utils.base.BaseViewModel
import com.pratham.project.fileio.utils.network.ResultWrapper
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: HomeRepository
) : BaseViewModel() {

    val userDetails: LiveData<UsernameInfo>
        get() = _userDetails

    private val _userDetails = MutableLiveData<UsernameInfo>()

    init {
        getUsernameDetails()
    }

    private fun getUsernameDetails(){
        viewModelScope.launch {
            when(val response = repo.getUserDetails()){
                is ResultWrapper.GenericError -> {
                    Log.e("TAG", "getUserDetails: ${response.error}" )
                }
                is ResultWrapper.Success -> {
                    if (response.value.isSuccessful) {
                        _userDetails.postValue(response.value.body())
                    } else {
                        Log.e("TAG", "getUserDetails: ${response.value.message()}" )
                    }
                }
            }
        }
    }


}