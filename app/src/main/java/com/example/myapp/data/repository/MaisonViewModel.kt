package com.example.myapp.data.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.myapp.model.Maison
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class MaisonViewModel: ViewModel() {
    fun saveData(
        maisonData: Maison,
        context: Context
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("maison")
            .document(maisonData.typeM)

        try {
            fireStoreRef.set(maisonData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Données enregistrées avec succès", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun retrieveData(
        maisonID: String,
        context: Context,
        data: (Maison) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("maison")
            .document(maisonID)

        try {
            fireStoreRef.get()
                .addOnSuccessListener {
                    // for getting single or particular document
                    if (it.exists()) {
                        val maisonData = it.toObject<Maison>()!!
                        data(maisonData)
                    } else {
                        Toast.makeText(context, "No House Data Found", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }
    fun updateData(
        maisonID: String,
        updatedData: Map<String, Any>,
        context: Context,
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("maison")
            .document(maisonID)

        try {
            fireStoreRef.update(updatedData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Données mises à jour avec succès", Toast.LENGTH_SHORT)
                        .show()
                    //navController.popBackStack()
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteData(
        maisonID: String,
        context: Context,
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("maison")
            .document(maisonID)

        try {
            fireStoreRef.delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "Maisons supprimées avec succès", Toast.LENGTH_SHORT)
                        .show()
                    //navController.popBackStack()
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun searchData(
        query: String,
        context: Context,
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("maison")

        try {
            fireStoreRef.whereEqualTo("champRecherche", query)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    // Traitement des résultats de la recherche
                    for (document in querySnapshot.documents) {
                        val maison = document.toObject(Maison::class.java)
                        // Faites quelque chose avec l'objet 'maison' correspondant à la recherche
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }


}