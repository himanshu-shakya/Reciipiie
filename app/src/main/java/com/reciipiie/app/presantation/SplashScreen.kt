package com.reciipiie.app.presantation

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.reciipiie.app.R
import com.reciipiie.app.presantation.viewmodel.AuthenticationViewModel
import com.reciipiie.app.ui.theme.Gray_0
import com.reciipiie.app.ui.theme.InterFontFamily
import com.reciipiie.app.ui.theme.Orange
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToMain: () -> Unit,
    navigateToLogin: () -> Unit,
    authViewModel: AuthenticationViewModel,
) {
    val userName by authViewModel.userName.collectAsState()
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
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(true) {
        scale.animateTo(
            targetValue = 1.8f,
            animationSpec = tween(400, easing = EaseInBounce),

            )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(400, easing = EaseOutBounce)
        )
        delay(200)
        Log.i("TAG", "SplashScreen: $userName")
        if(userName.isEmpty()){
            navigateToLogin()
        }else{
            navigateToMain()
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Orange),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.scale(scale.value)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "logo",
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
    }
}