package com.example.triviup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.triviup.databinding.RankingItemBinding
import com.example.triviup.model.Rank

class RankListAdapter : ListAdapter<Rank, RankListAdapter.ViewHolder>(RankListDiffCallback()) {

    class ViewHolder(private var binding: RankingItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(rank: Rank, position : Int) {
            binding.rank = rank
            binding.position = position + 1
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RankingItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),position)
    }
}

class RankListDiffCallback : DiffUtil.ItemCallback<Rank>() {
    override fun areItemsTheSame(oldItem: Rank, newItem: Rank): Boolean {
        return oldItem.username == newItem.username && oldItem.score == newItem.score
    }

    override fun areContentsTheSame(oldItem: Rank, newItem: Rank): Boolean {
        return oldItem == newItem
    }
}