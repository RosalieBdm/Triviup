package com.example.triviup.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Rank(val username: String? = "", val score: Int? = 0) {
}