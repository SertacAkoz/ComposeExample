package com.sertac.smartclock.view

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sertac.smartclock.Dto.UserLogin
import com.sertac.smartclock.Dto.UserRegister
import com.sertac.smartclock.SmartClockActivity
import com.sertac.smartclock.ui.theme.SmartClockTheme
import com.sertac.smartclock.util.SweetAlert
import com.sertac.smartclock.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel:RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // Paramaters
    val registerEmail = remember { mutableStateOf("") }
    val registerTc = remember { mutableStateOf("") }
    val registerPassword = remember { mutableStateOf("") }
    val registerGender = remember { mutableStateOf("") }
    val registerAge = remember { mutableStateOf("") }
    val registerName = remember { mutableStateOf("") }

    // View model values
    val customResponse by remember { viewModel.customResponse }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

//    var isStart = true

    var isStart by remember { mutableStateOf(true) }

    println("ASYNC --> ${isStart}")


    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 25.dp)
    ) {
        Text(text = "Register Screen",
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.dp))

        Card(modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.background,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            ),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomInput("Enter your email", mutableString = registerEmail)
                Spacer(modifier = Modifier.height(10.dp))
                CustomInput(textString = "Enter your password", mutableString = registerPassword)
                Spacer(modifier = Modifier.height(10.dp))
                CustomInput(textString = "Enter your tc no", mutableString = registerTc)
                Spacer(modifier = Modifier.height(10.dp))
                CustomInput(textString = "Enter your name", mutableString = registerName)
                Spacer(modifier = Modifier.height(10.dp))
                CustomInput(textString = "Enter your gender", mutableString = registerGender)
                Spacer(modifier = Modifier.height(10.dp))
                CustomInput(textString = "Enter your age", mutableString = registerAge)
                Spacer(modifier = Modifier.height(25.dp))
                LoginButton("Register"){
                    println("Button Clicked")
                    isStart = false
                    viewModel.register(UserRegister(
                        registerTc.value,
                        registerEmail.value,
                        registerName.value,
                        registerAge.value.toInt(),
                        registerGender.value.toInt(),
                        registerPassword.value
                        )
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))

            }
        }
    }

    if (customResponse.isSuccess && !isStart){
        isStart = true
        navController.navigate("login_screen"){
            launchSingleTop = true
        }
        println("RegisterScreen-customResponse-Success")
        SweetAlert.successPopup(context,"Success", customResponse.messsage)

    }else if (!customResponse.isSuccess && !customResponse.messsage.equals("null") && !isStart){
        isStart = true
        SweetAlert.errorPopup(context, "Error", customResponse.messsage)
    }else{
        println("Register-customResponse --> ${customResponse.isSuccess}")
        println("Register-isStart --> ${isStart}")
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        if (isLoading){
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }

        if (errorMessage.isNotEmpty()){
            println("RegisterScreen-isNotEmpty")
            RetryView(error = errorMessage) {
                viewModel.register(UserRegister(
                    registerTc.value,
                    registerEmail.value,
                    registerName.value,
                    registerAge.value.toInt(),
                    registerGender.value.toInt(),
                    registerPassword.value
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegister() {
    SmartClockTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.primary
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
            ) {
                RegisterScreen(navController = rememberNavController())
            }
        }
    }
}