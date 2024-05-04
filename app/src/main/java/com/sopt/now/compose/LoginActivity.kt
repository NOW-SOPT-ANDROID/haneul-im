package com.sopt.now.compose

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
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
import com.sopt.now.RequestLoginDto
import com.sopt.now.ResponseLoginDto
import com.sopt.now.ServicePool
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authService by lazy { ServicePool.authService }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome To SOPT",
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(80.dp))
        TextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("아이디") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("비밀번호") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, SignupActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text("회원가입")
            }
            Button(
                onClick = {
                    val request = RequestLoginDto(authenticationId = id, password = password)
                    authService.login(request).enqueue(object : Callback<ResponseLoginDto> {
                        override fun onResponse(call: Call<ResponseLoginDto>, response: Response<ResponseLoginDto>) {
                            if (response.isSuccessful) {
                                val loginResponse = response.body()
                                if (loginResponse?.code == 200) {
                                    val intent = Intent(context, MainActivity::class.java)
                                    context.startActivity(intent)
                                } else {
                                    Toast.makeText(context, "로그인 실패! 아이디와 비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "로그인에 실패했습니다!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                            Toast.makeText(context, "서버와의 통신에 실패했습니다!", Toast.LENGTH_SHORT).show()
                        }
                    })
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("로그인")
            }
        }
    }
}

