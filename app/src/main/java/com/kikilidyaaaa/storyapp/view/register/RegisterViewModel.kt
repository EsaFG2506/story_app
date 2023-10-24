package com.kikilidyaaaa.storyapp.view.register

import androidx.lifecycle.ViewModel
import com.kikilidyaaaa.storyapp.data.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) = userRepository.register(name, email, password)
}