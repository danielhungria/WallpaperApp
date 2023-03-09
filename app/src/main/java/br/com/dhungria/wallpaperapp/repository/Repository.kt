package br.com.dhungria.wallpaperapp.repository

import br.com.dhungria.wallpaperapp.data.dao.WallpaperDao
import br.com.dhungria.wallpaperapp.models.SupportModel
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID
import javax.inject.Inject

class Repository @Inject constructor(private val wallpaperDao: WallpaperDao) {

    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun queryCategory(): Task<QuerySnapshot> {
        return firebaseFireStore
            .collection("category")
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
    }

    fun queryWallpaperFiltered(field: String, value: Any): Task<QuerySnapshot> {
        return firebaseFireStore
            .collection("wallpaper")
            .whereEqualTo(field, value)
            .get()
    }

    fun queryAllWallpaper(): Task<QuerySnapshot> {
        return firebaseFireStore
            .collection("wallpaper")
            .get()
    }

    fun querySupportService(): Task<QuerySnapshot>{
        return firebaseFireStore
            .collection("supportService")
            .get()
    }

    fun uploadWallpaper(wallpaperModel: WallpaperModel, uuid: String) {
        Firebase.firestore
            .collection("wallpaper")
            .document(uuid)
            .set(wallpaperModel)
    }

    fun uploadSupport(supportModel: SupportModel, uuid: String){
        Firebase.firestore
            .collection("support")
            .document(uuid)
            .set(supportModel)
    }

    fun getAll() = wallpaperDao.getAll()
    suspend fun insert(wallpaperModel: WallpaperModel) = wallpaperDao.insert(wallpaperModel)

    suspend fun verifyWasFavorite(id: String): Boolean = wallpaperDao.verifyWasFavorite(id)

    fun getAllFavoritesWallpapers() = wallpaperDao.getAllFavoritesWallpapers()

    suspend fun delete(id: String) = wallpaperDao.delete(id)

}