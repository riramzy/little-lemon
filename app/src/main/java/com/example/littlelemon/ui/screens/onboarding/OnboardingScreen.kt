package com.example.littlelemon.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.ui.components.GreenLemonButton
import com.example.littlelemon.ui.components.ProgressIndicator
import com.example.littlelemon.ui.components.YellowLemonButton
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun OnboardingWelcomeScreen(
    onNext: () -> Unit = {},
    onSkip: () -> Unit = {}
) {
    OnboardingScreen(
        title = R.string.welcome_screen_title,
        content = R.string.welcome_screen_content,
        icon = R.drawable.welcome_icon,
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        mainColor = Color.White,
        buttonText = "Next   >",
        progression = 1,
        onNext = onNext,
        onSkip = onSkip
    )
}

@Composable
fun OnboardingBrowseScreen(
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    OnboardingScreen(
        title = R.string.welcome_screen2_title,
        content = R.string.welcome_screen2_content,
        icon = R.drawable.browse_icon,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        mainColor = MaterialTheme.colorScheme.secondaryContainer,
        buttonText = "Next   >",
        progression = 2,
        onNext = onNext,
        onSkip = onSkip
    )
}

@Composable
fun OnboardingQuickScreen(
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    OnboardingScreen(
        title = R.string.welcome_screen3_title,
        content = R.string.welcome_screen3_content,
        icon = R.drawable.quick_icon,
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        mainColor = Color.White,
        buttonText = "Next   >",
        progression = 3,
        onNext = onNext,
        onSkip = onSkip
    )
}

@Composable
fun OnboardingFindScreen(
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    OnboardingScreen(
        title = R.string.welcome_screen4_title,
        content = R.string.welcome_screen4_content,
        icon = R.drawable.location_icon,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        mainColor = MaterialTheme.colorScheme.secondaryContainer,
        buttonText = "Get Started   >",
        progression = 4,
        onNext = onNext,
        onSkip = onSkip
    )
}

@Composable
fun OnboardingScreen(
    title: Int,
    content: Int,
    icon: Int,
    backgroundColor: Color,
    mainColor: Color,
    buttonText: String,
    progression: Int,
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Text(
            text = "Skip",
            style = MaterialTheme.typography.labelSmall,
            color = mainColor.copy(alpha = 0.8f),
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.TopEnd)
                .clickable {
                    onSkip()
                }
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .wrapContentWidth()
                .wrapContentHeight()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "",
                tint = mainColor,
                modifier = Modifier
                    .padding(bottom = 21.dp)
                    .width(120.dp)
                    .height(120.dp)
            )
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 21.dp),
                textAlign = TextAlign.Center,
                color = mainColor
            )
            Text(
                text = stringResource(content),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = mainColor
            )
        }

        Column(
            modifier = Modifier
                .padding(20.dp)
                .wrapContentWidth()
                .wrapContentHeight()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ProgressIndicator(progression, mainColor)

            Spacer(modifier = Modifier.height(20.dp))

            if (backgroundColor == MaterialTheme.colorScheme.secondaryContainer) {
                YellowLemonButton(
                    buttonText,
                    onClick = onNext,
                )
            } else {
                GreenLemonButton(
                    buttonText,
                    onClick = onNext,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$progression of 4",
                style = MaterialTheme.typography.bodyMedium,
                color = mainColor.copy(alpha = 0.7f)
            )
        }
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    LittleLemonTheme {
        OnboardingWelcomeScreen()
        OnboardingBrowseScreen(
            onNext = {},
            onSkip = {}
        )
        OnboardingQuickScreen(
            onNext = {},
            onSkip = {}
        )
        OnboardingFindScreen(
            onNext = {},
            onSkip = {}
        )
    }
}