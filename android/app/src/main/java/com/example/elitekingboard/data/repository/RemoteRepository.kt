package com.example.elitekingboard.data.repository

import com.example.elitekingboard.data.api.RetroInstance
import com.example.elitekingboard.data.dto.request.LoginRequest
import com.example.elitekingboard.data.dto.request.SignUpRequest

class RemoteRepository {
    suspend fun requestSignUp(signUpRequest: SignUpRequest) = RetroInstance.client.requestSignUp(signUpRequest)
    suspend fun requestLogin(loginRequest: LoginRequest) = RetroInstance.client.requestLogin(loginRequest)
}
