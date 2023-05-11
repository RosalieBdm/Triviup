package com.example.triviup.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.triviup.database.QuestionDatabaseDao
import com.example.triviup.model.Category
import java.lang.IllegalArgumentException

class QuestionsViewModelFactory(
    private val category : Category,
    private val difficulty : String,
    private val questionDatabaseDao: QuestionDatabaseDao,
    private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(QuestionsViewModel::class.java)) {
            return QuestionsViewModel(category, difficulty, questionDatabaseDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}