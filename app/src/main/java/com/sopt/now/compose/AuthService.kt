package com.sopt.now


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("member/join")
    fun signUp(
        @Body request: RequestSignUpDto,
    ): Call<ResponseSignUpDto>
    @POST("member/login")
    fun login(
        @Body request: RequestLoginDto
    ): Call<ResponseLoginDto>
}