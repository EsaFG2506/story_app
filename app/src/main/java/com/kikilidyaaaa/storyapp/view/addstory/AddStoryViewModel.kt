package com.kikilidyaaaa.storyapp.view.addstory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kikilidyaaaa.storyapp.data.StoryRepository
import com.kikilidyaaaa.storyapp.view.login.LoginViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddStoryViewModel (private val storyRepository: StoryRepository) : ViewModel() {
    fun uploadStories(imageFile: File, description: String) = storyRepository.uploadStories(imageFile, description)

    fun logout() {
        viewModelScope.launch {
            storyRepository.logout()
            Log.d(TAG, "Token removed")
        }
    }

    companion object {
        private const val TAG = "AddStoryViewModel"
    }
}