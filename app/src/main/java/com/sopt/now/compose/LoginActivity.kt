package com.sopt.now.compose

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        SignupId = intent.getStringExtra("id"),
                        SignupPassword = intent.getStringExtra("password"),
                        SignupNickname = intent.getStringExtra("nickname"),
                        SignupMbti = intent.getStringExtra("mbti")
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(SignupId: String?, SignupPassword: String?, SignupNickname: String?, SignupMbti: String?) {
    var Id by remember { mutableStateOf("") }
    var Password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Welcome To SOPT",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(80.dp))
        Text("ID")
        TextField(
            value = Id,
            onValueChange = { Id = it },
            label = { Text("사용자 이름 입력") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text("비밀번호")
        TextField(
            value = Password,
            onValueChange = { Password = it },
            label = { Text("비밀번호 입력") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(250.dp))
        val context = LocalContext.current
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            Button(
                onClick = {
                    val intent = Intent(context, SignupActivity::class.java)
                    context.startActivity(intent)
                },
            ) {
                Text("회원가입")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    if(Id==SignupId&&Password==SignupPassword){
                        Toast.makeText(context,"로그인에 성공했습니다", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MainActivity::class.java).apply {
                            putExtra("id", SignupId)
                            putExtra("password", SignupPassword)
                            putExtra("nickname", SignupNickname)
                            putExtra("mbti", SignupMbti)
                        }
                        context.startActivity(intent)
                    }
                    else{
                        Toast.makeText(context,"로그인에 실패했습니다", Toast.LENGTH_SHORT).show()
                    }
                     },
            ) {
                Text("로그인")
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 30.dp))
    }
}
