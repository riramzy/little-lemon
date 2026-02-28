package com.riramzy.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.riramzy.littlelemon.R

val Karla = FontFamily(
    Font(
        resId = R.font.karla,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    )
)

val MarkaziText = FontFamily(
    Font(
        resId = R.font.markazi,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    //Display Title
    displayLarge = TextStyle(
        fontFamily = MarkaziText,
        fontWeight = FontWeight.Normal,
        fontSize = 64.sp,
    ),
    //Sub Title
    headlineLarge = TextStyle(
        fontFamily = MarkaziText,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
    ),
    //Section Title
    titleLarge = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,

    ),
    //Section Category
    titleSmall = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,

    ),
    //Card Title
    labelLarge = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    //Card Text
    labelSmall = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    //LeadText
    bodyMedium = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,

    )
)