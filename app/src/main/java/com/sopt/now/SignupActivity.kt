package com.sopt.now

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivitySignupBinding
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    private val authService by lazy { ServicePool.authService }
    private val authRepository by lazy { AuthRepository(authService) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.btSignUp.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val signUpRequest = getSignUpRequestDto() ?: return

        authRepository.signUp(signUpRequest) { result ->
            result.onSuccess { (signUpResponse, locationHeader) ->
                val userId = locationHeader
                Toast.makeText(
                    this@SignupActivity,
                    "회원가입 성공 유저의 ID는 $userId 입니다",
                    Toast.LENGTH_SHORT,
                ).show()
                Log.d("SignUp", "data: $signUpResponse, userId: $userId")
                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            }.onFailure {
                val errorC = it.localizedMessage
                Toast.makeText(
                    this@SignupActivity,
                    "회원가입 실패: $errorC",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    private fun getSignUpRequestDto(): RequestSignUpDto? {
        val id = binding.etId.text.toString()
        val password = binding.etPassword.text.toString()
        val nickname = binding.etNickname.text.toString()
        val phoneNumber = binding.etPhone.text.toString()

        if (!isPassword(password)) {
            Toast.makeText(this@SignupActivity, "비밀번호는 8자 이상이어야 하며, 숫자, 문자, 특수문자를 포함해야 합니다.", Toast.LENGTH_SHORT).show()
            return null
        }

        if (!isPhone(phoneNumber)) {
            Toast.makeText(this@SignupActivity, "전화번호 형식이 올바르지 않습니다. (예: 010-0000-0000)", Toast.LENGTH_SHORT).show()
            return null
        }

        return RequestSignUpDto(
            authenticationId = id,
            password = password,
            nickname = nickname,
            phone = phoneNumber
        )
    }

    private fun isPassword(password: String): Boolean {
        if (password.length < 8) {
            return false
        }
        val pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).*$")
        return pattern.matcher(password).matches()
    }

    private fun isPhone(phoneNumber: String): Boolean {
        val pattern = Pattern.compile("^010-[0-9]{4}-[0-9]{4}$")
        return pattern.matcher(phoneNumber).matches()
    }
}
