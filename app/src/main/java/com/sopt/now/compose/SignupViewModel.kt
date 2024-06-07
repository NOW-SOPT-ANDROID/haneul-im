package com.sopt.now

import androidx.lifecycle.ViewModel
import com.sopt.now.compose.AuthRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {
    private val authRepository = AuthRepository(ServicePool.authService)

    fun signUp(id: String, password: String, nickname: String, phoneNumber: String, onSuccess: (String?) -> Unit, onFailure: (String) -> Unit) {
        val request = RequestSignUpDto(id, password, nickname, phoneNumber)
        authRepository.signUp(request).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(call: Call<ResponseSignUpDto>, response: Response<ResponseSignUpDto>) {
                if (response.isSuccessful) {
                    val userId = response.headers()["location"]
                    onSuccess(userId)
                } else {
                    onFailure("회원가입 실패")
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                onFailure("서버 에러 발생")
            }
        })
    }
}
