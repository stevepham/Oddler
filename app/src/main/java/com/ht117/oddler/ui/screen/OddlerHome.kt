package com.ht117.oddler.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ht117.data.model.UiState
import com.ht117.oddler.ui.component.OddlerTopBar
import com.ht117.oddler.ui.theme.OddlerTheme
import com.ht117.oddler.ui.theme.Purple40
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OddlerHome(viewModel: HomeViewModel = koinViewModel()) {
    val controller = rememberNavController()
    val state = controller.currentBackStackEntryAsState()
    val route = state.value?.destination?.route
    val destiny = getCurrentDestiny(route)

    val accountID by viewModel.uiState.collectAsStateWithLifecycle(initialValue = UiState.Loading)
    OddlerTheme {
        Scaffold(
            bottomBar = {
                if (destiny == OddlerDestiny.HomeDestiny) {
                    BottomAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                        containerColor = Purple40
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (accountID as? UiState.Success)?.data ?: "",
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .align(Alignment.CenterStart)
                            )

                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .align(Alignment.CenterEnd)
                                    .clickable {
                                        controller.navigate(OddlerDestiny.AddDestiny.route)
                                    }
                            )
                        }
                    }
                }
            },
            topBar = {
                if (destiny != null) {
                    OddlerTopBar(contentId = destiny.titleId)
                }
            },
            modifier = Modifier
                .fillMaxSize()
        ) {
            OddlerGraph(controller, Modifier.padding(it))
        }
    }
}
