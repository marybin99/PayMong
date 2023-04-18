package com.app.watch.ui.interaction.battle

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.app.watch.navigation.NavItem
import com.app.watch.theme.PaymongTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BattleEnd(
    animationState: MutableState<String>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "배틀 결과 화면", textAlign = TextAlign.Center)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Button(
                onClick = {
                    animationState.value = "battle"
//                    coroutineScope.launch { pagerState.scrollToPage(0) }
                    navController.navigate(NavItem.Main.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop =true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "확인")
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleEndPreview() {
    val animationState = remember { mutableStateOf("") }
    val navController = rememberSwipeDismissableNavController()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    PaymongTheme {
        BattleEnd(animationState, pagerState, coroutineScope, navController)
    }
}