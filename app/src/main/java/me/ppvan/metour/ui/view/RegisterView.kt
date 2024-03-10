package me.ppvan.metour.ui.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.ppvan.metour.data.User
import me.ppvan.metour.ui.component.CommonLoginButton
import me.ppvan.metour.ui.component.CommonText
import me.ppvan.metour.ui.component.CommonTextField
import me.ppvan.metour.ui.component.TopAppBarMinimalTitle
import me.ppvan.metour.ui.component.rememberImeState
import me.ppvan.metour.viewmodel.RegisterState

@SuppressLint("ShowToast")
@Composable
//navController: NavController
fun RegisterView(
    state: RegisterState,
    onRegisterClick: (User) -> Unit,
    onLoginClick: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember {
        mutableStateOf("")
    }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val valid = listOf(username, email, phoneNumber, password).all { it.isNotBlank() }
    val passwordMatch = password.equals(confirmPassword)

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }
    LaunchedEffect(key1 = state) {
        when (state) {
            RegisterState.Success -> Toast.makeText(
                context,
                "Đăng ký thành công",
                Toast.LENGTH_SHORT
            ).show()

            RegisterState.Fail -> Toast.makeText(
                context,
                "Đăng ký thất bại, hãy thử lại sau",
                Toast.LENGTH_SHORT
            ).show()

            else -> {

            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp, top = 20.dp, bottom = 20.dp)
            .navigationBarsPadding()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .imePadding()
        ) {
            TopAppBarMinimalTitle {
                Text(text = "Đăng ký")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                CommonTextField(
                    text = fullName,
                    placeholder = "Họ và Tên",
                    onValueChange = { fullName = it },
                    isPasswordTextField = false
                )
                Spacer(modifier = Modifier.height(20.dp))

                CommonTextField(
                    text = email,
                    placeholder = "Email",
                    keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { email = it },
                    isPasswordTextField = false
                )
                Spacer(modifier = Modifier.height(16.dp))
                CommonTextField(
                    text = phoneNumber,
                    placeholder = "Số điện thoại",
                    onValueChange = { phoneNumber = it },
                    keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isPasswordTextField = false
                )
                Spacer(modifier = Modifier.height(16.dp))
                CommonTextField(
                    text = username,
                    placeholder = "Tên người dùng",
                    onValueChange = { username = it },
                    isPasswordTextField = false
                )
                Spacer(modifier = Modifier.height(16.dp))
                CommonTextField(
                    text = password,
                    placeholder = "Mật khẩu",
                    onValueChange = { password = it },
                    isPasswordTextField = true,
                    valid = passwordMatch,
                    keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                Spacer(modifier = Modifier.height(16.dp))
                CommonTextField(
                    text = confirmPassword,
                    placeholder = "Xác nhận mật khẩu",
                    onValueChange = { confirmPassword = it },
                    isPasswordTextField = true,
                    valid = passwordMatch,
                    keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            CommonLoginButton(
                text = "Đăng ký",
                enable = valid and passwordMatch,
                modifier = Modifier.fillMaxWidth()
            ) {
                val user = User(
                    fullName = fullName.trim(),
                    phoneNumber = phoneNumber.trim(),
                    avatarUrl = "",
                    username = username.trim(),
                    email = email.trim(),
                    password = password.trim(),
                    city = "Hà Nội"
                )
                onRegisterClick(user)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CommonText(
                    text = "Đã có tài khoản?",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ) {}
                Spacer(modifier = Modifier.width(4.dp))
                CommonText(
                    text = "Đăng nhập",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                ) {
                    onLoginClick()
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RegisterPreview() {
    RegisterView(state = RegisterState.Idle, onLoginClick = {}, onRegisterClick = {})
}