package org.utl.examenparcialdos

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.utl.examenparcialdos.ZodiacoCalculadora
import org.utl.examenparcialdos.R
import org.utl.examenparcialdos.FirestoreManager

@Composable
fun ZodiacoResuladotScreen(
    navController: NavController,
    name: String,
    paternal: String,
    maternal: String,
    day: Int,
    month: Int,
    year: Int,
    documentId: String
) {
    val fullName = "$name $paternal $maternal"
    val age = ZodiacoCalculadora.calculateAge(day, month, year)
    val (zodiacAnimal, zodiacImageName) = ZodiacoCalculadora.getChineseZodiac(year)
    val context = LocalContext.current
    val firestoreManager = remember { FirestoreManager() }

    val imageResourceId = androidx.compose.ui.platform.LocalContext.current.resources.getIdentifier(
        zodiacImageName,
        "drawable",
        androidx.compose.ui.platform.LocalContext.current.packageName
    )

    LaunchedEffect(key1 = documentId, key2 = zodiacAnimal) {
        if (documentId.isNotBlank() && zodiacAnimal.isNotBlank()) {
            val updates = mapOf("horoscopo" to zodiacAnimal)
            firestoreManager.updateUserData(
                documentId = documentId,
                updates = updates,
                onSuccess = {
                    Toast.makeText(context, "Horoscopo guardado", Toast.LENGTH_SHORT).show()
                },
                onFailure = { e ->
                    Toast.makeText(context, "Error al guardar horoscopo: ${e.message}", Toast.LENGTH_LONG).show()
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
        Text(text = "Hola $fullName", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Tienes $age años y tu signo zodiacal", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        if (imageResourceId != 0) {
            Image(
                painter = painterResource(id = imageResourceId),
                contentDescription = zodiacAnimal,
                modifier = Modifier.size(120.dp)
            )
        } else {
            Text(text = "Imagen del signo zodiacal no encontrada", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Es $zodiacAnimal", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            navController.navigate("exam/$documentId")
        }) {
            Text("Hacer Encuesta")
        }
    }
}