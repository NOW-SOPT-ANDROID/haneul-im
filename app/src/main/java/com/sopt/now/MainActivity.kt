package com.sopt.now

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sopt.now.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvNickname.setText(intent.getStringExtra("nickname").toString().toUpperCase())
        binding.tvMbti2.setText(intent.getStringExtra("mbti").toString().toUpperCase())
        binding.tvId2.setText(intent.getStringExtra("id").toString())
        binding.tvPassword2.setText(intent.getStringExtra("password").toString())
        binding.tvCity.setText(intent.getStringExtra("city").toString().toUpperCase())
        }

}