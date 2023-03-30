package com.ht117.oddler.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PercentItem(modifier: Modifier, percent: String, isEditable: Boolean, onValueChange: (String) -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isEditable) Color.White else Color.Gray)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (isEditable) {
            TextField(
                value = percent,
                onValueChange = {
                    onValueChange.invoke(it)
                },
                modifier = modifier
                    .align(Alignment.CenterStart)
                    .fillMaxSize()
                    .padding(end = 64.dp)
                    .background(Color.Gray),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                )
            )
        } else {
            Text(
                text = percent,
                modifier = modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp),
                color = Color.White
            )
        }

        Box(
            modifier = modifier
                .width(64.dp)
                .fillMaxHeight()
                .border(1.dp, Color.Black, RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                .background(Color.Gray)
                .align(Alignment.CenterEnd),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "%", color = Color.White)
        }
    }
}
