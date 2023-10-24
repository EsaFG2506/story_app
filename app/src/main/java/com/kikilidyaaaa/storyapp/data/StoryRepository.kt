package com.kikilidyaaaa.storyapp.data

import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.kikilidyaaaa.storyapp.data.pref.*
import com.kikilidyaaaa.storyapp.data.response.ErrorResponse
import com.kikilidyaaaa.storyapp.data.response.FileUploadResponse
import com.kikilidyaaaa.storyapp.data.retrofit.ApiService
import com.kikilidyaaaa.storyapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class StoryRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun getStories() = liveData {
        emit(Result.Loading)
        try {
            val successResponse = apiService.getStories()
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(errorMessage?.let { Result.Error(it) })
        }
    }

    fun getDetailStories(id: String) = liveData {
        try {
            emit(Result.Loading)
            val successResponse = apiService.getDetailStories(id)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(errorMessage?.let { Result.Error(it) })
        }
    }

    fun uploadStories(imageFile: File, description: String) = liveData {
        try {
            emit(Result.Loading)
            val result = withContext(Dispatchers.IO) {
                val requestBody = description.toRequestBody("text/plain".toMediaType())
                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val multipartBody = MultipartBody.Part.createFormData("photo", imageFile.name, requestImageFile)
                apiService.uploadImage(multipartBody, requestBody).execute()
            }

            emit(Result.Success(result))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, FileUploadResponse::class.java)
            val errorMessage = errorBody.message
            emit(errorMessage?.let { Result.Error(it) })
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(userPreference, apiService)
            }.also { instance = it }
    }
}