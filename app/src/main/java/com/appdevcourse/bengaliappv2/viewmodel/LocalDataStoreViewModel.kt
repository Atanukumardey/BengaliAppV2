package com.appdevcourse.bengaliappv2.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appdevcourse.bengaliappv2.repository.LocalDatastore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalDataStoreViewModel
@Inject
constructor(@ApplicationContext private val context:Context) : ViewModel() {

    private val datastoreSetting: LocalDatastore = LocalDatastore(context)
    fun saveToLocal(user:List<String>) = viewModelScope.launch {
        datastoreSetting.saveUserToLocal(user)
    }

    val readFromLocal = datastoreSetting.readFromLocal

//    init {
//        viewModelScope.launch {
//
//        }
//    }

}