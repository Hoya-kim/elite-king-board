package com.example.elitekingboard.data.dto.request

data class SignUpRequest(
    var nickname: String,
    var password: String,
    var email: String
) {
    constructor(): this("", "", "")
}
