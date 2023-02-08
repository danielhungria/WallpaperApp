package br.com.dhungria.wallpaperapp.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class FirebaseRepository @Inject constructor() {

//    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseFirestoreCategory: FirebaseFirestore = FirebaseFirestore.getInstance()

//    fun getUser(): FirebaseUser?{
//        return firebaseAuth.currentUser
//    }

    fun queryWallpaper(): Task<QuerySnapshot>{
        return firebaseFireStore
            .collection("wallpaper")
            .get()
    }
    fun queryCategory(): Task<QuerySnapshot>{
        return firebaseFireStore
            .collection("Teste")
            .get()
    }

}