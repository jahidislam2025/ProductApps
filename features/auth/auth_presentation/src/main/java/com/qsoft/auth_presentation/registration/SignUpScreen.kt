package com.qsoft.auth_presentation.registration

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.qsoft.common.util.UiEvent
import com.qsoft.designsystem.components.AppActionButton
import com.qsoft.designsystem.components.CommonTextField
import com.qsoft.designsystem.components.PasswordTextField
import com.qsoft.designsystem.r
import com.qsoft.designsystem.ssp
import com.qsoft.designsystem.theme.BACKGROUND_COLOR
import com.qsoft.designsystem.theme.ColorPrimaryDark
import com.qsoft.designsystem.theme.appBrush
import com.qsoft.designsystem.theme.bodyRegularSpanStyle
import com.qsoft.designsystem.theme.bodyRegularTextStyle
import com.qsoft.designsystem.theme.grayScale
import com.qsoft.designsystem.theme.primaryBlue
import com.qsoft.designsystem.theme.subHeading1TextStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.qsoft.common.R as CommonR
import com.qsoft.designsystem.R as DesignSystemR


@SuppressLint("ContextCastToActivity")
@Composable
fun SignUpScreen(
    state: SignUpState,
    onEvent: (SignUpEvent) -> Unit,
    onSignIn: () -> Unit,
    uiEvent: Flow<UiEvent>,
    onHome: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {

    val context = LocalContext.current


    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> {
                    onHome()
                }

                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message.asString(context = context),
                        duration = SnackbarDuration.Short,
                    )
                }

                is UiEvent.NavigateUp -> {

                }
            }

        }
    }

    val annotateSignUpString = buildAnnotatedString {
        withStyle(style = bodyRegularSpanStyle.copy(fontSize = 15.ssp())) {
            append(stringResource(id = CommonR.string.already_have_an_account) + " ")
        }

        pushStringAnnotation(
            tag = CommonR.string.sign_in.toString(),
            annotation = CommonR.string.sign_in.toString()
        )

        withStyle(
            style = bodyRegularSpanStyle.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 15.ssp()
            )
        ) {
            append(stringResource(id = CommonR.string.sign_in))
        }

        append(".")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.r())
            .padding(top = 24.r()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(120.r()))

        Text(text = stringResource(CommonR.string.getting_started), style = subHeading1TextStyle)

        Spacer(modifier = Modifier.height(10.r()))

        Text(
            stringResource(CommonR.string.create_an_account_to_continue_and_connect_with_the_people),
            style = bodyRegularTextStyle.copy(color = grayScale),
            modifier = Modifier.padding(horizontal = 23.r())
        )

        Spacer(modifier = Modifier.height(30.r()))

        ContentBox(
            state = state,
            onEvent = onEvent,
            onSignIn = {
                onSignIn()
            },
            annotateSignUpString = annotateSignUpString
        )
    }
}

@Composable
private fun ContentBox(
    state: SignUpState,
    onEvent: (SignUpEvent) -> Unit,
    onSignIn: () -> Unit,
    annotateSignUpString: AnnotatedString
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.r()),
                shape = RoundedCornerShape(12.r())
            ),

        ) {
        Column(
            modifier = Modifier.padding(20.r()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            CommonTextField(
                value = state.email,
                onValueChange = { onEvent(SignUpEvent.OnEmailEnter(it)) },
                isTouched = state.isEmailTouched,
                isValid = state.isMailValid,
                onTouched = { onEvent(SignUpEvent.OnEmailTouchedListener) },
                placeholder = stringResource(id = CommonR.string.your_email),
                leadingIcon = painterResource(id = DesignSystemR.drawable.ic_mail),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(14.r()))

            CommonTextField(
                value = state.name,
                onValueChange = {
                    onEvent(SignUpEvent.OnNameInput(it))
                },
                isTouched = state.isNameTouched,
                isValid = true,
                onTouched = { onEvent(SignUpEvent.OnNameInputTouchedListener) },
                placeholder = stringResource(id = CommonR.string.your_name),
                leadingIcon = painterResource(id = DesignSystemR.drawable.ic_account),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(14.r()))

            PasswordTextField(
                value = state.password,
                onValueChange = { onEvent(SignUpEvent.OnPasswordEnter(it)) },
                isTouched = state.isPasswordTouched,
                isValid = state.isPasswordValid,
                onTouched = { onEvent(SignUpEvent.OnPasswordTouchedListener) },
                keyboardController = keyboardController
            )

            if (state.isPasswordTouched && state.password.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.r()))

                LinearProgressIndicator(
                    progress = { (state.strengthResult.score / 4f).coerceIn(0f, 1f) },
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    color = state.strengthResult.strength.color,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.r())
                )

                Spacer(modifier = Modifier.height(8.r()))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = state.strengthResult.strength.label,
                        color = state.strengthResult.strength.color,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }


            Spacer(modifier = Modifier.height(14.r()))

            PasswordTextField(
                value = state.confirmPassword,
                onValueChange = { onEvent(SignUpEvent.OnConfirmPasswordEnter(it)) },
                isTouched = state.isConfirmPasswordTouched,
                isValid = state.isConfirmPasswordValid,
                onTouched = { onEvent(SignUpEvent.OnConfirmPasswordTouchedListener) },
                keyboardController = keyboardController,
                placeholder = stringResource(CommonR.string.confirm_password)
            )

            Spacer(modifier = Modifier.height(14.r()))


            Spacer(modifier = Modifier.height(20.r()))

            AppActionButton(
                text = CommonR.string.sign_up,
                onClick = {

                },
                textStyle = bodyRegularTextStyle,
                modifier = Modifier
                    .height(40.r())
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.r()))

            Text(
                modifier = Modifier
                    .clickable {
                        annotateSignUpString
                            .getStringAnnotations(
                                tag = CommonR.string.sign_in.toString(),
                                start = 0,
                                end = annotateSignUpString.length
                            )
                            .firstOrNull()
                            ?.let {
                                onSignIn()
                            }
                    },
                text = annotateSignUpString,
                style = bodyRegularTextStyle
            )

        }
    }

}

@Composable
@Preview
fun PreviewSignUpScreen() {
    SignUpScreen(
        state = SignUpState(),
        onEvent = {},
        onSignIn = {},
        uiEvent = flow { },
        onHome = {},
        snackBarHostState = remember {
            SnackbarHostState()
        }
    )
}