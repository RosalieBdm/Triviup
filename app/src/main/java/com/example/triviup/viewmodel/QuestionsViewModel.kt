package com.example.triviup.viewmodel

import android.app.Application
import android.text.Html
import androidx.lifecycle.*
import com.example.triviup.QuestionsFragment
import com.example.triviup.database.QuestionDatabaseDao
import com.example.triviup.model.Category
import com.example.triviup.model.Question
import com.example.triviup.network.DataFetchStatus
import com.example.triviup.network.QuestionResponse
import com.example.triviup.network.TriviaApi
import kotlinx.coroutines.launch
import timber.log.Timber

class QuestionsViewModel(
    private val category : Category,
    private val difficulty: String,
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
        val answerHtml = Html.fromHtml(answer, Html.FROM_HTML_MODE_LEGACY)
            .toString()
        if (answerHtml == fragment.currentQuestion.correct_answer){
            fragment.waitNextQuestion(true)
        }else {
            fragment.waitNextQuestion(false)
        }
    }



    fun getQuestions( ) {
        viewModelScope.launch {
            try {
                val questionResponse: QuestionResponse =
                    TriviaApi.questionRetrofitService.getQuestions(10, category.id, difficulty)
                _questionList.value = questionResponse.results
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception) {
                Timber.tag("error").d("pas de questions: %s", e.message)
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _questionList.value = arrayListOf()
            }
            questionDatabaseDao.insertAll(_questionList.value!!)
        }
    }
}