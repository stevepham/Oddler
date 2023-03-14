package com.ht117.oddler.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextItem(modifier: Modifier, text: String, isEnable: Boolean) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .background(if (isEnable) Color.White else Color.Gray),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            modifier = modifier
                .padding(start = 8.dp),
            text = text,
            color = if (isEnable) Color.Black else Color.White,
            textAlign = TextAlign.Start
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItem() {
    Column {
        TextItem(Modifier, text = "T-Shirt", isEnable = false)
        TextItem(Modifier, text = "$30.00", isEnable = true)
    }
}
