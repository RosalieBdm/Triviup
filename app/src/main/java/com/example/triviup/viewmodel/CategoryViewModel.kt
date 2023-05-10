package com.example.triviup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.triviup.model.Category

class CategoryViewModel(
    application: Application
) : AndroidViewModel(application) {


    private val _navigateToQuestions = MutableLiveData<Category?>()
    val navigateToQuestions: MutableLiveData<Category?>
        get() {
            return _navigateToQuestions
        }


    fun onCategoryItemClicked(category: Category) {
        _navigateToQuestions.value = category
    }

    fun onQuestionsNavigated() {
        _navigateToQuestions.value = null
    }

}