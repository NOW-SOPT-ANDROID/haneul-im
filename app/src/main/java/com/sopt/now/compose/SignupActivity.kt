package com.sopt.now.compose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.RequestSignUpDto
import com.sopt.now.ResponseSignUpDto
import com.sopt.now.ServicePool
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    SignupScreen()
                }
            }
        }
    }
}

@Composable
fun SignupScreen() {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authService by lazy { ServicePool.authService }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "SIGN UP",
            fontSize = 30.sp,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .align(Alignment.CenterHorizontally)
        )

        TextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("아이디") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("비밀번호") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("핸드폰번호") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("닉네임") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (!isPassword(password)) {
                    Toast.makeText(context, "비밀번호 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (!isPhone(phoneNumber)) {
                    Toast.makeText(context, "전화번호 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val signUpRequest = RequestSignUpDto(id, password, nickname, phoneNumber)
                authService.signUp(signUpRequest).enqueue(object : Callback<ResponseSignUpDto> {
                    override fun onResponse(call: Call<ResponseSignUpDto>, response: Response<ResponseSignUpDto>) {
                        if (response.isSuccessful) {
                            val data: ResponseSignUpDto? = response.body()
                            val userId = response.headers()["location"]
                            Toast.makeText(
                                context,
                                "회원가입 성공 유저의 ID는 $userId 입니둥",
                                Toast.LENGTH_SHORT,
                            ).show()
                            Log.d("SignUp", "data: $data, userId: $userId")
                            context.startActivity(Intent(context, LoginActivity::class.java))
                        } else {
                            Toast.makeText(context, "회원가입 실패", Toast.LENGTH_SHORT).show()
                            // 회원가입 실패 시 처리
                        }
                    }

                    override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                        Toast.makeText(context, "서버 에러 발생", Toast.LENGTH_SHORT).show()
                        Log.e("SignupActivity", "에러 : ${t.message}", t)
                    }
                })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("회원가입")
        }
    }
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
