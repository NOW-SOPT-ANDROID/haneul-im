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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.LoginActivity
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import java.util.regex.Pattern

class SignupActivity : ComponentActivity() {
    private val signupViewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    SignupScreen(signupViewModel)
                }
            }
        }
    }
}

@Composable
fun SignupScreen(signupViewModel: SignupViewModel) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }

    val context = LocalContext.current

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

                signupViewModel.signUp(
                    id = id,
                    password = password,
                    nickname = nickname,
                    phoneNumber = phoneNumber,
                    onSuccess = { userId ->
                        Toast.makeText(
                            context,
                            "회원가입 성공 유저의 ID는 $userId 입니둥",
                            Toast.LENGTH_SHORT,
                        ).show()
                        context.startActivity(Intent(context, LoginActivity::class.java))
                    },
                    onFailure = { message ->
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                )
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
