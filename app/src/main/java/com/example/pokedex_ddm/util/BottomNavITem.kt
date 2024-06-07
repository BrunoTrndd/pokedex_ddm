package com.example.pokedex_ddm.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title: String, var icon: ImageVector, var screenRoute: String) {
    object Home : BottomNavItem("Home", Icons.Default.Home, "pokemon_list_fragment")
    object Profile : BottomNavItem("Profile", Icons.Default.Person, "profile")
    object Settings : BottomNavItem("Settings", Icons.Default.Settings, "settings")
}