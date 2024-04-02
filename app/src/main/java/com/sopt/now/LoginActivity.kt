package com.sopt.now

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
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
            val savedid = intent.getStringExtra("id").toString()
            val savedpassword = intent.getStringExtra("password").toString()
            val savednickname = intent.getStringExtra("nickname").toString()
            val savedmbti = intent.getStringExtra("mbti").toString()
            val savedcity = intent.getStringExtra("city").toString()
            if (id == savedid && password == savedpassword) {
                val intent = Intent(this, MainActivity::class.java).apply{
                    putExtra("id", savedid)
                    putExtra("password", savedpassword)
                    putExtra("nickname", savednickname)
                    putExtra("mbti", savedmbti)
                    putExtra("city", savedcity)
                }
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "로그인에 실패했습니다", Toast.LENGTH_LONG).show()
            }

        }

    }
}