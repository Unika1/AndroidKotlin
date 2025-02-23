package com.example.newproject.repository

import com.example.newproject.model.RemedyModel
import com.google.firebase.database.DatabaseReference

class RemedyRepositoryImpl :RemedyRepository {
    override fun addRemedy(
        remedy: RemedyModel,
        database: DatabaseReference,
        callback: (Boolean, String?) -> Unit
    ) {
        val remedyId = database.push().key
        remedy.remedyId = remedyId ?: ""

        database.child("remedies").child(remedy.remedyId).setValue(remedy)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    override fun getRemedies(
        database: DatabaseReference,
        callback: (List<RemedyModel>?, String?) -> Unit
    ) {
        database.child("remedies").get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val remedies = snapshot.children.mapNotNull {
                        it.getValue(RemedyModel::class.java)
                    }
                    callback(remedies, null)
                } else {
                    callback(null, "No remedies found")
                }
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    override fun updateRemedy(
        remedy: RemedyModel,
        database: DatabaseReference,
        callback: (Boolean, String?) -> Unit
    ) {
        database.child("remedies").child(remedy.remedyId).setValue(remedy)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    override fun deleteRemedy(
        remedyId: String,
        database: DatabaseReference,
        callback: (Boolean, String?) -> Unit
    ) {
        database.child("remedies").child(remedyId).removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }
}