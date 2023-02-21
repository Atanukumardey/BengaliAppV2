package com.appdevcourse.bengaliappv2.repository


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalDatastore
@Inject
constructor(@ApplicationContext private val context: Context) {

    companion  object PreferencesKey {
        val userName = stringPreferencesKey("userName")
        val fullName = stringPreferencesKey("fullName")
        val userEmail = stringPreferencesKey("userEmail")
        val userNumber = stringPreferencesKey("userNumber")
        val dob = stringPreferencesKey("dob")
        val imageURL = stringPreferencesKey("imageURL")
        private val Context.dataStore by preferencesDataStore(name = "setting")
    }
    suspend fun saveNameToLocal(name: String) {
        context.dataStore.edit { preference->
            preference[PreferencesKey.userName] = name
        }
    }
    
    suspend fun saveUserToLocal(user: List<String>) {
        context.dataStore.edit { preference->
            preference[PreferencesKey.userName] = user[0]
            preference[PreferencesKey.fullName] = user[1]
            preference[PreferencesKey.userEmail] = user[2]
            preference[PreferencesKey.userNumber] = user[3]
            preference[PreferencesKey.dob] = user[4]
            preference[PreferencesKey.imageURL] = user[5]
        }
    }
    


    val readFromLocal : Flow<List<String>> = context.dataStore.data
        .catch {
            if(this is Exception){
                emit(emptyPreferences())
            }
        }.map { preference->
            val userData = arrayListOf<String>(
                preference[PreferencesKey.userName] ?: "null",
                preference[PreferencesKey.fullName] ?: "null",
                preference[PreferencesKey.userEmail] ?: "null",
                preference[PreferencesKey.userNumber] ?: "null",
                preference[PreferencesKey.dob] ?: "null",
                preference[PreferencesKey.imageURL] ?: "null"
            )
            userData
        }

}

