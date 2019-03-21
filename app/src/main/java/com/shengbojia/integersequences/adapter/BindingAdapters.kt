package com.shengbojia.integersequences.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:sequenceId")
fun bindSequenceId(view: TextView, id: Int?) {
    if (id != null) {
        view.text = "A${id.toString()}"
    }
}
git