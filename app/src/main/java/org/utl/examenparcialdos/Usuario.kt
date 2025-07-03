package org.utl.examenparcialdos

import com.google.firebase.Timestamp

data class Usuario (
    val nombre: String = "",
    val apaterno: String = "",
    val amaterno: String = "",
    val dia: Int = 0,
    val mes: Int = 0,
    val anio: Int = 0,
    val sexo: String = "",
    var puntuacion: Double = 0.0,
    var horoscopo: String = "",
    val timestamp: Timestamp = Timestamp.now()
)