package com.example.triviup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.triviup.model.Rank
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ResultFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseReference = Firebase.database.reference
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    fun sendScore(name: String, score: Int) {
        val rank = Rank(name, score)
        val id = databaseReference.push().key!!
        databaseReference.child("Ranking").child(id).setValue(rank)
    }


}