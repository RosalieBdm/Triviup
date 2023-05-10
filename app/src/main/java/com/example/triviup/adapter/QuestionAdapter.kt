package com.example.triviup.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.triviup.QuestionsFragment
import com.example.triviup.QuestionsFragment.Companion.correctAnswer
import com.example.triviup.R
import com.example.triviup.databinding.QuestionItemBinding
import com.google.android.material.color.ColorRoles
import kotlinx.coroutines.NonDisposableHandle.parent


class QuestionAdapter(private val questionClickListener: QuestionClickListener) :  ListAdapter<String, QuestionAdapter.ViewHolder>(QuestionDiffCallback()){

    class ViewHolder(private var binding: QuestionItemBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        val answerButton = binding.answerButton
        @SuppressLint("ResourceAsColor")
        fun bind(answer: String, questionClickListener: QuestionClickListener) {
            Log.d("zzzzzzzzz", "bind , ${QuestionsFragment.enableButtons}")
            binding.answer = answer
            answerButton.text = answer
            binding.clickListener = questionClickListener
            var backgroundColor = ContextCompat.getColor(context, R.color.purple_500)
            if (QuestionsFragment.enableButtons) {
                answerButton.isEnabled = true
                answerButton.alpha = 1f

            } else {
                answerButton.isEnabled = false
                answerButton.alpha = 0.8f
                backgroundColor = if (answer == correctAnswer){
                    ContextCompat.getColor(context, R.color.green_correct)
                } else {
                    ContextCompat.getColor(context, R.color.red_wrong)
                }
                Log.d("zzzzzzzzz", "color changed")

            }
            answerButton.setTextColor(Color.WHITE)
            answerButton.setBackgroundColor(backgroundColor)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = QuestionItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, parent.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), questionClickListener)
    }


}

class QuestionDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}

class QuestionClickListener(val clickListener: (answer: String) -> Unit) {
    fun onClick(answer: String) = clickListener(answer)
}