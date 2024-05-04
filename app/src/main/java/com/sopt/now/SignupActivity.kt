package com.sopt.now

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    private val authService by lazy { ServicePool.authService }

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

        authService.signUp(signUpRequest).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseSignUpDto? = response.body()
                    val userId = response.headers()["location"]
                    Toast.makeText(
                        this@SignupActivity,
                        "회원가입 성공 유저의 ID는 $userId 입니둥",
                        Toast.LENGTH_SHORT,
                    ).show()
                    Log.d("SignUp", "data: $data, userId: $userId")
                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                } else {
                    val errorC = response.code()
                    val errorM = response.message()
                    Toast.makeText(
                        this@SignupActivity,
                        "회원가입 실패 $errorC , $errorM",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                Log.e("SignupActivity", "에러 : ${t.message}", t)
                Toast.makeText(this@SignupActivity, "서버 에러 발생 ", Toast.LENGTH_SHORT).show()
            }
        })
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
