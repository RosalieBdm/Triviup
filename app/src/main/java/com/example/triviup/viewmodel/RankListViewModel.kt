package com.example.triviup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.triviup.model.Rank
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import timber.log.Timber

class RankListViewModel(application: Application, databaseReference: DatabaseReference) :  AndroidViewModel(application) {

    private val _rankList = MutableLiveData<List<Rank>>()
    val rankList: LiveData<List<Rank>>
        get() {
            return _rankList
        }

    init {
        databaseReference.addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val ranks = snapshot.child("Ranking").children.mapNotNull { it.getValue<Rank>() }
                _rankList.value = ranks.sortedByDescending { it.score }
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.tag("Error")
                    .w(Exception("Error loadPost:onCancelled"), "loadPost:onCancelled")
            }

        })
    }
}