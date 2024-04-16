package com.sopt.now

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignup.setOnClickListener {
            val id = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()
            val nickname = binding.etNickname.text.toString()
            val mbti = binding.etMbti.text.toString()
            val city = binding.etCity.text.toString()
            if(id.length in 6..10&&password.length in 8..12&&nickname.isNotEmpty()&&mbti.isNotEmpty()&&city.isNotEmpty()){
                Toast.makeText(getApplicationContext(),"회원이 되신 것을 축하드려요 !", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra("id", id)
                    putExtra("password", password)
                    putExtra("nickname", nickname)
                    putExtra("mbti", mbti)
                    putExtra("city", city)
                }
                startActivity(intent)
            }
            else{
                Toast.makeText(getApplicationContext(),"정보를 다시 입력해주세요.", Toast.LENGTH_LONG).show()
            }


        }

    }
}