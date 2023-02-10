package br.com.dhungria.wallpaperapp.repository

import br.com.dhungria.wallpaperapp.data.dao.WallpaperDao
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class Repository @Inject constructor(private val wallpaperDao: WallpaperDao) {

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
            .collection("category")
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
    }
    fun queryWallpaperFiltered(field: String, value: Any): Task<QuerySnapshot>{
        return firebaseFireStore
            .collection("wallpaper")
            .whereEqualTo(field, value)
            .get()
    }
    fun queryCategoryFiltered(field: String, value: Any): Task<QuerySnapshot>{
        return firebaseFireStore
            .collection("category")
            .whereEqualTo(field, value)
            .get()
    }

    suspend fun insert(wallpaperModel: WallpaperModel) = wallpaperDao.insert(wallpaperModel)

}