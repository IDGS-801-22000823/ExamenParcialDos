package org.utl.examenparcialdos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.utl.examenparcialdos.ui.theme.ExamenParcialDosTheme
import org.utl.examenparcialdos.ExamenScreen
import org.utl.examenparcialdos.PersonalDataFormScreen
import org.utl.examenparcialdos.QuizResultadoScreen
import org.utl.examenparcialdos.ZodiacoResuladotScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenParcialDosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "personal_data_form") {
        composable("personal_data_form") {
            PersonalDataFormScreen(navController = navController)
        }
        composable("zodiac_result/{name}/{paternal}/{maternal}/{day}/{month}/{year}/{documentId}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val paternal = backStackEntry.arguments?.getString("paternal") ?: ""
            val maternal = backStackEntry.arguments?.getString("maternal") ?: ""
            val day = backStackEntry.arguments?.getString("day")?.toIntOrNull() ?: 1
            val month = backStackEntry.arguments?.getString("month")?.toIntOrNull() ?: 1
            val year = backStackEntry.arguments?.getString("year")?.toIntOrNull() ?: 2000
            val documentId = backStackEntry.arguments?.getString("documentId") ?: ""

            ZodiacoResuladotScreen(
                navController = navController,
                name = name,
                paternal = paternal,
                maternal = maternal,
                day = day,
                month = month,
                year = year,
                documentId = documentId
            )
        }
        composable("exam/{documentId}") { backStackEntry ->
            val documentId = backStackEntry.arguments?.getString("documentId") ?: ""
            ExamenScreen(navController = navController, documentId = documentId)
        }
        composable("quiz_result/{score}/{documentId}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            val documentId = backStackEntry.arguments?.getString("documentId") ?: ""
            QuizResultadoScreen(navController = navController, score = score, documentId = documentId)
        }
    }
}