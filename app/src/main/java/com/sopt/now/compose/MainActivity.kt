package com.sopt.now.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(Id = intent.getStringExtra("id"),
                        Password = intent.getStringExtra("password"),
                        Nickname = intent.getStringExtra("nickname"),
                        Mbti = intent.getStringExtra("mbti"),
                        City = intent.getStringExtra("city"),
                        memberId = intent.getStringExtra("memberId"))

                }
            }
        }
    }
}

@Composable
fun Scaffold(Id: String?, Password: String?, Nickname: String?, Mbti: String?,City: String?, memberId: String?) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val viewModel = HomeViewModel()
    val items = listOf(
        BottomNavigationItem(
            icon = Icons.Filled.Home,
            label = "Home"
        ),
        BottomNavigationItem(
            icon = Icons.Filled.Search,
            label = "Search"
        ),
        BottomNavigationItem(
            icon = Icons.Filled.Person,
            label = "Profile"
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            when(selectedItem) {
                0 -> {
                    LazyColumn {
                        item {
                            MyProfileItem(viewModel.profile)
                        }
                        items(viewModel.mockFriendList) {
                            FriendProfileItem(it)
                        }
                    }
                }
                1 -> {
                    Text(text ="Search")
                }
                2 -> {
                    val context = LocalContext.current
                    Button(
                        onClick = {
                            val intent = Intent(context, PasswordActivity::class.java).apply {
                                putExtra("memberId", memberId)
                            }
                            context.startActivity(intent)
                        }
                    ) {
                        Text("비밀번호 변경")

                    }
                }
            }
        }
    }
}

@Composable
fun FriendProfileItem(friend: Friend) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape),
            painter = painterResource(id = friend.profileImage),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = friend.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(10.dp))
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = if (friend.selfDescription.length > 20) {
                friend.selfDescription.take(15) + "..."
            } else {
                friend.selfDescription
            },
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.width(16.dp))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileItem(profile: Profile) {
    var isEditingDescription by remember { mutableStateOf(false) }
    var editedDescription by remember { mutableStateOf(profile.selfDescription) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = profile.profileImage),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text =profile.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (isEditingDescription) {
                    OutlinedTextField(
                        value = editedDescription,
                        onValueChange = { editedDescription = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp),
                        singleLine = true,
                        label = { Text("Description") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                isEditingDescription = false
                                profile.selfDescription = editedDescription
                            }
                        )
                    )
                } else {
                    Text(
                        text = profile.selfDescription,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            IconButton(
                onClick = {
                    isEditingDescription = true
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            }
        }
    }
}
