package me.ppvan.meplace.ui.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.ppvan.meplace.R
import me.ppvan.meplace.data.Comment
import me.ppvan.meplace.data.Destination
import me.ppvan.meplace.ui.view.CommentData
import me.ppvan.meplace.ui.view.CommentDataDto
import me.ppvan.meplace.ui.view.createCommemtToDes
import me.ppvan.meplace.ui.view.fetchCmtsDestination

@Composable
fun UserRatingBar(
    destination: Destination,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    ratingState: Int,
    ratingIconPainter: Painter = painterResource(id = R.drawable.ic_star),
    selectedColor: Color = Color(0xFFFFD700),
    unselectedColor: Color = Color(0xFFA2ADB1),
    rating: (Int) -> Unit,
    newComment: (CommentDataDto) -> Unit,
    totalRating: Int,
    sourceComments: List<CommentData>
) {

    Text(
        text = "Đánh giá",
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        modifier = modifier.padding(bottom = 6.dp)
    )

    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$totalRating.0",
            fontWeight = FontWeight.Medium,
            fontSize = 50.sp,
            modifier = modifier.padding()
        )
        repeat(5) { index ->
            val icon = if (index < ratingState) {
                Icons.Filled.Star
            } else {
                Icons.Outlined.StarBorder
            }
            StarIcon(
                size = size,
                icon= icon,
                ratingValue = index + 1,
                onClick = rating
            )
        }
    }

    AddCommentField(idDes= destination.id, modifier = modifier, onClick = newComment, sourceComments = sourceComments)

    Text(
        text = "Bình luận",
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        modifier = modifier.padding(bottom = 6.dp)
    )

    sourceComments.forEach { comment ->
        CommentItem(modifier = modifier, comment = comment)
    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StarIcon(
    size: Dp,
    icon: ImageVector,
    ratingValue: Int,
    onClick: (Int) -> Unit
) {

    Icon(
        imageVector = icon,
        contentDescription = "Star Rating",
        tint = Color(0xffffd700),
        modifier = Modifier
            .size(size)
            .clickable { onClick(ratingValue) },
    )
}

@Composable
fun CommentItem(modifier: Modifier = Modifier, comment: CommentData) {
    // Display the list of comment
    Text(
        text = comment.username,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = modifier
    )
    Text(
        text = comment.body,
        style = MaterialTheme.typography.bodySmall,
        fontSize = 15.sp,
        modifier = modifier
    )
    HorizontalDivider(
        thickness = 1.dp,
        color = Color.LightGray,
        modifier = modifier
    )
}

@Composable
fun AddCommentField(idDes: Int, modifier: Modifier = Modifier, onClick: (CommentDataDto) -> Unit, sourceComments: List<CommentData>) {
    val text = remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val corouScope = rememberCoroutineScope()
    var res by remember { mutableStateOf(0) }

    OutlinedTextField(
        enabled = true,
        maxLines = 1,
        modifier = Modifier.fillMaxWidth()
            .padding(start = 9.dp, end = 9.dp, bottom = 9.dp, top = 20.dp),
        value = text.value,
        onValueChange = { text.value = it },
        placeholder = { Text("Thêm bình luận") },
        shape = RoundedCornerShape(50),
        trailingIcon = {
            Icon(
                Icons.AutoMirrored.Filled.Send,
                modifier = Modifier.clickable {
                    val currentText = text.value.text
                    onClick(CommentDataDto(0, "thanhtd", text.value.text))
                    corouScope.launch(Dispatchers.IO) {
                        res = createCommemtToDes(CommentDataDto(idDes = idDes, username = "thanhtd", body = currentText ))
                    }
                    keyboardController?.hide()
                    text.value = TextFieldValue("")
                                              },
                contentDescription = null)}
    )
}