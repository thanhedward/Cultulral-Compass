package me.ppvan.metour.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.ppvan.metour.ui.component.CommonLoginButton
import me.ppvan.metour.ui.component.CommonText
import me.ppvan.metour.ui.component.CommonTextField
import me.ppvan.metour.ui.component.TopAppBarMinimalTitle
import me.ppvan.metour.viewmodel.LoginState

@Composable
fun LoginView(
    state: LoginState,
    onLoginClick: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val formValid = username.isNotBlank() and password.isNotBlank()
    val context = LocalContext.current

    LaunchedEffect(key1 = state) {
        when (state) {
            LoginState.Idle -> {}
            LoginState.Success -> {
                Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
            }

            LoginState.Failed -> {
                Toast.makeText(
                    context,
                    "Tài khoản hoặc mật khẩu không chính xác",
                    Toast.LENGTH_SHORT
                ).show()
            }

            LoginState.Done -> TODO()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp, top = 20.dp, bottom = 20.dp)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TopAppBarMinimalTitle {
                Text(text = "Đăng nhập")
            }
            Spacer(modifier = Modifier.height(20.dp))

            if (state == LoginState.Failed) {
                Text(
                    text = "Tài khoản hoặc mật khẩu không chính xác",
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            Column {
                CommonTextField(
                    text = username,
                    placeholder = "Tên người dùng",
                    onValueChange = { username = it },
                    isPasswordTextField = false,
                    keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(16.dp))
                CommonTextField(
                    text = password,
                    placeholder = "Mật khẩu",
                    onValueChange = { password = it },
                    isPasswordTextField = true,
                    keyboardOption = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                Spacer(modifier = Modifier.height(20.dp))
                CommonLoginButton(
                    text = "Đăng nhập",
                    enable = formValid,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onLoginClick(username, password)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CommonText(
                    text = "Chưa có tài khoản?",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ) {}
                Spacer(modifier = Modifier.width(4.dp))
                CommonText(
                    text = "Đăng ký",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                ) {
                    onNavigateToRegister()
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginPreview() {
    LoginView(LoginState.Idle, { _, _ -> }, {})
}