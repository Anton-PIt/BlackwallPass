package com.cybersec.blackwallpass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cybersec.blackwallpass.ui.screens.auth.presentation.AuthScreen
import com.cybersec.blackwallpass.ui.screens.auth.presentation.AuthViewModel
import com.cybersec.blackwallpass.ui.theme.BlackwallPassTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

sealed interface Screen {
	@Serializable object Auth : Screen
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			BlackwallPassTheme {
				val navController = rememberNavController()

				NavHost(
					navController = navController,
					startDestination = Screen.Auth
				) {
					composable<Screen.Auth> {
						val authViewModel: AuthViewModel = hiltViewModel()
						AuthScreen(
							viewModel = authViewModel
						)
					}
				}
			}
		}
	}
}