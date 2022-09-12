package com.aengussong.mirror.control

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aengussong.mirror.R
import com.aengussong.mirror.ui.navigation.Navigation

@Composable
fun ControlScreen(vm: ControlViewModel = viewModel(), onNavigate: (Navigation) -> Unit) {
    val error by vm.errorFlow.collectAsState(initial = null)
    LaunchedEffect(error) {
        error?.let(onNavigate)
    }

    val data by vm.dataFlow.collectAsState()

    LazyVerticalGrid(
        modifier = Modifier.systemBarsPadding(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(data) { item ->
            CrossfadeCard(item = item) {
                vm.onControllerInteraction(it)
            }
        }
    }
}

@Composable
private fun CrossfadeCard(item: Controller, onItemClick: (Controller) -> Unit) {
    Crossfade(targetState = item.isOn) { shouldShow ->

        val imageTint =
            if (shouldShow) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
        val cardTint =
            if (!shouldShow) MaterialTheme.colors.primary else MaterialTheme.colors.secondary

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(4.dp)
                .clickable { onItemClick(item) },
            border = BorderStroke(15.dp, MaterialTheme.colors.secondary),
            backgroundColor = cardTint
        ) {
            Icon(
                modifier = Modifier.padding(20.dp),
                painter = getPainterBy(item = item),
                contentDescription = null,
                tint = imageTint,
            )
        }
    }
}

@Composable
private fun getPainterBy(item: Controller): Painter {
    return when (item.type) {
        is Spook -> painterResource(id = R.drawable.ic_ghost)
        is MessageForObserver -> painterResource(id = R.drawable.ic_walk)
        is ShowTemperature -> painterResource(id = R.drawable.ic_thermostat)
        is OpenRemoteControl -> painterResource(id = R.drawable.ic_web)
        is Light -> painterResource(id = R.drawable.ic_lightbulb)
        is Guests -> painterResource(id = R.drawable.ic_kabaddi)
    }
}