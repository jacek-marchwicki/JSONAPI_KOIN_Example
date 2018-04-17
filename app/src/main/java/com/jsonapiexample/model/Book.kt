package com.jsonapiexample.model

import com.gustavofao.jsonapi.Annotations.SerialName
import com.gustavofao.jsonapi.Annotations.Type
import com.gustavofao.jsonapi.Models.Resource


@Type("books")
class Book() : Resource() {

    var title: String? = null
    @SerialName("date_published")
    var datePublished: String? = null
    var isbn: String? = null

    override fun toString(): String {
        return "Book(title=$title, datePublished=$datePublished, isbn=$isbn)"
    }
}