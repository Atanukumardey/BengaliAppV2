package com.appdevcourse.bengaliappv2.data.repository

import com.appdevcourse.bengaliappv2.common.ResultState
import com.appdevcourse.bengaliappv2.data.model.UserModel
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userTableReference: DatabaseReference;
    fun insert(
        item: UserModel.UserItems
    ) : Flow<ResultState<String>>

    fun getItemByUserName(userName:String?) : Flow<ResultState<List<UserModel>>>

    fun getItemByEmail(email:String?) : Flow<ResultState<List<UserModel>>>

    fun getItems() : Flow<ResultState<List<UserModel>>>

    fun delete(
        key:String
    ) : Flow<ResultState<String>>

    fun update(
        res: UserModel
    ) : Flow<ResultState<String>>

}