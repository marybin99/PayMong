package com.paymong.ui.watch.feed

import android.media.SoundPool
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.feed.FeedViewModel
import com.paymong.common.R
import com.paymong.common.code.FoodCode
import com.paymong.domain.entity.Food
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.landing.MainBackgroundGif
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedBuyList(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    feedViewModel: FeedViewModel = viewModel()
) {
    feedViewModel.getFoodList(feedViewModel.foodCategory)
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    val img = painterResource(R.drawable.main_bg)
    Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
    MainBackgroundGif()

    if (screenWidthDp < 200) {
        SmallWatch(animationState, pagerState, coroutineScope, navController, feedViewModel)
    }
    else {
        BigWatch(animationState, pagerState, coroutineScope, navController, feedViewModel)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SmallWatch(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    feedViewModel: FeedViewModel
) {
    val payPointText = if (feedViewModel.payPoint.toString().length > 5) {
        feedViewModel.payPoint.toString().substring(0, 5) + "+"
    } else {
        feedViewModel.payPoint.toString()
    }

    val soundPool = SoundPool.Builder()
        .setMaxStreams(1) // 동시에 재생 가능한 스트림의 최대 수
        .build()
    val context = LocalContext.current
    val buttonSound = soundPool.load(context, com.paymong.ui.R.raw.button_sound, 1)

    fun ButtonSoundPlay () {
        soundPool.play(buttonSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        // * User Point *
        Box(modifier = Modifier
            .padding(bottom = 15.dp)
            .height(20.dp)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.pointbackground),
                contentDescription = "pointbackground",
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pointlogo),
                    contentDescription = "pointlogo",
                    modifier = Modifier
                        .size(10.dp)
                        .padding(bottom = 2.dp)
                )
                Text(
                    text = payPointText,
                    textAlign = TextAlign.Center,
                    fontFamily = dalmoori,
                    fontSize = 10.sp,
                    color = Color(0xFF0C4DA2),
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
        }

        Box(modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            //왼쪽 버튼
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        ButtonSoundPlay();
                        feedViewModel.prevButtonClick()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    modifier = Modifier.fillMaxHeight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.leftbnt),
                        contentDescription = "leftbnt",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

            // 음식
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxHeight(1f)) {
                    Row(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = feedViewModel.name,
                            textAlign = TextAlign.Center,
                            fontFamily = dalmoori,
                            fontSize = 16.sp
                        )
                    }

                    var foodImg = R.drawable.none
                    if (feedViewModel.foodCode != "") {
                        foodImg = FoodCode.valueOf(feedViewModel.foodCode).code
                    }

                    Row(
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Image(
                            painter = painterResource(foodImg),
                            contentDescription = "foodImg",
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }
            }

            // 오른쪽 버튼
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { ButtonSoundPlay(); feedViewModel.nextButtonClick() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    modifier = Modifier.fillMaxHeight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rightbnt),
                        contentDescription = "rightbnt",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }

        // 구매 버튼
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(50.dp)
                .padding(top = 15.dp)
        ) {
            Button(
                onClick = {
                    feedViewModel.selectButtonClick()
                    animationState.value = AnimationCode.Feed
                    coroutineScope.launch { pagerState.scrollToPage(1) }
                    navController.navigate(WatchNavItem.Main.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.blue_bnt),
                        contentDescription = "blue_bnt",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pointlogo),
                            contentDescription = "pointlogo",
                            modifier = Modifier
                                .size(13.dp)
                                .padding(bottom = 2.dp)
                        )
                        Text(
                            text = String.format(" %d", feedViewModel.price),
                            textAlign = TextAlign.Center,
                            fontFamily = dalmoori,
                            color = Color(0xFF0C4DA2),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun BigWatch(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    feedViewModel: FeedViewModel
) {
    val payPointText = if (feedViewModel.payPoint.toString().length > 5) {
        feedViewModel.payPoint.toString().substring(0, 5) + "+"
    } else {
        feedViewModel.payPoint.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)

    ) {
        // * User Point *
        Box(
            modifier = Modifier
                .padding(bottom = 15.dp)
                .height(30.dp)
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.pointbackground),
                contentDescription = "pointbackground",
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pointlogo),
                    contentDescription = "pointlogo",
                    modifier = Modifier
                        .size(15.dp)
                        .padding(bottom = 2.dp)
                )
                Text(
                    text = payPointText,
                    textAlign = TextAlign.Center,
                    fontFamily = dalmoori,
                    fontSize = 15.sp,
                    color = Color(0xFF0C4DA2),
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
        }

        Box(
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            // 왼쪽 버튼
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { feedViewModel.prevButtonClick() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    modifier = Modifier.fillMaxHeight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.leftbnt),
                        contentDescription = "leftbnt",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            // 음식
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxHeight(1f)) {
                    Row(
                        modifier = Modifier
                            .height(21.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = feedViewModel.name,
                            textAlign = TextAlign.Center,
                            fontFamily = dalmoori,
                            fontSize = 21.sp
                        )
                    }

                    var foodImg = R.drawable.none
                    if (feedViewModel.foodCode != "") {
                        foodImg = FoodCode.valueOf(feedViewModel.foodCode).code
                    }

                    Row(
                        modifier = Modifier
                            .height(85.dp)
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Image(
                            painter = painterResource(foodImg),
                            contentDescription = "foodImg",
                            modifier = Modifier.size(85.dp)

                        )
                    }
                }
            }

            // 오른쪽 버튼
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { feedViewModel.nextButtonClick() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    modifier = Modifier.fillMaxHeight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rightbnt),
                        contentDescription = "rightbnt",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }


        //  구매 버튼
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(60.dp)
                .padding(top = 15.dp)
        ) {
            Button(
                onClick = {
                    feedViewModel.selectButtonClick()
                    animationState.value = AnimationCode.Feed
                    coroutineScope.launch { pagerState.scrollToPage(1) }
                    navController.navigate(WatchNavItem.Main.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.blue_bnt),
                        contentDescription = "blue_bnt",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pointlogo),
                            contentDescription = "pointlogo",
                            modifier = Modifier
                                .size(18.dp)
                                .padding(bottom = 2.dp)
                        )
                        Text(
                            text = String.format(" %d", feedViewModel.price),
                            textAlign = TextAlign.Center,
                            fontFamily = dalmoori,
                            color = Color(0xFF0C4DA2),
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun FeedBuyListPreview() {
    val animationState = remember { mutableStateOf(AnimationCode.Normal) }
    val navController = rememberSwipeDismissableNavController()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val feedViewModel: FeedViewModel = viewModel()

    PaymongTheme {
        FeedBuyList(animationState, pagerState, coroutineScope, navController, feedViewModel)
    }
}