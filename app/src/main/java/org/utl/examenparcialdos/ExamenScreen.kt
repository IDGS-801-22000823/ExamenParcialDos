package org.utl.examenparcialdos

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.utl.examenparcialdos.Pregunta
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamenScreen(navController: NavController, documentId: String) {
    val questions = remember {
        listOf(
            Pregunta("¿Cual es 7*7?", listOf("77", "48", "49", "17"), 2),
            Pregunta("¿Capital de Francia?", listOf("Tlaxcala", "Madrid", "Paris", "Francia"), 2),
            Pregunta("¿Quien le gana a Goku?", listOf("Nadie", "Vegeta", "Halo Verde", "Linux"), 0),
            Pregunta("¿Numero de continentes?", listOf("8", "5", "6", "7"), 3),
            Pregunta("¿Instrumento musical de cuerda?", listOf("Trompeta", "Violin", "Flauta", "Bateria"), 1),
            Pregunta("¿Animal mas raro segun la ciencia?", listOf("Perezoso", "Ajolote", "ornitorrinco", "Armadillo"), 2)
        )
    }

    val selectedOptions = remember { mutableStateMapOf<Int, Int?>() }
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Examen de Conocimientos", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        questions.forEachIndexed { qIndex, question ->
            Text(text = "${qIndex + 1}.- ${question.questionText}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            question.options.forEachIndexed { oIndex, option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = selectedOptions[qIndex] == oIndex,
                        onClick = { selectedOptions[qIndex] = oIndex }
                    )
                    Text(text = option)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            var score = 0
            var allAnswered = true
            for (i in questions.indices) {
                if (selectedOptions[i] == null) {
                    allAnswered = false
                    break
                }
                if (selectedOptions[i] == questions[i].correctAnswerIndex) {
                    score++
                }
            }

            if (allAnswered) {
                navController.navigate("quiz_result/$score/$documentId")
            } else {
                Toast.makeText(context, "Responde todas las preguntas >:v .", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Terminar Examen")
        }
    }
}