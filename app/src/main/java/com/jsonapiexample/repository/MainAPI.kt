package com.jsonapiexample.repository

import com.gustavofao.jsonapi.Models.JSONApiObject
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MainAPI {

    @GET("/v2/authors?include=books")
    fun authors(): Single<JSONApiObject>

    @GET("/v2/authors/{author_id}/books")
    fun authorBooks(@Path("author_id") authorId: Int): Single<JSONApiObject>
}