package com.jsonapiexample.view

import android.support.v7.widget.RecyclerView
import android.view.View
import com.jsonapiexample.model.Author
import kotlinx.android.synthetic.main.item_author.view.*
import java.text.SimpleDateFormat

class AuthorsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(author: Author) {
        author.let {
            view.tvAuthorName.text = it.name
            view.tvAuthorDateOfBirth.text = it.dateOfBirth
            view.tvAuthorDateOfDeath.text = it.dateOfDeath
        }
    }
}