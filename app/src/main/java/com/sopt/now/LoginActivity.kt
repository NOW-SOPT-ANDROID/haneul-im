package com.sopt.now

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authService by lazy { ServicePool.authService }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            val id = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()
            val requestLoginDto = RequestLoginDto(
                authenticationId = id,
                password = password
            )
            authService.login(requestLoginDto).enqueue(object : Callback<ResponseLoginDto> {
                override fun onResponse(
                    call: Call<ResponseLoginDto>,
                    response: Response<ResponseLoginDto>,
                ) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse?.code == 200) {
                            val memberId = response.headers()["location"]
                            Toast.makeText(
                                this@LoginActivity,
                                "로그인 성공 유저의 ID는 $memberId 입니둥",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val bundle = Bundle().apply {
                                putString("memberId", memberId)
                            }

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtras(bundle)
                            startActivity(intent)

                        } else {
                            Toast.makeText(applicationContext, "로그인 실패 ! 아이디와 비밀번호를 다시 한번 확인해주세요.", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "로그인에 실패했습니다 !", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                    Toast.makeText(applicationContext, "서버와 통신에 실패했습니다 !", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
