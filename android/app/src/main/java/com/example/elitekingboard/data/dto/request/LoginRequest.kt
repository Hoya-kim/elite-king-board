package com.example.elitekingboard.data.dto.request

data class LoginRequest(
    var email: String,
    var password: String
) {
    constructor() : this("", "")
}
