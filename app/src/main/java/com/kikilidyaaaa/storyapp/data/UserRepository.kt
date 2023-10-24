package com.kikilidyaaaa.storyapp.data

import androidx.lifecycle.*
import com.google.gson.Gson
import com.kikilidyaaaa.storyapp.data.pref.*
import com.kikilidyaaaa.storyapp.data.response.*
import com.kikilidyaaaa.storyapp.data.retrofit.*
import com.kikilidyaaaa.storyapp.utils.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.*

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun register(name: String, email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val message = apiService.register(name, email, password).message
            emit(Result.Success(message))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(errorMessage?.let { Result.Error(it) })
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val successResponse = apiService.login(email, password)
            emit(Result.Success(successResponse))
            val name = successResponse.loginResult?.name ?: ""
            val token = successResponse.loginResult?.token ?: ""
            val userModel = UserModel(email, name, token, true)
            userPreference.saveSession(userModel)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(errorMessage?.let { Result.Error(it) })
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}