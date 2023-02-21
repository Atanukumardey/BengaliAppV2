package com.appdevcourse.bengaliappv2.data.repository

import com.google.firebase.database.DatabaseReference
import android.util.Log
import com.appdevcourse.bengaliappv2.common.ResultState
import com.appdevcourse.bengaliappv2.data.model.UserModel
import com.google.firebase.database.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UserDbRepository
@Inject
constructor(
    private var db:FirebaseDatabase,
) : UserRepository {
    override val userTableReference: DatabaseReference = db.getReference("User")
    override fun insert(item: UserModel.UserItems): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        userTableReference.push().setValue(
            item
        ).addOnCompleteListener {
            if(it.isSuccessful)
                trySend(ResultState.Success("Data inserted Successfully.."))
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }
        awaitClose {
            close()
        }
    }

    override fun getItemByUserName(userName: String?): Flow<ResultState<List<UserModel>>> {
        TODO("Not yet implemented")
    }

    override fun getItemByEmail(email: String?): Flow<ResultState<List<UserModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        val checkUser = userTableReference.orderByChild("email").equalTo(email)
        val valueEvent = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val item = snapshot.children.map{
                    UserModel(
                        it.getValue(UserModel.UserItems::class.java),
                        key = it.key
                    )
                }

                //Log.d("UserData: ", item.toString())
                trySend(ResultState.Success(item))
            }
            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }
        }
        checkUser.addListenerForSingleValueEvent(valueEvent)

        awaitClose {
            checkUser.removeEventListener(valueEvent)
            close()
        }
    }

    override fun getItems(): Flow<ResultState<List<UserModel>>> =  callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map {
                    UserModel(
                        it.getValue(UserModel.UserItems::class.java),
                        key = it.key
                    )
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }

        }

        userTableReference.addValueEventListener(valueEvent)

        awaitClose {
            userTableReference.removeEventListener(valueEvent)
            close()
        }
    }

    override fun delete(key: String): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        userTableReference.child(key).removeValue()
            .addOnCompleteListener {
                trySend(ResultState.Success("item Deleted"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }
    override fun update(res: UserModel): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        Log.d("UserData: ", res.item.toString())
        val map = HashMap<String,Any>()
        map["userName"] = res.item?.userName!!
        map["fullName"] = res.item.fullName
        map["email"] = res.item.email
        map["phone"] = res.item.phone
        map["dateOfBirth"] = res.item.dateOfBirth
        map["password"] = res.item.password
        map["imageURL"] = res.item.imageURL
        Log.e("Key",res.key!!)
        userTableReference.child(res.key!!).updateChildren(
            map
        ).addOnCompleteListener {
            trySend(ResultState.Success("Update successfully..."))
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }
        awaitClose {
            close()
        }
    }
}