package com.jsonapiexample.model

import com.gustavofao.jsonapi.Annotations.SerialName
import com.gustavofao.jsonapi.Annotations.Type
import com.gustavofao.jsonapi.Models.JSONList
import com.gustavofao.jsonapi.Models.Resource

@Type("authors")
class Author() : Resource() {

    var name: String? = null
    @SerialName("date_of_birth")
    var dateOfBirth: String? = null
    @SerialName("date_of_death")
    var dateOfDeath: String? = null
    var books: JSONList<Book>? = null

    override fun toString(): String {
        return "Author(name=$name, dateOfBirth=$dateOfBirth, dateOfDeath=$dateOfDeath, books=$books)"
    }


}