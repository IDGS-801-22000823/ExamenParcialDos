package org.utl.examenparcialdos

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import org.utl.examenparcialdos.Usuario

class FirestoreManager {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("Usuarios")

    fun saveUserData(userData: Usuario, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        usersCollection.add(userData)
            .addOnSuccessListener { documentReference ->
                Log.d("FirestoreManager", "DocumentSnapshot added with ID: ${documentReference.id}")
                onSuccess(documentReference.id)
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreManager", "Error adding document", e)
                onFailure(e)
            }
    }

    fun updateUserData(documentId: String, updates: Map<String, Any>, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        usersCollection.document(documentId)
            .update(updates)
            .addOnSuccessListener {
                Log.d("FirestoreManager", "DocumentSnapshot successfully updated!")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreManager", "Error updating document", e)
                onFailure(e)
            }
    }
}