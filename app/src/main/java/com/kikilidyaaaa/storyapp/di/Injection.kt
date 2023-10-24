package com.kikilidyaaaa.storyapp.di

import android.content.Context
import com.kikilidyaaaa.storyapp.data.StoryRepository
import com.kikilidyaaaa.storyapp.data.UserRepository
import com.kikilidyaaaa.storyapp.data.pref.UserPreference
import com.kikilidyaaaa.storyapp.data.pref.dataStore
import com.kikilidyaaaa.storyapp.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(pref, apiService)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return StoryRepository.getInstance(pref, apiService)
    }
}