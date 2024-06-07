package com.sopt.now

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.sopt.now.compose.LoginActivity
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class PasswordActivity : ComponentActivity() {
    private val passwordViewModel: PasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    PasswordScreen(passwordViewModel, memberId = intent.getStringExtra("memberId"))
                }
            }
        }
    }
}

@Composable
fun PasswordScreen(passwordViewModel: PasswordViewModel, memberId: String?) {
    var previousPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newPasswordVerification by remember { mutableStateOf("") }

    val context = LocalContext.current

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

                if (memberId != null) {
                    passwordViewModel.changePassword(
                        memberId = memberId,
                        previousPassword = previousPassword,
                        newPassword = newPassword,
                        newPasswordVerification = newPasswordVerification,
                        onSuccess = {
                            Toast.makeText(context, "비밀번호가 변경되었습니다! 다시 로그인 해주세요", Toast.LENGTH_SHORT).show()
                            context.startActivity(Intent(context, LoginActivity::class.java))
                        },
                        onFailure = { message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("변경하기")
        }
    }
}
