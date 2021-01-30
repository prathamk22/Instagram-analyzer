package com.pratham.project.fileio.utils.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel : ViewModel(){

    protected val backgroundThread = CoroutineScope(Dispatchers.IO)

}