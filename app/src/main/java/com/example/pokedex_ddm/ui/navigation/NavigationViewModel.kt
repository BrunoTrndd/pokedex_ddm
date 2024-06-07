package com.example.pokedex_ddm.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.pokedex_ddm.util.BottomNavItem

class MainViewModel : ViewModel() {
    private val _selectedItem = MutableLiveData<BottomNavItem>(BottomNavItem.Home)
    val selectedItem: LiveData<BottomNavItem> = _selectedItem

    fun onItemSelected(item: BottomNavItem) {
        _selectedItem.value = item
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, viewModel: MainViewModel) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorites,
        BottomNavItem.Profile,
    )
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        val selectedItem by viewModel.selectedItem.observeAsState(BottomNavItem.Home)
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = selectedItem == item,
                alwaysShowLabel = true,
                onClick = {
                    viewModel.onItemSelected(item)
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
