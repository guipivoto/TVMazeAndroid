package com.jobsity.challenge.pincode

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.jobsity.challenge.biometrics.BiometricsApi
import com.jobsity.challenge.resources.R
import com.jobsity.challenge.theme.minPadding

@Composable
internal fun PinCodeScreenView(viewModel: PinCodeScreenViewModel) {
    val screenState = viewModel.pinState.collectAsState().value

    Header()

    PasswordField(screenState.isProtected) { viewModel.onPinInserted(it) }

    if (!screenState.isProvisioned) {
        Footer { viewModel.onSkipClicked() }
    }

    when (viewModel.events.collectAsState(PinCodeScreenViewModel.Event.Idle).value) {
        is PinCodeScreenViewModel.Event.Idle -> Unit
        is PinCodeScreenViewModel.Event.PromptBiometric -> UseBiometricsDialog {
            viewModel.onEnableBiometrics(it)
        }
        is PinCodeScreenViewModel.Event.BiometricAuthentication -> BiometricsAuthentication {
            viewModel.biometricAuthenticationResult(it)
        }
    }
}

@Composable
private fun PasswordField(isPinSet: Boolean, pinInsertedCallback: (String) -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(minPadding)
                .fillMaxWidth(),
            text = stringResource(if (isPinSet) R.string.enter_your_pin_code else R.string.create_pin_code),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h5
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(minPadding)
        )

        var password by remember { mutableStateOf("") }
        var isVisible by remember { mutableStateOf(false) }
        TextField(
            value = password,
            onValueChange = {
                if (it.length < 4) {
                    password = it
                } else if (it.length == 4) {
                    password = it
                    pinInsertedCallback(password)
                }
            },
            label = { Text(stringResource(id = R.string.pincode_label)) },
            placeholder = { Text(stringResource(id = R.string.pincode_placeholder)) },
            singleLine = true,
            visualTransformation = if (isVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            trailingIcon = {
                val image : ImageVector
                val description : String
                if (isVisible) {
                    image = Icons.Filled.Visibility
                    description = stringResource(R.string.pincode_hide_password)
                } else {
                    image = Icons.Filled.VisibilityOff
                    description = stringResource(R.string.pincode_show_password)
                }
                IconButton(onClick = { isVisible = !isVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )
    }
}

@Composable
fun UseBiometricsDialog(onDialogButtonSelected: (boolean: Boolean) -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(id = R.string.biometrics_are_supported)) },
        text = { Text(text = stringResource(id = R.string.use_biometrics)) },
        confirmButton = {
            Button(onClick = { onDialogButtonSelected(true) }) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        },
        dismissButton = {
            Button(onClick = { onDialogButtonSelected(false) }) {
                Text(text = stringResource(id = R.string.no))
            }
        }
    )
}

@Composable
private fun BiometricsAuthentication(onBiometricsAuthentication: (PinCodeScreenViewModel.BiometricsResult) -> Unit) {
    BiometricsApi.showPrompt(LocalContext.current as FragmentActivity,
        stringResource(id = R.string.biometrics_is_enabled),
        stringResource(id = R.string.confirm_your_identity),
        stringResource(id = R.string.biometrics_use_pin),
        object : BiometricsApi.BiometricPrompEvents {
            override fun onSuccess() {
                onBiometricsAuthentication(PinCodeScreenViewModel.BiometricsResult.SUCCESS)
            }

            override fun onFailure() {
                onBiometricsAuthentication(PinCodeScreenViewModel.BiometricsResult.FAILURE)
            }

            override fun onNegative() {
                onBiometricsAuthentication(PinCodeScreenViewModel.BiometricsResult.NEGATIVE)
            }
        })
}


@Composable
private fun Header() {
    Text(
        modifier = Modifier
            .padding(vertical = 30.dp, horizontal = minPadding)
            .fillMaxWidth(),
        text = stringResource(R.string.welcome),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.h2
    )
}

@Composable
private fun Footer(onSkipListener: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Button(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = onSkipListener
        ) {
            Text(text = stringResource(id = R.string.skip))
        }
    }
}


