package com.jsonapiexample.view

import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jsonapiexample.R
import com.jsonapiexample.model.Author

class AuthorsRecyclerAdapter : RecyclerView.Adapter<AuthorsViewHolder>() {

    var items: List<Author> = listOf()
//        set(value) {
//            items = value
//            notifyDataSetChanged()
//        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AuthorsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_author, parent, false))

    override fun onBindViewHolder(holder: AuthorsViewHolder, position: Int) {
        holder.bind(items[position])
    }

}