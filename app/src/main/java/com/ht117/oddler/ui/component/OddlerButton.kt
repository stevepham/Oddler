package com.ht117.oddler.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun OddlerButton(modifier: Modifier = Modifier, onClick: () -> Unit, isEnable: () -> Boolean, txtId: Int) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = isEnable.invoke()
    ) {
        Text(text = stringResource(id = txtId))
    }
}
