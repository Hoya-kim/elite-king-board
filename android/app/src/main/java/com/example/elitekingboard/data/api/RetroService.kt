package com.example.elitekingboard.data.api

import com.example.elitekingboard.data.dto.request.LoginRequest
import com.example.elitekingboard.data.dto.request.SignUpRequest
import com.example.elitekingboard.data.dto.response.LoginResponse
import com.example.elitekingboard.data.dto.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetroService {
    @POST("/accounts/sign-up")
    suspend fun requestSignUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>
    @POST("/login")
    suspend fun requestLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
