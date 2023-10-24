package com.kikilidyaaaa.storyapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kikilidyaaaa.storyapp.data.StoryRepository
import com.kikilidyaaaa.storyapp.di.Injection
import com.kikilidyaaaa.storyapp.view.addstory.AddStoryViewModel
import com.kikilidyaaaa.storyapp.view.detail.DetailViewModel
import com.kikilidyaaaa.storyapp.view.main.MainViewModel

class StoryViewModelFactory(private val storyRepository: StoryRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(storyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StoryViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): StoryViewModelFactory {
            if (INSTANCE == null) {
                synchronized(StoryViewModelFactory::class.java) {
                    INSTANCE = StoryViewModelFactory(Injection.provideStoryRepository(context))
                }
            }
            return INSTANCE as StoryViewModelFactory
        }
    }
}