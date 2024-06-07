package com.sopt.now

import androidx.lifecycle.ViewModel
import com.sopt.now.compose.AuthRepository
import com.sopt.now.compose.RequestChangePasswordDto
import com.sopt.now.compose.ResponseChangePasswordDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordViewModel : ViewModel() {
    private val authRepository = AuthRepository(ServicePool.authService)

    fun changePassword(memberId: String, previousPassword: String, newPassword: String, newPasswordVerification: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val request = RequestChangePasswordDto(previousPassword, newPassword, newPasswordVerification)
        authRepository.changePassword(memberId, request).enqueue(object : Callback<ResponseChangePasswordDto> {
            override fun onResponse(call: Call<ResponseChangePasswordDto>, response: Response<ResponseChangePasswordDto>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure("비밀번호 변경에 실패했습니다")
                }
            }

            override fun onFailure(call: Call<ResponseChangePasswordDto>, t: Throwable) {
                onFailure("서버 에러 발생")
            }
        })
    }
}
