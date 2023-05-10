package com.example.triviup.viewmodel

import android.app.Application
import android.graphics.Movie
import android.text.Html
import android.util.Log
import androidx.lifecycle.*
import com.example.triviup.QuestionsFragment
import com.example.triviup.QuestionsFragmentArgs
import com.example.triviup.database.QuestionDatabase
import com.example.triviup.database.QuestionDatabaseDao
import com.example.triviup.model.Category
import com.example.triviup.model.Question
import com.example.triviup.network.DataFetchStatus
import com.example.triviup.network.QuestionResponse
import com.example.triviup.network.TriviaApi
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class QuestionsViewModel(
    private val category : Category,
    private val questionDatabaseDao : QuestionDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() {
            return _question
        }



    private val _questionList = MutableLiveData<List<Question>>()
    val questionList: LiveData<List<Question>>
        get() {
            return _questionList
        }

    init {
        getQuestions()
        _dataFetchStatus.value = DataFetchStatus.LOADING
    }

    fun deleteQuestions(){
        viewModelScope.launch {
            questionDatabaseDao.deleteAll()
        }
    }

    fun onQuestionItemClicked(answer: String, fragment : QuestionsFragment) {
        Log.d("aaaaaaaaaaaaaaaa", "Clicked on answer, ${answer} / ${fragment.currentQuestion.correct_answer}")
        var answerHtml = Html.fromHtml(answer, Html.FROM_HTML_MODE_LEGACY)
            .toString()
        if (answerHtml == fragment.currentQuestion.correct_answer){
            Log.d("aaaaaaaaaaaaaaaa", "correct answer")
            fragment.waitNextQuestion(true)

        }else {
            Log.d("aaaaaaaaaaaaaaaa", "wrong answer")
            fragment.waitNextQuestion(false)
        }



        Log.d("aaaaaaaaaaaaaaaa", "Next question")
    }



    fun getQuestions() {
        Log.d("API_CALL", "Calling API with category id ${category.id}")
        viewModelScope.launch {
            try {
                val questionResponse: QuestionResponse =
                    TriviaApi.questionRetrofitService.getQuestions(10, category.id, "multiple")
                _questionList.value = questionResponse.results
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception) {
                Log.d("error", "pas de questions: ${e.message}")
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _questionList.value = arrayListOf()
            }
            questionDatabaseDao.insertAll(_questionList.value!!)
        }
    }
}