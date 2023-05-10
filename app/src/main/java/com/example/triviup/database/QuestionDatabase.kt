package com.example.triviup.database

import android.content.Context
import android.graphics.Movie
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.triviup.model.Question
import com.example.triviup.model.StringListConverter

@Database(entities = [Question::class], version = 2, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class QuestionDatabase : RoomDatabase() {
    abstract val questionDatabaseDao: QuestionDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: QuestionDatabase? = null

        fun getInstance(context: Context): QuestionDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuestionDatabase::class.java,
                        "questions"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}