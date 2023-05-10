package com.example.triviup.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.triviup.model.Question


@Dao
interface QuestionDatabaseDao {
    @Insert
    suspend fun insert(question: Question)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(questionList: List<Question>)

    @Delete
    suspend fun delete(question: Question)

    @Query("DELETE FROM questions")
    suspend fun deleteAll()

    @Query("SELECT * FROM questions WHERE id = :questionId")
    fun getQuestionById(questionId: Int): Question

    @Query("SELECT * FROM questions ORDER BY id ASC LIMIT 1")
    suspend fun getFirstQuestion(): Question

    @Query("SELECT * from questions")
    suspend fun getAllQuestions(): List<Question>

    @Query("SELECT * from questions")
    suspend fun getNextQuestion(): List<Question>
}