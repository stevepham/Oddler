package com.ht117.oddler.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.ht117.data.model.AppErr
import com.ht117.oddler.R

@Composable
fun ErrorText(modifier: Modifier, err: AppErr) {
    val msg = when (err) {
        is AppErr.NoNetworkErr -> {
            stringResource(id = R.string.network_lost_err)
        }
        is AppErr.NetErr -> err.message
        is AppErr.UnknownErr -> {
            stringResource(id = R.string.unknown_err)
        }
    }

    Text(modifier = modifier, text = msg, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
}
