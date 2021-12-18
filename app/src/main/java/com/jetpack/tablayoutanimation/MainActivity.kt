package com.jetpack.tablayoutanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.jetpack.tablayoutanimation.ui.theme.*
import kotlinx.coroutines.launch

@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabLayoutAnimationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Tab Layout Animation",
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            },
                            backgroundColor = YellowColor
                        )

                        TabLayoutAnimation()
                    }
                }
            }
        }
    }
}

enum class TabPage {
    Accept, InProgress, Complete
}

val list = listOf("Accept", "InProgress", "Complete")

@ExperimentalPagerApi
@Composable
fun TabLayoutAnimation() {
    val pagerState = rememberPagerState(pageCount = 3)
    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState)
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    val backgroundColor by animateColorAsState(
        when(pagerState.currentPage) {
            0 -> AcceptColor
            1 -> InProgressColor
            else -> CompleteColor
        }
    )

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = backgroundColor,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabLayoutIndicator(
                tabPositions = tabPositions,
                tabPage =
                    when(pagerState.currentPage) {
                        0 -> TabPage.Accept
                        1 -> TabPage.InProgress
                        else -> TabPage.Complete
                    }
            )
        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = list[index],
                        color = if (pagerState.currentPage == index) Color.White else Color.White.copy(alpha = 0.5f),
                        fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState) {
    HorizontalPager(state = pagerState) { page ->
        when(page) {
            0 -> TabScreen(
                data = "Accept TabLayout",
                image = R.drawable.cat,
                color = AcceptColor
            )
            1 -> TabScreen(
                data = "InProgress TabLayout",
                image = R.drawable.cat,
                color = InProgressColor
            )
            2 -> TabScreen(
                data = "Complete TabLayout",
                image = R.drawable.cat,
                color = CompleteColor
            )
        }
    }
}






















