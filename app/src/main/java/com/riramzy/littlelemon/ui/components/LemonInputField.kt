package com.riramzy.littlelemon.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riramzy.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun LemonInputField(
    modifier: Modifier = Modifier,
    requiredText: String,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    isPasswordField: Boolean = false,
    isReadOnly: Boolean = false,
    isMultiline: Boolean = true,
) {
    var isClicked by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onTertiary
            } else {
                MaterialTheme.colorScheme.tertiaryContainer
            },
            contentColor = if (isSystemInDarkTheme()) {
                Color.White
            } else {
                Color.Black
            }
        ),
        modifier = modifier
            .width(380.dp)
            .wrapContentHeight()
            .defaultMinSize(minHeight = 45.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .width(380.dp)
                .wrapContentHeight()
                .defaultMinSize(minHeight = 45.dp)
        ) {
            Text(
                text = requiredText,
                style = MaterialTheme.typography.labelLarge,
                color = when {
                    isReadOnly -> if (isSystemInDarkTheme())
                        Color.White.copy(alpha = 0.3f)
                    else
                        Color.Black.copy(alpha = 0.3f)
                    isClicked -> if (isSystemInDarkTheme())
                        Color.White.copy(alpha = 0.3f)
                    else
                        Color.Black.copy(alpha = 0.3f)
                    else -> if (isSystemInDarkTheme())
                        Color.White
                    else
                        Color.Black
                },
                modifier = Modifier
                    .padding(start = 15.dp)
            )

            val visualTransformation = if (isPasswordField && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            }

            val keyboardOptions = if (isPasswordField) {
                KeyboardOptions.Default
            } else {
                KeyboardOptions.Default
            }

            BasicTextField(
                value = value,
                onValueChange = {
                    if (!isReadOnly) {
                        onValueChange(it)
                        isClicked = it.isNotEmpty()
                    }
                },
                singleLine = !isMultiline,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                ),
                cursorBrush = SolidColor(
                    if (isSystemInDarkTheme()) {
                        Color.White
                    } else {
                        Color.Black
                    }
                ),
                readOnly = isReadOnly,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .then(
                                if (isMultiline) {
                                    Modifier.wrapContentHeight()
                                } else {
                                    Modifier.height(45.dp)
                                }
                            )
                            .fillMaxWidth()
                            .background(
                                if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onTertiary
                                else MaterialTheme.colorScheme.tertiaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = "Enter",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = if (isClicked) {
                                        if (isSystemInDarkTheme())
                                            Color.White
                                        else
                                            Color.Black
                                    } else {
                                        if (isSystemInDarkTheme())
                                            Color.White.copy(alpha = 0.5f)
                                        else
                                            Color.Black.copy(alpha = 0.5f)
                                    },
                                )
                            )
                            isClicked = false
                        } else {
                            isClicked = true
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier
                    .weight(3f)
            )

            if (isPasswordField) {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Hide Password",
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    )
                }
            }
        }
    }
}

@Composable
fun LemonSubInputField(
    modifier: Modifier = Modifier,
    requiredText: String,
    value: String = "",
    onValueChange: (String) -> Unit = {},
) {
    //var textValue by remember { mutableStateOf("") }
    var isClicked by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.onTertiary
            } else {
                MaterialTheme.colorScheme.tertiaryContainer
            },
            contentColor = if (isSystemInDarkTheme()) {
                Color.White
            } else {
                Color.Black
            }
        ),
        modifier = modifier
            .height(45.dp)
            .width(380.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .height(45.dp)
                .width(380.dp)
                .padding(8.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                    isClicked = it.isNotEmpty()
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                ),
                cursorBrush = SolidColor(
                    if (isSystemInDarkTheme()) Color.White else Color.Black
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .height(45.dp)
                            .fillMaxWidth()
                            .background(
                                if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onTertiary
                                else MaterialTheme.colorScheme.tertiaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 0.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = requiredText,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = if (isClicked) {
                                        if (isSystemInDarkTheme())
                                            Color.White
                                        else
                                            Color.Black
                                    } else {
                                        if (isSystemInDarkTheme())
                                            Color.White.copy(alpha = 0.5f)
                                        else
                                            Color.Black.copy(alpha = 0.5f)
                                    },
                                )
                            )
                            isClicked = false
                        } else {
                            isClicked = true
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier
                    .weight(2f)
            )
        }
    }
}

@Preview
@Composable
fun LemonInputFieldPreviewLemon() {
    LittleLemonTheme {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            LemonInputField(
                requiredText = "Ramzy",
                value = "Lemon\nGreek\nLemon",
                onValueChange = {},
                isMultiline = true
            )
            LemonInputField(
                requiredText = "Ramzy",
                value = "",
                onValueChange = {},
                isPasswordField = true
            )
            LemonSubInputField(
                requiredText = "Ramzy"
            )
        }

    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LemonInputFieldDarkPreviewLemon() {
    LittleLemonTheme {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            LemonInputField(
                requiredText = "Ramzy",
                value = "",
                onValueChange = {}
            )
            LemonInputField(
                requiredText = "Ramzy",
                value = "",
                onValueChange = {},
                isPasswordField = true
            )
            LemonSubInputField(
                requiredText = "Ramzy"
            )
        }
    }
}