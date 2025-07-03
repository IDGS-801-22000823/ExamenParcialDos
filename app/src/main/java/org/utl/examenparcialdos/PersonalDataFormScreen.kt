package org.utl.examenparcialdos

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.utl.examenparcialdos.Usuario
import org.utl.examenparcialdos.FirestoreManager
import com.google.firebase.Timestamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataFormScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var paternal by remember { mutableStateOf("") }
    var maternal by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var selectedSex by remember { mutableStateOf("") }
    val context = LocalContext.current
    val firestoreManager = remember { FirestoreManager() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Datos Personales", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = paternal,
            onValueChange = { paternal = it },
            label = { Text("Apaterno") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = maternal,
            onValueChange = { maternal = it },
            label = { Text("Amaterno") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Fecha de nacimiento", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = day,
                onValueChange = { if (it.length <= 2) day = it },
                label = { Text("Dia") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = month,
                onValueChange = { if (it.length <= 2) month = it },
                label = { Text("Mes") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = year,
                onValueChange = { if (it.length <= 4) year = it },
                label = { Text("Año") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "SEXO", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedSex == "Masculino",
                onClick = { selectedSex = "Masculino" }
            )
            Text("Masculino")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = selectedSex == "Femenino",
                onClick = { selectedSex = "Femenino" }
            )
            Text("Femenino")
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                name = ""
                paternal = ""
                maternal = ""
                day = ""
                month = ""
                year = ""
                selectedSex = ""
            }) {
                Text("Limpiar")
            }
            Button(onClick = {
                if (name.isNotBlank() && paternal.isNotBlank() && maternal.isNotBlank() &&
                    day.isNotBlank() && month.isNotBlank() && year.isNotBlank() &&
                    selectedSex.isNotBlank()
                ) {
                    val userData = Usuario(
                        nombre = name,
                        apaterno = paternal,
                        amaterno = maternal,
                        dia = day.toInt(),
                        mes = month.toInt(),
                        anio = year.toInt(),
                        sexo = selectedSex,
                        puntuacion = 0.0,
                        horoscopo = "",
                        timestamp = Timestamp.now()
                    )

                    firestoreManager.saveUserData(
                        userData = userData,
                        onSuccess = { documentId ->
                            Toast.makeText(context, "Datos guardados", Toast.LENGTH_SHORT).show()
                            navController.navigate("zodiac_result/$name/$paternal/$maternal/$day/$month/$year/$documentId")
                        },
                        onFailure = { e ->
                            Toast.makeText(context, "Error al guardar: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    )

                } else {
                    Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Siguiente")
            }
        }
    }
}