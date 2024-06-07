package com.example.pokedex_ddm.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun LoginFragment (
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Scaffold( ) {  paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row() {
                    Text("Poké", style = MaterialTheme.typography.body2, color = Color.Companion.White)
                    Text("dex", style = MaterialTheme.typography.body2, color = Color(0xFFCD3131))
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Usuário") })
                OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Senha") })
                Button(onClick={/*TODO*/}) {
                    Text("Login")
                }
            }
        }
    }
}