package com.paymong.ui.watch.battle

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.navigation.WatchNavItem
import com.paymong.ui.theme.PaymongTheme
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BattleSelect(
    navController: NavHostController
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
//        Column(
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxHeight()
//        ) {
//            Button(
//                onClick = {
//                    selectState.value = "LEFT"
//                    navController.navigate(WatchNavItem.BattleActive.route){
//                        popUpTo(navController.graph.findStartDestination().id)
//                        launchSingleTop =true
//                    }
//                }
//            ) {
//                Text(text = "왼쪽")
//            }
//        }
//        Column(
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxHeight()
//        ) {
//            Button(
//                onClick = {
//                    selectState.value = "RIGHT"
//                    navController.navigate(WatchNavItem.BattleActive.route){
//                        popUpTo(navController.graph.findStartDestination().id)
//                        launchSingleTop =true
//                    }
//                }
//            ) {
//                Text(text = "오른쪽")
//            }
//        }
    }

}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleSelectPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        BattleSelect(navController)
    }
}