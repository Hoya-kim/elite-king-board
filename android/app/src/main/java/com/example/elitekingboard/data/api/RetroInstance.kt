package com.example.elitekingboard.data.api

object RetroInstance {
    val baseUrl = "http://10.0.2.2:8080"
    val client = BaseRetrofit.getClient(baseUrl).create(RetroService::class.java)
}
