package me.ppvan.metour.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.ppvan.metour.ui.theme.LightGrayColor

@Composable
fun CommonText(
    text: String,
    color: Color = Color.Black,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    function: () -> Unit
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        modifier = Modifier.clickable {
            function()
        }
    )
}

@Composable
fun CommonLoginButton(
    text: String,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    Surface {
        Button(
            modifier = modifier
                .height(58.dp),
            onClick = { onClick() },
            enabled = enable
        ) {
            Text(text = text, fontSize = 20.sp, color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(
    text: String,
    placeholder: String,
    isPasswordTextField: Boolean,
    keyboardOption: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    valid: Boolean = true,
    onValueChange: (String) -> Unit
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }


    OutlinedTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        label = { Text(text = placeholder, color = LightGrayColor) },
        maxLines = 1,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (valid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (isPasswordTextField and !passwordVisible) PasswordVisualTransformation()
        else VisualTransformation.None,
        keyboardOptions = keyboardOption,
        trailingIcon = {
            if (text.isNotBlank() and isPasswordTextField) {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        })
}