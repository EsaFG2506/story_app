package com.kikilidyaaaa.storyapp.view.login

import android.util.Log
import androidx.lifecycle.*
import com.kikilidyaaaa.storyapp.data.UserRepository
import com.kikilidyaaaa.storyapp.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
            Log.d(TAG, "Token saved: ${user.token}")
        }
    }

    fun login(email: String, password: String) = userRepository.login(email, password)

    companion object {
        private const val TAG = "LoginViewModel"
    }
}