package com.example.triviup.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var id: Int = 0,

    var name: String,
    ) : Parcelable
