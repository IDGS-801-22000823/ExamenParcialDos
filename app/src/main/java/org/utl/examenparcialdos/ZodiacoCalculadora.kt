package org.utl.examenparcialdos

import java.util.Calendar


object ZodiacoCalculadora {
    fun calculateAge(day: Int, month: Int, year: Int): Int {
        val dob = Calendar.getInstance().apply {
            set(year, month - 1, day) // El mes es de índice 0
        }
        val today = Calendar.getInstance()

        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return age
    }

    fun getChineseZodiac(year: Int): Pair<String, String> {
        val animals = arrayOf(
            "Rata", "Buey", "Tigre", "Conejo", "Dragón", "Serpiente",
            "Caballo", "Cabra", "Mono", "Gallo", "Perro", "Cerdo"
        )
        val imageResourceNames = arrayOf(
            "rata", "buey", "tigre", "conejo", "dragon", "serpiente",
            "caballo", "cabra", "mono", "gallo", "perro", "cerdo"
        )
        val index = (year - 1900) % 12
        return Pair(animals.getOrNull(index) ?: "", imageResourceNames.getOrNull(index) ?: "")
    }
}