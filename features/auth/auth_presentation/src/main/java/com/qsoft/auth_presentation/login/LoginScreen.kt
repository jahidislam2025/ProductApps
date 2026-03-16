package com.qsoft.auth_presentation.login

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import com.qsoft.designsystem.rippleClickable
import com.qsoft.designsystem.ssp
import com.qsoft.designsystem.theme.bodyRegularSpanStyle
import com.qsoft.designsystem.theme.bodyRegularTextStyle
import com.qsoft.designsystem.theme.bodyXSRegularTextStyle
import com.qsoft.designsystem.theme.grayScale
import com.qsoft.designsystem.theme.primaryBlue
import com.qsoft.designsystem.theme.subHeading1TextStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.qsoft.common.R as CommonR
import com.qsoft.designsystem.R as DesignSystemR

@SuppressLint("ContextCastToActivity")
@Composable
fun LoginScreen(
    snackBarHostState: SnackbarHostState,
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit,
    onHome: () -> Unit
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
            append(stringResource(id = CommonR.string.you_have_not_any_account) + " ")
        }

        pushStringAnnotation(
            tag = CommonR.string.sign_up.toString(),
            annotation = CommonR.string.sign_up.toString()
        )

        withStyle(
            style = bodyRegularSpanStyle.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 15.ssp()
            )
        ) {
            append(stringResource(id = CommonR.string.sign_up))
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

        Text(text = stringResource(CommonR.string.sign_in), style = subHeading1TextStyle)

        Spacer(modifier = Modifier.height(10.r()))

        Text(
            stringResource(CommonR.string.welcome_back_you_have_been_missed),
            style = bodyRegularTextStyle.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Spacer(modifier = Modifier.height(30.r()))

        ContentBox(
            state = state,
            onEvent = onEvent,
            onForgotPassword = {
                onForgotPassword()
            },
            onSignUp = {
                onSignUp()
            },
            annotateSignUpString = annotateSignUpString,
            onSignIn = {
                onEvent(LoginEvent.OnSignIn)
            }
        )
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun ContentBox(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit,
    annotateSignUpString: AnnotatedString,
    onSignIn: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.r()),//appBrush(),
                shape = RoundedCornerShape(12.r())
            ),

        ) {
        Column(
            modifier = Modifier.padding(20.r()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            CommonTextField(
                value = state.email,
                onValueChange = { onEvent(LoginEvent.OnEmailEnter(it)) },
                isTouched = state.isEmailTouched,
                isValid = state.isMailValid,
                onTouched = { onEvent(LoginEvent.OnEmailTouchedListener) },
                placeholder = stringResource(id = CommonR.string.your_email),
                leadingIcon = painterResource(id = DesignSystemR.drawable.ic_mail),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )



            Spacer(modifier = Modifier.height(14.r()))

            PasswordTextField(
                value = state.password,
                onValueChange = { onEvent(LoginEvent.OnPasswordEnter(it)) },
                isTouched = state.isPasswordTouched,
                isValid = state.isPasswordValid,
                onTouched = { onEvent(LoginEvent.OnPasswordTouchedListener) },
                keyboardController = keyboardController
            )

            Spacer(modifier = Modifier.height(14.r()))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Checkbox(
                    modifier = Modifier.size(36.r()),
                    checked = state.isRememberMeChecked,
                    onCheckedChange = {
                        onEvent(LoginEvent.OnRememberMeChecked(it))
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = primaryBlue,
                        uncheckedColor = grayScale,
                        checkmarkColor = Color.White
                    )
                )

                Text(
                    text = stringResource(CommonR.string.remember_me),
                    style = bodyXSRegularTextStyle
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(CommonR.string.forgot_password),
                    style = bodyXSRegularTextStyle,
                    modifier = Modifier.rippleClickable {
                        onForgotPassword()
                    }
                )
            }



            Spacer(modifier = Modifier.height(20.r()))

            AppActionButton(
                text = CommonR.string.sign_in,
                onClick = {
                    onSignIn()
                },
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
                                tag = CommonR.string.sign_up.toString(),
                                start = 0,
                                end = annotateSignUpString.length
                            )
                            .firstOrNull()
                            ?.let {
                                onSignUp()
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
fun PreviewLoginScreen() {
    LoginScreen(
        state = LoginState(
            isRememberMeChecked = false
        ),
        onEvent = {},
        onForgotPassword = {},
        onSignUp = {},
        uiEvent = flow { },
        onHome = {},
        snackBarHostState = remember {
            SnackbarHostState()
        }
    )
}