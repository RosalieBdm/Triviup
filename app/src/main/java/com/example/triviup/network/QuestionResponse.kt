package com.example.triviup.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.example.triviup.model.Question


@JsonClass(generateAdapter = true)
class QuestionResponse {
    @Json(name = "response_code")
    var page: Int = 0;

    @Json(name = "results")
    var results: List<Question> = listOf()

}