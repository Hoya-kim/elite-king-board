package com.example.elitekingboard.data.api

object RetroInstance {
    val baseUrl = "http"
    val client = BaseRetrofit.getClient(baseUrl).create(RetroService::class.java)
}
