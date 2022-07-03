package com.sertac.smartclock

sealed class NavigationItem(var route: String, var icon: Int, var title: String){
    object Home : NavigationItem("home", R.drawable.home, "Home")
    object Pulse : NavigationItem("Pulse", R.drawable.home, "Pulse")
    object Step : NavigationItem("Step", R.drawable.home, "Step")
    object Post : NavigationItem("Post", R.drawable.home, "Post")
}
