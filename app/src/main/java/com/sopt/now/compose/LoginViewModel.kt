package com.sopt.now.compose

import androidx.lifecycle.ViewModel
import com.sopt.now.RequestLoginDto
import com.sopt.now.ResponseLoginDto
import com.sopt.now.ServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository(ServicePool.authService)

    fun login(id: String, password: String, onSuccess: (String?) -> Unit, onFailure: (String) -> Unit) {
        val request = RequestLoginDto(id, password)
        authRepository.login(request).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(call: Call<ResponseLoginDto>, response: Response<ResponseLoginDto>) {
                if (response.isSuccessful && response.body()?.code == 200) {
                    val userId = response.headers()["location"]
                    onSuccess(userId)
                } else {
                    onFailure("로그인 실패! 아이디와 비밀번호를 다시 확인해주세요.")
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                onFailure("서버와의 통신에 실패했습니다!")
            }
        })
    }
}
