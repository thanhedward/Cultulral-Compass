package me.ppvan.metour.ui.page

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.ppvan.metour.R
import me.ppvan.metour.data.FakeTourismDataSource
import me.ppvan.metour.data.User
import me.ppvan.metour.ui.component.rememberImeState
import me.ppvan.metour.viewmodel.ProfileViewModel
import me.ppvan.moon.utils.ScaleTransition


@Composable
fun ProfilePage(profileViewModel: ProfileViewModel) {

    val user by profileViewModel.loggedInUser
    val editMode by profileViewModel.editMode

    AnimatedContent(
        targetState = editMode,
        label = "page-navigation",
        transitionSpec = {
            ScaleTransition.scaleUp.enterTransition()
                .togetherWith(ScaleTransition.scaleUp.exitTransition())
        }
    ) { editModeVisible ->
        if (editModeVisible) {
            ProfileEditPage(
                user,
                onBackPressed = { profileViewModel.navigateToViewMode() },
                onSubmit = { user ->
                    profileViewModel.updateUserProfile(user)
                })
        } else {
            ProfileViewPage(user) { profileViewModel.navigateToEditMode() }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditPage(
    user: User,
    onBackPressed: () -> Unit,
    onSubmit: (User) -> Unit
) {
    var expand by remember {
        mutableStateOf(false)
    }
    val cities = FakeTourismDataSource.dummyCities
    var city by remember {
        mutableStateOf(user.city)
    }
    var fullName by remember {
        mutableStateOf(user.fullName)
    }
    var email by remember {
        mutableStateOf(user.email)
    }
    var avatarUrl by remember {
        mutableStateOf(user.avatarUrl)
    }
    var phoneNumber by remember {
        mutableStateOf(user.phoneNumber)
    }

    val formGroup = Modifier
        .fillMaxWidth()
//        .height(64.dp)
        .padding(8.dp)
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            avatarUrl = uri.toString()
        }
    )
    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Profile Page",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }

        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .aspectRatio(1f)
                    .clickable {
                        photoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
            ) {
//                Image(
//                    modifier = Modifier
//                        .matchParentSize()
//                        .clip(CircleShape),
//                    painter = painterResource(id = R.drawable.bocchi),
//                    contentDescription = null
//                )
                AsyncImage(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(CircleShape),
                    model = ImageRequest.Builder(context)
                        .data(avatarUrl)
                        .error(R.drawable.default_user)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.default_user),
                    contentDescription = stringResource(R.string.user_avatar),
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Transparent), contentAlignment = Alignment.BottomEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xff2980b9)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Change Profile"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "@${user.username}",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .weight(1f, fill = false)
                .padding(8.dp),
//                .verticalScroll(scrollState)
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedTextField(
                modifier = formGroup,
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text(text = "Họ và tên") },
                colors = OutlinedTextFieldDefaults.colors()
            )
            OutlinedTextField(
                modifier = formGroup,
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            OutlinedTextField(
                modifier = formGroup,
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text(text = "Số điện thoại") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            ExposedDropdownMenuBox(
                modifier = formGroup,
                expanded = expand,
                onExpandedChange = { expand = !expand }) {
                OutlinedTextField(
                    value = city,
                    label = { Text(text = "Tỉnh/Thành Phố") },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expand) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expand,
                    onDismissRequest = { expand = false }
                ) {
                    cities.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                city = item
                                expand = false
//                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val newUser =
                        User(
                            userId = 0L,
                            username = user.username,
                            avatarUrl = avatarUrl,
                            fullName = fullName,
                            email = email,
                            phoneNumber = phoneNumber,
                            password = user.password,
                            city = city
                        )
                    onSubmit(newUser)
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "Cập nhật Profile",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileViewPage(
    user: User,
    onEditClick: () -> Unit
) {
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = {

            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back"
                )
            },
            actions = {
                IconButton(onClick = { onEditClick() }) {
                    Icon(imageVector = Icons.Outlined.EditNote, contentDescription = null)
                }
            }

        )

        Surface {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .aspectRatio(1f)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .matchParentSize()
                            .clip(CircleShape),
                        model = ImageRequest.Builder(context)
                            .data(user.avatarUrl)
                            .error(R.drawable.default_user)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.default_user),
                        contentDescription = stringResource(R.string.user_avatar),
                        contentScale = ContentScale.Crop,
                    )

                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "@${user.username}",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )


                Spacer(modifier = Modifier.height(48.dp))

                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ProfileListItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            label = "Họ và tên",
                            content = user.fullName
                        )

                        ProfileListItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            label = "Email",
                            content = user.email
                        )

                        ProfileListItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            label = "Số điện thoại",
                            content = user.phoneNumber
                        )

                        ProfileListItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            label = "Tỉnh/Thành Phố",
                            content = user.city
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileListItem(
    icon: @Composable () -> Unit,
    label: String,
    content: String
) {
    ElevatedCard {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(horizontal = 4.dp, vertical = 8.dp)


        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(MaterialTheme.colorScheme.surface, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            Spacer(modifier = Modifier.width(12.dp))

            Column(
                Modifier
                    .weight(1f)
//                .height(50.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Light),
                    color = MaterialTheme.colorScheme.outline
                )
                Text(text = content, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Preview
@Composable
fun ProfilePagePreview() {
    ProfileViewPage(User.default(), {})
}