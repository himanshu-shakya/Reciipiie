package com.reciipiie.app.common.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.reciipiie.app.R
import com.reciipiie.app.ui.theme.Gray_1
import com.reciipiie.app.ui.theme.Gray_10
import com.reciipiie.app.ui.theme.Gray_6
import com.reciipiie.app.ui.theme.Orange

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(100),

    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next
    ),
    string: String,
    hintInternalText: String = "xyz@xyz.in",
    @DrawableRes icon: Int = R.drawable.ic_search,
    iconSize: Dp = 29.dp,
    onValueChange: (String) -> Unit,
    onBackClick:()->Unit = {}
    ) {

        BasicTextField(
            value = string,
            onValueChange = { onValueChange(it) },
            cursorBrush = SolidColor(
                Orange
            ),
            modifier = Modifier.clip(shape),
            textStyle = TextStyle(
                color = Gray_10,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            ),
            keyboardOptions = keyboardOptions,
            decorationBox = { innerBox ->
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(shape)
                        .background(Gray_1, shape),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = hintInternalText,
                            modifier = Modifier
                                .size(iconSize)
                                .padding(3.dp),
                            tint = Gray_10,

                            )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize(), contentAlignment = Alignment.CenterStart
                    ) {
                        if (string.isEmpty()) {
                            Text(
                                text = hintInternalText,
                                style = TextStyle(
                                    color = Gray_6,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Start
                                )
                            )
                        }

                        innerBox()

                    }
                }
            },
            singleLine = true,
        )
}
@Composable
fun ReciipiieLoading(modifier: Modifier = Modifier) {
    val isPlaying by remember {
        mutableStateOf(true)
    }
    // for speed
    val speed by remember {
        mutableStateOf(1f)
    }

    // remember lottie composition ,which
    // accepts the lottie composition result
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.loading)
    )

    val progress by animateLottieCompositionAsState(
        // pass the composition created above
        composition,

        // Iterates Forever
        iterations = LottieConstants.IterateForever,

        // pass isPlaying we created above,
        // changing isPlaying will recompose
        // Lottie and pause/play
        isPlaying = isPlaying,

        // pass speed we created above,
        // changing speed will increase Lottie
        speed = speed,

        // this makes animation to restart when paused and play
        // pass false to continue the animation at which it was paused
        restartOnPlay = false

    )
    Box(modifier =Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        LottieAnimation(
            composition,
            progress,
            modifier = modifier.size(50.dp),
            contentScale = ContentScale.FillBounds
        )
    }

}
