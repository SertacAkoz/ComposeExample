package com.sertac.smartclock

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sertac.smartclock.backgroundservice.PostService
import com.sertac.smartclock.ui.theme.SmartClockTheme
import com.sertac.smartclock.view.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SmartClockActivity : AppCompatActivity() {

    var postService: Intent? = null

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            setContent {
                SmartClockTheme {
//                    Scaffold(
//                        bottomBar = {BottomNavigationBar()}
//                    ) {
//                        Surface(
//                            modifier = Modifier.fillMaxSize(),
//                            color = MaterialTheme.colors.primary,
//                        ) {
//                            Column(modifier = Modifier
//                                .fillMaxSize()
//                                .background(color = MaterialTheme.colors.primary)
//                            ) {
//
//                            }
//                        }
//                    }

                    SmartClockScreen()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        println("Onstart")
        try {
            println("Onstart")
            postService = Intent(this, PostService::class.java)
            startService(postService)
        }catch (e:Exception){
            println("Exception-Service-Start --> ${e}")
        }
    }

    override fun onStop() {
        super.onStop()

        stopService(postService)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SmartClockScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {BottomNavigationBar(navController)}
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.surface,
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.surface)
            ) {
                Navigation(navController = navController)
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
//        composable(NavigationItem.Pulse.route) {
//            PulseScreen()
//        }
//        composable(NavigationItem.Step.route) {
//            StepScreen()
//        }
        composable(NavigationItem.Post.route) {
            PostScreen()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
//        NavigationItem.Pulse,
//        NavigationItem.Step,
        NavigationItem.Post
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.onPrimary,
        contentColor = MaterialTheme.colors.primary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title, modifier = Modifier.size(25.dp)) },
                label = { Text(text = item.title) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.primary,
                alwaysShowLabel = false,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSmartClockScreen() {
    SmartClockTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colors.primary
//        ) {
//            Column(modifier = Modifier
//                .fillMaxSize()
//                .background(color = MaterialTheme.colors.primary)
//            ) {
//                BottomNavigationBar()
//            }
//        }

        SmartClockScreen()

//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colors.primary
//        ) {
//            Column(modifier = Modifier
//                .fillMaxSize()
//                .background(color = MaterialTheme.colors.primary)
//            ) {
//                LoginScreen(navController = rememberNavController())
//            }
//        }
    }
}