package com.kikilidyaaaa.storyapp.view.main

import android.util.Log
import androidx.lifecycle.*
import com.kikilidyaaaa.storyapp.data.StoryRepository
import com.kikilidyaaaa.storyapp.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return storyRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            storyRepository.logout()
            Log.d(TAG, "Token removed")
        }
    }

    fun getStories() = storyRepository.getStories()

    companion object {
        private const val TAG = "MainViewModel"
    }
}