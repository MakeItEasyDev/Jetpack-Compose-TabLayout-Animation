package com.jetpack.tablayoutanimation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TabLayoutIndicator(
    tabPositions: List<TabPosition>,
    tabPage: TabPage
) {
    val transition = updateTransition(
        tabPage, label = "Tab Indicator"
    )
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            if (TabPage.Accept isTransitioningTo TabPage.InProgress ||
                        TabPage.Accept isTransitioningTo TabPage.Complete ||
                        TabPage.InProgress isTransitioningTo TabPage.Complete
            ) {
                spring(stiffness = Spring.StiffnessVeryLow)
            } else {
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = "Indicator Left"
    ) { page ->
        tabPositions[page.ordinal].left
    }

    val indicatorRight by transition.animateDp(
        transitionSpec = {
            if (TabPage.Accept isTransitioningTo TabPage.InProgress ||
                TabPage.Accept isTransitioningTo TabPage.Complete ||
                TabPage.InProgress isTransitioningTo TabPage.Complete
            ) {
                spring(stiffness = Spring.StiffnessMedium)
            } else {
                spring(stiffness = Spring.StiffnessVeryLow)
            }
        },
        label = "Indicator Right"
    ) { page ->
        tabPositions[page.ordinal].right
    }
    val color by transition.animateColor(
        label = "Border Color"
    ) { page ->
        when(page) {
            TabPage.Accept -> Color.White
            else -> Color.White
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, color), RoundedCornerShape(4.dp)
            )
    )
}













