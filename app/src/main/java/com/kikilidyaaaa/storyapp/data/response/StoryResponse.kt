package com.kikilidyaaaa.storyapp.data.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryResponse(

	@field:SerializedName("listStory")
	val listStory: List<ListStoryItem> = emptyList(),

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Entity(tableName = "stories")
@Parcelize
data class ListStoryItem(

	@PrimaryKey
	@ColumnInfo(name = "id")
	val id: String,

	@ColumnInfo(name = "name")
	val name: String,

	@ColumnInfo(name = "photoUrl")
	val photoUrl: String,

	@ColumnInfo(name = "description")
	val description: String
) : Parcelable
