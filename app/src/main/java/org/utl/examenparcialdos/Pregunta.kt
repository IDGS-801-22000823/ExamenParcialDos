package org.utl.examenparcialdos

data class Pregunta (
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)