package com.aengussong.mirror.connecting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aengussong.mirror.R
import com.aengussong.mirror.ui.MirrorButton
import com.aengussong.mirror.ui.navigation.Navigation
import com.airbnb.lottie.compose.*

@Composable
fun ConnectingScreen(
    vm: ConnectingViewModel = viewModel(),
    ip: String? = null,
    port: String? = null,
    onNavigate: (Navigation) -> Unit
) {
    val initialAddress: String by remember { mutableStateOf((ip ?: "") + (port ?: "")) }
    val displayedAddress by vm.connectingAddressFlow.collectAsState(initial = initialAddress)

    val navigation by vm.navigationFlow.collectAsState(initial = null)

    val error: Error? by vm.errorFlow.collectAsState(initial = null)

    val localError = error
    if (localError != null) {
        when (localError) {
            is CantConnectToAddress -> ConnectingDialog(
                text = stringResource(id = R.string.dialog_cant_connect_to_mirror),
                onClick = { vm.onErrorAcknowledged(localError) }
            )
            is MirrorNotDiscovered -> ConnectingDialog(
                text = stringResource(id = R.string.dialog_mirror_not_discovered_title),
                onClick = { vm.onErrorAcknowledged(localError) }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.scanning))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
        )
        LottieAnimation(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f),
            composition = composition,
            progress = progress,
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .padding(40.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 18.sp,
                text = stringResource(id = R.string.connecting),
                color = Color.White
            )

            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 18.sp,
                text = displayedAddress,
                color = Color.White
            )
        }

        LaunchedEffect(initialAddress) {
            vm.onAddressSet(ip, port)
        }

        LaunchedEffect(navigation) {
            navigation?.let(onNavigate)
        }
    }
}

@Composable
private fun ConnectingDialog(text: String, onClick: () -> Unit) {
    AlertDialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        text = {
            Text(
                fontSize = 18.sp,
                text = text,
                color = Color.White
            )
        },
        confirmButton = {
            MirrorButton(label = stringResource(id = R.string.ok), onClick = onClick)
        },
        onDismissRequest = {}
    )
}
