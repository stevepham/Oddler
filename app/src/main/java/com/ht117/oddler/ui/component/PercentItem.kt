package com.ht117.oddler.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PercentItem(modifier: Modifier = Modifier, percent: Float, isEnabled: Boolean) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isEnabled) Color.White else Color.Gray)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = percent.toString(),
            modifier = modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp)
        )

        Box(
            modifier = modifier
                .width(48.dp)
                .fillMaxHeight()
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                .align(Alignment.CenterEnd),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "%")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPercentItem() {
    Column {
        PercentItem(percent = 8.5F, isEnabled = true)
        PercentItem(percent = 8.5F, isEnabled = false)
    }
}
