package me.ppvan.metour.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DialogScreen(modifier: Modifier, dialogState: Boolean, onDismissRequest: () -> Unit) {
    if (dialogState) {
        Dialog(
            onDismissRequest = onDismissRequest,
            content = {
                Card(
                    modifier = modifier
                        .width(300.dp)
                        .height(150.dp),

                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.fillMaxSize(),
                    ) {
                        Text(
                            text = "Coming Soon!",
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
//                            fontFamily = poppinsFamily,
                            modifier = modifier
                                .weight(1f)
                                .wrapContentSize(Alignment.Center)
                        )
                        Spacer(modifier = modifier.height(12.dp))
                        Button(
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(

                            ),
                            modifier = modifier
                                .width(120.dp)
                                .height(45.dp), onClick = onDismissRequest
                        ) {
                            Text(
                                text = "Close",
                                fontWeight = FontWeight.Medium,
//                                fontFamily = poppinsFamily,
                                fontSize = 14.sp,
                            )
                        }
                        Spacer(modifier = modifier.height(12.dp))
                    }
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        )
    }
}