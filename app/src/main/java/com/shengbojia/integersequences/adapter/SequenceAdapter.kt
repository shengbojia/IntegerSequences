package com.shengbojia.integersequences.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shengbojia.integersequences.model.IntSequence
import com.shengbojia.integersequences.ui.SearchResultFragmentDirections

class SequenceAdapter : PagedListAdapter<IntSequence, RecyclerView.ViewHolder>(SequenceDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            SequenceHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sequence = getItem(position) ?: IntSequence(
            999999,
            "",
            "Loading...",
            emptyList())

        sequence.let {
            with(holder as SequenceHolder) {
                this.itemView.tag = it
                this.bind(createOnClickListener(it.numberId), it)
            }
        }
    }

    private fun createOnClickListener(sequenceId: Int): View.OnClickListener {
        return View.OnClickListener {
            Log.d(TAG, "onClick $sequenceId")
        }
    }

    companion object {
        private const val TAG = "AdapterSequence"
    }
}

private class SequenceDiffCallBack : DiffUtil.ItemCallback<IntSequence>() {

    override fun areItemsTheSame(oldItem: IntSequence, newItem: IntSequence): Boolean {
        return oldItem.numberId == newItem.numberId
    }

    override fun areContentsTheSame(oldItem: IntSequence, newItem: IntSequence): Boolean {
        return oldItem == newItem
    }
}