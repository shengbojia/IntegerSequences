package com.shengbojia.integersequences.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shengbojia.integersequences.databinding.ListItemSequenceBinding
import com.shengbojia.integersequences.model.IntSequence

class SequenceHolder(
    private val binding: ListItemSequenceBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(listener: View.OnClickListener, item: IntSequence) {
        binding.apply {
            clickListener = listener
            intSequence = item
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup): SequenceHolder {
            return SequenceHolder(ListItemSequenceBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))
        }
    }
}