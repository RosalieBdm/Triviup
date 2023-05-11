package com.example.triviup.model

import android.os.Parcelable
import androidx.room.*
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "question")
    @Json(name = "question")
    var question: String = "",

    @ColumnInfo(name = "category")
    @Json(name = "category")
    var category: String,

    @ColumnInfo(name = "type")
    @Json(name = "type")
    var type: String,

    @ColumnInfo(name = "difficulty")
    @Json(name = "difficulty")
    var difficulty: String,

    @ColumnInfo(name = "correct_answer")
    @Json(name = "correct_answer")
    var correct_answer: String,

    @ColumnInfo(name = "incorrect_answers")
    @Json(name = "incorrect_answers")
    @TypeConverters(StringListConverter::class)
    var incorrect_answers: List<String>

) : Parcelable


class StringListConverter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(string: String): List<String> {
        return string.split(",")
    }
}
