package com.sopt.now


import com.sopt.now.compose.RequestChangePasswordDto
import com.sopt.now.compose.ResponseChangePasswordDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
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
    @PATCH("member/password")
    fun changePassword(
        @Header("memberId") memberId: String,
        @Body request: RequestChangePasswordDto
    ): Call<ResponseChangePasswordDto>

}