package com.sopt.now.compose

import com.sopt.now.AuthService
import com.sopt.now.RequestLoginDto
import com.sopt.now.RequestSignUpDto
import com.sopt.now.ResponseLoginDto
import com.sopt.now.ResponseSignUpDto
import retrofit2.Call

class AuthRepository(private val authService: AuthService) {
    fun login(request: RequestLoginDto): Call<ResponseLoginDto> {
        return authService.login(request)
    }

    fun signUp(request: RequestSignUpDto): Call<ResponseSignUpDto> {
        return authService.signUp(request)
    }

    fun changePassword(memberId: String, request: RequestChangePasswordDto): Call<ResponseChangePasswordDto> {
        return authService.changePassword(memberId, request)
    }
}
