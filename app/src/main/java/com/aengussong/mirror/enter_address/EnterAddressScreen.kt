package com.aengussong.mirror.enter_address

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aengussong.mirror.R
import com.aengussong.mirror.ui.navigation.Navigation
import com.aengussong.mirror.ui.theme.MirrorTheme

@Composable
fun EnterAddressScreen(vm: EnterAddressViewModel = viewModel(), onNavigate: (Navigation) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.primary)
    ) {
        val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
        val error: Error? by vm.errorFlow.collectAsState(null)
        if (error != null) {
            LaunchedEffect(error) {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(
                    error?.message ?: "Unknown error",
                    duration = SnackbarDuration.Indefinite
                )
            }
        }

        val navigation: Navigation? by vm.navigationFlow.collectAsState(initial = null)
        if (navigation != null) {
            LaunchedEffect(navigation) {
                navigation?.let(onNavigate)
            }
        }

        Text(
            modifier = Modifier
                .padding(top = 120.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.title_cant_access_previous_address),
            color = Color.White
        )
        Text(
            modifier = Modifier
                .padding(40.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 18.sp,
            text = stringResource(id = R.string.message_provide_new_address),
            color = Color.White
        )

        var ip by rememberSaveable { mutableStateOf("") }
        MirrorAddressInput(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(40.dp),
            value = ip,
            label = stringResource(id = R.string.hint_mirror_ip),
            placeholder = "192.168.0.24",
            onTextChanged = { text ->
                ip = text
            })

        var port by rememberSaveable { mutableStateOf("") }
        MirrorAddressInput(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 40.dp),
            value = port,
            label = stringResource(id = R.string.hint_mirror_port),
            placeholder = "8080",
            onTextChanged = { text ->
                port = text
            })
        MirrorAddressButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 40.dp),
            label = stringResource(id = R.string.scan),
            onClick = { vm.onScan(ip, port) })
        MirrorAddressButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 80.dp, top = 20.dp),
            label = stringResource(id = R.string.automatic_scan),
            onClick = { vm.onAutomaticScan() })


        SnackbarHost(hostState = snackbarHostState)
    }
}

@Composable
private fun MirrorAddressInput(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    placeholder: String,
    onTextChanged: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, color = Color.White, shape = RoundedCornerShape(8.dp)),
        placeholder = { Text(text = placeholder, color = Color(0xFF767676)) },
        colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
        value = value,
        label = { Text(text = label, color = Color(0xFF767676)) },
        onValueChange = onTextChanged,
        singleLine = true
    )
}

@Composable
private fun MirrorAddressButton(modifier: Modifier = Modifier, label: String, onClick: () -> Unit) {
    Button(
        modifier = modifier
            .height(48.dp)
            .width(178.dp), onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
    ) {
        Text(text = label)
    }
}

@Preview
@Composable
fun EditPreview() {
    MirrorTheme {
        EnterAddressScreen {}
    }
}