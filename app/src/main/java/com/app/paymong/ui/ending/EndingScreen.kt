package com.app.paymong.ui.ending

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.app.paymong.code.CommonCode
import com.app.paymong.navigation.NavItem
import com.app.paymong.ui.component.TopBar
import com.app.paymong.ui.theme.PaymongTheme

@Composable
fun Ending(navController: NavController) {
    Scaffold(
        topBar = { TopBar(msg = "Paymong 컬렉션", navController = navController) }
    ) {
        Box(Modifier.padding(it)){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "컬렉션", textAlign = TextAlign.Center)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate(NavItem.EndingDetail.route + "/ID") },
                    modifier = Modifier.size(60.dp)
                ) {
                    Text(text = "컬렉션 목록", textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EndingPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        Ending(navController)
    }
}