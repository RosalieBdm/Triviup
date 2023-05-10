package com.example.triviup.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.triviup.model.Category
import kotlinx.coroutines.launch

class CategoryViewModel(
    application: Application
) : AndroidViewModel(application) {


    private val _navigateToQuestions = MutableLiveData<Category?>()
    val navigateToQuestions: MutableLiveData<Category?>
        get() {
            return _navigateToQuestions
        }


    fun onCategoryItemClicked(category: Category) {
        Log.d("aaaaaaaaaaaaaaaa", "Clicked on category")
        _navigateToQuestions.value = category
    }

    fun onQuestionsNavigated() {
        _navigateToQuestions.value = null
    }

}