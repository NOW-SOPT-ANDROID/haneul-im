package com.sopt.now.compose

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignupScreen()
                }
            }
        }
    }
}
@Composable
fun SignupScreen() {
    var Id by remember { mutableStateOf("") }
    var Password by remember { mutableStateOf("") }
    var Nickname by remember { mutableStateOf("") }
    var Mbti by remember { mutableStateOf("") }
    var City by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "SIGN UP",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(80.dp))
        Text("ID")
        TextField(
            value = Id,
            onValueChange = { Id = it },
            label = { Text("이름을 입력해주세요") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text("비밀번호")
        TextField(
            value = Password,
            onValueChange = { Password = it },
            label = { Text("비밀번호를 입력해주세요") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Password)

        )
        Spacer(modifier = Modifier.height(30.dp))
        Text("닉네임")
        TextField(
            value = Nickname,
            onValueChange = { Nickname = it },
            label = { Text("닉네임을 입력해주세요") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text("Mbti")
        TextField(
            value = Mbti,
            onValueChange = { Mbti = it },
            label = { Text("Mbti를 입력해주세요") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text("거주지")
        TextField(
            value = City,
            onValueChange = { City = it },
            label = { Text("거주지를 입력해주세요") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.height(70.dp))
        val context = LocalContext.current
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                if (Id.length in 6..10 && Password.length in 8..12 && Nickname.isNotEmpty() && Mbti.length==4&& City.isNotEmpty()) {
                    val intent = Intent(context, LoginActivity::class.java).apply {
                        putExtra("id", Id)
                        putExtra("password", Password)
                        putExtra("nickname", Nickname)
                        putExtra("mbti", Mbti)
                        putExtra("city", City)
                    }
                    Toast.makeText(context,"회원이 되신 것을 축하드려요 !", Toast.LENGTH_SHORT).show()
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context,"정보를 모두 입력해주세요.", Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Text("회원가입")
        }
    }
}