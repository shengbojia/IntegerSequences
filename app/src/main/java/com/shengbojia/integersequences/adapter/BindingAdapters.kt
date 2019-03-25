package com.shengbojia.integersequences.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:sequenceId")
fun bindSequenceId(view: TextView, id: Int?) {
    if (id != null) {
        view.text = "A${id.toString()}"
    }
}

@BindingAdapter("app:totalResultsFound")
fun bindTotalResultsFound(view: TextView, totalCount: Int?) {
    if (totalCount != null) {
        view.text = "${totalCount.toString()}"
    } else {
        view.text = "0"
    }
}
