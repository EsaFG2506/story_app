package com.kikilidyaaaa.storyapp.data.room

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kikilidyaaaa.storyapp.data.response.ListStoryItem

@Database(entities = [ListStoryItem::class], version = 3, exportSchema = false)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): StoryDatabase {
            if (INSTANCE == null) {
                synchronized(StoryDatabase::class.java) {
                    val MIGRATION_2_3: Migration = object : Migration(2, 3) {
                        override fun migrate(database: SupportSQLiteDatabase) {
                            database.execSQL("ALTER TABLE stories ADD COLUMN description TEXT")
                        }
                    }
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        StoryDatabase::class.java, "story.db")
                        .fallbackToDestructiveMigration()
                        .addMigrations(MIGRATION_2_3)
                        .build()
                }
            }
            return INSTANCE as StoryDatabase
        }
    }
}