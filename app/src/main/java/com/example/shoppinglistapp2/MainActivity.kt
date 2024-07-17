package com.example.shoppinglistapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglistapp2.ui.theme.ShoppingListApp2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListApp2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()
                    .padding(top = 32.dp, bottom = 32.dp)) { innerPadding ->

                //ShoppingListApp()

                    Navigation()


                }
            }
        }
    }
}



@Composable
fun Navigation(){
    val navController = rememberNavController()
    val viewModel : LocationViewModel = viewModel()
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)

    NavHost(navController, startDestination = "shoppinglistscreen"){
        composable("shoppinglistscreen"){
            ShoppingListApp(
                locationUtils = locationUtils,
                viewModel = viewModel,
                navController = navController,
                context = context,
                address = viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address"
            )
        }

        dialog("locationscreen"){backStack ->
            viewModel.location.value?.let { it1 ->

                LocationSelectionScreen(location = it1, onLocationSelected = { locationData ->
                    viewModel.fetchAddress("${locationData.latitude},${locationData.longitude}")
                    navController.popBackStack()
                })


            }
        }
    }


}










