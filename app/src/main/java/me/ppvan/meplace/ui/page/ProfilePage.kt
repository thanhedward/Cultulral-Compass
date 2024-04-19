package me.ppvan.meplace.ui.page

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.ppvan.meplace.data.FakeDestinationDataSource
import me.ppvan.meplace.data.User
import me.ppvan.meplace.ui.component.rememberImeState
import me.ppvan.meplace.viewmodel.ProfileViewModel
import me.ppvan.meplace.R
import me.ppvan.meplace.Routes
import me.ppvan.moon.utils.ScaleTransition


@Composable
fun ProfilePage(
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
) {

    val user: User = profileViewModel.loggedInUser
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
            ProfileViewPage(navController, user, profileViewModel ) { profileViewModel.navigateToEditMode() }
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
    val cities = FakeDestinationDataSource.dummyCities
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
                    text = "Thông tin cá nhân",
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
                    text = "Cập nhật thông tin",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileViewPage(
    navController: NavHostController,
    user: User,
    profileViewModel: ProfileViewModel,
    onEditClick: () -> Unit,
) {
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()) {

        Text(
            text = "Cá nhân",
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 10.dp, top = 15.dp, bottom = 20.dp)
        )

        Surface {
            Column(horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(start = 5.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(5.dp))
                    AsyncImage(
                        modifier = Modifier
                            .clip(CircleShape)
                            .width(100.dp)
                            .height(100.dp)
                        ,
                        model = ImageRequest.Builder(context)
                            .data(user.avatarUrl)
                            .error(R.drawable.default_user)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.default_user),
                        contentDescription = stringResource(R.string.user_avatar),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Column {
                        Text(
                            text = user.fullName,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            text = "@${user.username}",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium, color = Color.Gray)
                        )
                    }

                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Trung tâm tài khoản",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold, color = Color.Black.copy(0.9f), fontSize = 18.sp),
                    modifier = Modifier.padding(start = 5.dp)

                )

                Spacer(modifier = Modifier.height(5.dp))

                Box(contentAlignment = Alignment.Center) {
                    Column(
                    ) {
                        ProfileListItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Person,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                            label = "Thông tin cá nhân",

                            ){
                            profileViewModel.navigateToEditMode()
                        }

                        ProfileListItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Lock,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                            label = "Đổi mật khẩu",
                        ){
                            navController.navigate(Routes.ChangePass.name)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Dịch vụ",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold, color = Color.Black.copy(0.9f), fontSize = 18.sp),
                    modifier = Modifier.padding(start = 5.dp)

                )

                Spacer(modifier = Modifier.height(5.dp))

                Box(contentAlignment = Alignment.Center) {
                    Column {
                        ProfileListItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                            label = "Danh sách địa điểm yêu thích",

                            ){

                        }

                        ProfileListItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.ListAlt,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                            label = "Danh sách địa điểm đã đến",
                        ){}
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Khác",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold, color = Color.Black.copy(0.9f), fontSize = 18.sp),
                    modifier = Modifier.padding(start = 5.dp)

                )

                Spacer(modifier = Modifier.height(5.dp))

                Box(contentAlignment = Alignment.Center) {
                    Column {
                        ProfileListItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                            label = "Điều khoản dịch vụ",

                            ){

                        }

                        ProfileListItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Logout,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                            label = "Đăng xuất",
                        ){}
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
    onClick: () -> Unit
) {
    Box(modifier = Modifier.clickable(onClick = onClick)){
        Row(modifier = Modifier
            .padding(start = 5.dp, top = 8.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(16.dp))
        ) {
            Box(modifier = Modifier.width(40.dp)){
                icon()
            }
            Box(
                Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .align(Alignment.CenterVertically),
            ) {
                Text(
                    text = label,
                    fontSize = 16.sp
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditPassPage(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    onBackPressed: () -> Unit,
    onSubmit: (User) -> Unit
) {
    var expand by remember {
        mutableStateOf(false)
    }
    val cities = FakeDestinationDataSource.dummyCities
    var currentPassword by remember {
        mutableStateOf("")
    }
    var newPassword by remember {
        mutableStateOf("")
    }
    var reNewPassWord by remember {
        mutableStateOf("")
    }
    var passwordMismatch by remember {
        mutableStateOf(false)
    }
    var incorrectPassword by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val formGroup = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

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
                    text = "Thay đổi mật khẩu",
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

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .weight(1f, fill = false)
                .padding(8.dp),
        ) {
            OutlinedTextField(
                modifier = formGroup,
                value = currentPassword,
                onValueChange = { currentPassword = it },
                label = { Text(text = "Mật khẩu cũ") },
                colors = OutlinedTextFieldDefaults.colors()
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                modifier = formGroup,
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text(text = "Mật khẩu mới") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                modifier = formGroup,
                value = reNewPassWord,
                onValueChange = {
                    reNewPassWord = it
                    // Kiểm tra nếu mật khẩu nhập lại không trùng khớp với mật khẩu mới
                    passwordMismatch = it != newPassword
                },
                label = { Text(text = "Nhập lại mật khẩu mới") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            // Hiển thị thông báo nếu mật khẩu không trùng khớp
            if (passwordMismatch) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Mật khẩu không trùng khớp",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Button(
                onClick = {
                    // Kiểm tra mật khẩu cũ có đúng không
                    if (currentPassword != profileViewModel.loggedInUser.password) {
                        incorrectPassword = true
                    } else {
                        var user = profileViewModel.loggedInUser.copy(
                            password = newPassword
                        )
                        incorrectPassword = false
                        profileViewModel.updateUserProfile(user)
                        navController.popBackStack()
                        Toast.makeText(context, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Thay đổi mật khẩu",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Hiển thị thông báo nếu mật khẩu cũ không đúng
            if (incorrectPassword) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Mật khẩu cũ không đúng",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
