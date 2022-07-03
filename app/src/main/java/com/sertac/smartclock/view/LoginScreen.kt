package com.sertac.smartclock.view

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sertac.smartclock.Dto.UserLogin
import com.sertac.smartclock.R
import com.sertac.smartclock.SmartClockActivity
import com.sertac.smartclock.ui.theme.Shapes
import com.sertac.smartclock.ui.theme.SmartClockTheme
import com.sertac.smartclock.view.RegisterScreen
import com.sertac.smartclock.viewmodel.LoginViewModel


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // Parameters
    val loginTc = remember { mutableStateOf("") }
    val loginPassword = remember { mutableStateOf("") }

    // Values
    val customResponse by remember { viewModel.customResponse }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 75.dp)
    ) {
        Text(text = "Login Screen",
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Welcome to AHTS",
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Thin
        )
        Spacer(modifier = Modifier.height(50.dp))

        Card(modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.background,
                shape = RoundedCornerShape(topStart = 75.dp, topEnd = 75.dp)
            ),
            shape = RoundedCornerShape(topStart = 75.dp, topEnd = 75.dp),
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomInput(textString = "Enter your tc no", mutableString = loginTc)
                Spacer(modifier = Modifier.height(10.dp))
                CustomInputWithPassword(textString = "Enter your password", mutableString = loginPassword)
                Spacer(modifier = Modifier.height(25.dp))
                LoginButton("Login"){
                    println("Button Clicked")
                    println("Login-TcNo --> ${loginTc.value}")
                    println("Login-Password --> ${loginPassword.value}")
                    viewModel.login(UserLogin(loginTc.value, loginPassword.value))
//                    viewModel.login(UserLogin("20659880052", "123456"))
//                    context.startActivity(Intent(context, SmartClockActivity::class.java))
                }
                Spacer(modifier = Modifier.height(50.dp))

                Text(modifier = Modifier.clickable {
                    navController.navigate("register_screen")
                },text = "Register here",
                    color = MaterialTheme.colors.primary,
                    fontSize = 24.sp
                )

            }
        }
    }

    if (customResponse.isSuccess){
        println("ChangeActivityChangeActivityChangeActivityChangeActivityChangeActivityChangeActivity")
        context.startActivity(Intent(context, SmartClockActivity::class.java))
    }


    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        if (isLoading){
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }

        if (errorMessage.isNotEmpty()){
            RetryView(error = errorMessage) {
                viewModel.login(UserLogin(loginTc.value, loginPassword.value))
            }
        }
    }
}

@Composable
fun LoginButton(btnText:String ,func:() -> Unit) {
    Button(
        onClick = func,
        modifier = Modifier
            .width(200.dp)
            .height(50.dp)
            .background(color = MaterialTheme.colors.primary, shape = CircleShape),
        shape = CircleShape,
        elevation = ButtonDefaults.elevation(5.dp)
    ) {
        Image(
            painterResource(id = R.drawable.login),
            contentDescription = "Cart button icon",
            modifier = Modifier.size(50.dp))

        Text(text = btnText,Modifier.padding(start = 10.dp), fontSize = 16.sp)
    }
}

@Composable
fun CustomInput(textString:String, mutableString:MutableState<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(text = textString,
            color = MaterialTheme.colors.primary,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        BasicTextField(value = mutableString.value, onValueChange = {
            mutableString.value = it
        },
            maxLines = 1,
            singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(color = MaterialTheme.colors.primary),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(color = MaterialTheme.colors.background, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        )
    }
}

@Composable
fun CustomInputWithPassword(textString:String, mutableString:MutableState<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(text = textString,
            color = MaterialTheme.colors.primary,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        BasicTextField(value = mutableString.value, onValueChange = {
            mutableString.value = it
        },
            maxLines = 1,
            singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(color = MaterialTheme.colors.primary),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(color = MaterialTheme.colors.background, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Composable
fun RetryView(
    error : String,
    onRetry : () -> Unit
) {
    Column {
        Text(text = error, color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { onRetry },
            modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Retry")
        }
    }
}