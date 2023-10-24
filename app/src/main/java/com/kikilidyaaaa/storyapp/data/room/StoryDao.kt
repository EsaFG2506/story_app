package com.kikilidyaaaa.storyapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kikilidyaaaa.storyapp.data.response.ListStoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStories(stories: List<ListStoryItem>)

    @Query("SELECT * FROM stories")
    fun getAllStories(): LiveData<List<ListStoryItem>>

    @Query("SELECT * FROM stories WHERE id = :id")
    fun getDetailStoryById(id: String): ListStoryItem?
}