package com.appdevcourse.bengaliappv2.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appdevcourse.bengaliappv2.common.ResultState
import com.appdevcourse.bengaliappv2.data.model.UserModel
import com.appdevcourse.bengaliappv2.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo:UserRepository
):ViewModel() {
    private val _res: MutableState<UserItemState> = mutableStateOf(UserItemState())
    val res: State<UserItemState> = _res

    fun insert(items: UserModel.UserItems) = repo.insert(items)

    private val _updateRes:MutableState<UserModel> = mutableStateOf(
        UserModel(
            item = UserModel.UserItems(),
            key = ""
        )
    )
    val updateRes:State<UserModel> = _updateRes

    fun setData(data:UserModel){
        _updateRes.value = data

    }

    init {
        viewModelScope.launch {
            repo.getItemByEmail("atanukumar251@gmail.com").collect{
                when(it){
                    is ResultState.Success -> {
                        _res.value = UserItemState(
                            item = it.data,
                        )
                        Log.d("Data In ViweModel:",it.data.toString())
                    }
                    is ResultState.Failure->{
                        _res.value = UserItemState(
                            error = it.msg.toString()
                        )
                    }
                    ResultState.Loading->{
                        _res.value = UserItemState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
    fun delete(key:String) = repo.delete(key)
    fun update(item:UserModel) = repo.update(item)
}

data class UserItemState(
    val item:List<UserModel> = emptyList(),
    val error:String  = "",
    val isLoading:Boolean = false
)