package com.sopt.now.compose

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        Id = intent.getStringExtra("id"),
                        Password = intent.getStringExtra("password"),
                        Nickname = intent.getStringExtra("nickname"),
                        Mbti = intent.getStringExtra("mbti"),
                        City = intent.getStringExtra("city")
                    )
                }
            }
        }
    }
}


@Composable
fun MainScreen(Id: String?, Password: String?, Nickname: String?, Mbti: String?,City: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        Spacer(modifier = Modifier.height(70.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (Nickname != null) {
                Text(
                    text = Nickname.toUpperCase(),
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            if (City != null) {
                Text(
                    text = City.toUpperCase(),
                    fontSize = 40.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(150.dp))
        Text(
            text ="ID",
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (Id != null) {
            Text(
                text = Id,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text ="비밀번호",
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (Password != null) {
            Text(
                text = Password,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text ="MBTI",
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (Mbti != null) {
            Text(
                text = Mbti.toUpperCase(),
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}