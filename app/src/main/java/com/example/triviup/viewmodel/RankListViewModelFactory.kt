package com.example.triviup.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.triviup.viewmodel.RankListViewModel
import com.google.firebase.database.DatabaseReference
import java.lang.IllegalArgumentException

class RankListViewModelFactory(private val databaseReference: DatabaseReference, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RankListViewModel::class.java)) {
            return RankListViewModel(application,databaseReference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}