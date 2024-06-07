package com.sopt.now

import com.sopt.now.compose.RequestChangePasswordDto
import com.sopt.now.compose.ResponseChangePasswordDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(private val authService: AuthService) {

    fun login(requestLoginDto: RequestLoginDto, callback: (Result<Pair<ResponseLoginDto, String?>>) -> Unit) {
        authService.login(requestLoginDto).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>
            ) {
                if (response.isSuccessful) {
                    val locationHeader = response.headers()["location"]
                    response.body()?.let {
                        callback(Result.success(Pair(it, locationHeader)))
                    } ?: callback(Result.failure(Exception("")))
                } else {
                    callback(Result.failure(Exception("${response.code()}")))
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }

    fun signUp(requestSignUpDto: RequestSignUpDto, callback: (Result<Pair<ResponseSignUpDto, String?>>) -> Unit) {
        authService.signUp(requestSignUpDto).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>
            ) {
                if (response.isSuccessful) {
                    val locationHeader = response.headers()["location"]
                    response.body()?.let {
                        callback(Result.success(Pair(it, locationHeader)))
                    } ?: callback(Result.failure(Exception("")))
                } else {
                    callback(Result.failure(Exception("${response.code()}")))
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }

    fun changePassword(memberId: String, requestChangePasswordDto: RequestChangePasswordDto, callback: (Result<ResponseChangePasswordDto>) -> Unit) {
        authService.changePassword(memberId, requestChangePasswordDto).enqueue(object : Callback<ResponseChangePasswordDto> {
            override fun onResponse(
                call: Call<ResponseChangePasswordDto>,
                response: Response<ResponseChangePasswordDto>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(Result.success(it))
                    } ?: callback(Result.failure(Exception("")))
                } else {
                    callback(Result.failure(Exception("${response.code()}")))
                }
            }

            override fun onFailure(call: Call<ResponseChangePasswordDto>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }
}
