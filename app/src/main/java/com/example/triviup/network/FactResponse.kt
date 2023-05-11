package com.example.triviup.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.example.triviup.model.Question


@JsonClass(generateAdapter = true)
class FactResponse {
    @Json(name = "fact")
    var fact: String = ""
}