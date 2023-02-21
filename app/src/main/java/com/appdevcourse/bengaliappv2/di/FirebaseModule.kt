package com.appdevcourse.bengaliappv2.di

//import android.content.Context
//import androidx.compose.ui.platform.LocalContext
//import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun providesRealtimeDb(): FirebaseDatabase = Firebase.database
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseStorage {
    @Provides
    @Singleton
    fun providesFirebaseStorage(): FirebaseStorage = Firebase.storage
}

