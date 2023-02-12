package br.com.dhungria.wallpaperapp.repository

import br.com.dhungria.wallpaperapp.data.dao.WallpaperDao
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class Repository @Inject constructor(private val wallpaperDao: WallpaperDao) {

    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()

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

    fun getAll() = wallpaperDao.getAll()
    suspend fun insert(wallpaperModel: WallpaperModel) = wallpaperDao.insert(wallpaperModel)

    suspend fun verifyWasFavorite(id: String):Boolean = wallpaperDao.verifyWasFavorite(id)

    fun getAllFavoritesWallpapers() = wallpaperDao.getAllFavoritesWallpapers()

    suspend fun delete(id: String) = wallpaperDao.delete(id)

}