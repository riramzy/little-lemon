package com.example.littlelemon.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun LemonCutlerySelector(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Cutlery",
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Help reduce plastic waste. only \n" +
                        "Only ask for cutlery if you need it.",
                style = MaterialTheme.typography.labelSmall,

                )
            RadioButton(
                selected = false,
                onClick = { /*TODO*/ }
            )
        }
    }
}

@Preview
@Composable
fun LemonCutlerySelectorPreview() {
    LittleLemonTheme {
        LemonCutlerySelector()
    }
}

