package org.utl.examenparcialdos

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.utl.examenparcialdos.FirestoreManager

@Composable
fun QuizResultadoScreen(navController: NavController, score: Int, documentId: String) {
    val context = LocalContext.current
    val firestoreManager = remember { FirestoreManager() }

    val totalQuestions = 6
    val calificacionFinal = (score.toDouble() / totalQuestions) * 10

    LaunchedEffect(key1 = documentId, key2 = calificacionFinal) {
        if (documentId.isNotBlank()) {
            val updates = mapOf("puntuacion" to calificacionFinal)
            firestoreManager.updateUserData(
                documentId = documentId,
                updates = updates,
                onSuccess = {
                    Toast.makeText(context, "Calificacion final guardada", Toast.LENGTH_SHORT).show()
                },
                onFailure = { e ->
                    Toast.makeText(context, "Error al guardar calificacion final: ${e.message}", Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Resultados del Cuestionario", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Respuestas bien: $score de $totalQuestions", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        val calificacionFormateada = String.format("%.2f", calificacionFinal)
        Text(text = "Calificacion: $calificacionFormateada", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            navController.navigate("personal_data_form") {
                popUpTo("personal_data_form") {
                    inclusive = true
                }
            }
        }) {
            Text("Hacer Otro Registro")
        }
    }
}