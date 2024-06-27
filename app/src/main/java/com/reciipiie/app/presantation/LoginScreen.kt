package com.reciipiie.app.presantation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reciipiie.app.MainActivity
import com.reciipiie.app.R
import com.reciipiie.app.common.utils.UiState
import com.reciipiie.app.common.utils.bounceClick
import com.reciipiie.app.presantation.viewmodel.AuthenticationViewModel
import com.reciipiie.app.ui.theme.Gray_0
import com.reciipiie.app.ui.theme.InterFontFamily
import com.reciipiie.app.ui.theme.Orange
import kotlinx.coroutines.flow.collectLatest
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun LoginScreen(
    authViewModel: AuthenticationViewModel,
    mainActivity: MainActivity,
    navigateToMain:()->Unit
) {
    val appNameText = buildAnnotatedString {
        append("Rec")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Normal
            )
        ) {
            append("ii")
        }
        append("p")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Normal
            )
        ) {
            append("ii")
        }
        append("e")
    }
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(true) {
        authViewModel.continueWithGoogle.collectLatest { state ->
            when (state) {
                is UiState.Error -> {
                    snackbarHostState.showSnackbar(message = state.message)
                }

                UiState.Idle -> {}
                UiState.Loading -> {
                }

                is UiState.Success -> {
                    navigateToMain()
                }
            }


        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {

            Image(
                painter = painterResource(id = R.drawable.login_background_image),
                contentDescription = "background image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                ) {

                    Text(
                        text = "Welcome to",
                        color = Gray_0,
                        fontFamily = InterFontFamily,
                        fontSize = 50.sp,
                        fontStyle = FontStyle.Italic,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                    Text(
                        text = appNameText,
                        color = Gray_0,
                        fontFamily = InterFontFamily,
                        fontSize = 50.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                }
                Text(
                    text = "Please signing to continue",
                    fontFamily = InterFontFamily,
                    fontSize = 14.sp,
                    color = Gray_0,

                    )
                Spacer(modifier = Modifier.height(17.dp))
                Button(
                    onClick = {
                        authViewModel.continueWithGoogle(mainActivity)
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .height(50.dp)
                        .bounceClick(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange

                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "google icon",
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Continue with google",
                        fontFamily = InterFontFamily,
                        fontSize = 16.sp,
                        color = Gray_0,
                        fontWeight = FontWeight.SemiBold,
                    )
                }


            }
        }
    }

}