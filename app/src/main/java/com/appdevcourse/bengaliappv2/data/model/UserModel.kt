package com.appdevcourse.bengaliappv2.data.model

data class UserModel(
    val item: UserItems?,
    val key:String? = ""
){
    data class UserItems(
        val userName:String = "",
        val fullName:String = "",
        val email:String = "",
        val phone:String = "",
        val dateOfBirth:String = "",
        val password:String = "",
        val imageURL:String = ""
    )
}
