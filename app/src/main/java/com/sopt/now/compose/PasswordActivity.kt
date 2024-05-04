package com.sopt.now.compose

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.unit.dp
import com.sopt.now.ServicePool
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PasswordScreen(memberId = intent.getStringExtra("memberId"))
                }
            }
        }
    }
}

@Composable
fun PasswordScreen(memberId: String?) {
    var previousPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newPasswordVerification by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authService by lazy { ServicePool.authService }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = previousPassword,
            onValueChange = { previousPassword = it },
            label = { Text("이전 비밀번호") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("새로운 비밀번호") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = newPasswordVerification,
            onValueChange = { newPasswordVerification = it },
            label = { Text("새로운 비밀번호 확인") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                if (newPassword != newPasswordVerification) {
                    Toast.makeText(context, "새로운 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val changePasswordRequest = RequestChangePasswordDto(
                    previousPassword = previousPassword,
                    newPassword = newPassword,
                    newPasswordVerification = newPasswordVerification
                )

                if (memberId != null) {
                    authService.changePassword(memberId, changePasswordRequest).enqueue(object : Callback<ResponseChangePasswordDto> {
                        override fun onResponse(call: Call<ResponseChangePasswordDto>, response: Response<ResponseChangePasswordDto>) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "비밀번호가 변경되었습니다! 다시 로그인 해주세요", Toast.LENGTH_SHORT).show()
                                context.startActivity(Intent(context, LoginActivity::class.java))
                            } else {
                                Toast.makeText(context, "비밀번호 변경에 실패했습니다ㅠ", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseChangePasswordDto>, t: Throwable) {
                            Toast.makeText(context, "서버 에러 발생", Toast.LENGTH_SHORT).show()
                            Log.e("PasswordActivity", "에러 : ${t.message}", t)
                        }
                    })
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("변경하기")
        }
    }
}
