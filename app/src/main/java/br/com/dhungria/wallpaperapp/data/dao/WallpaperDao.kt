package br.com.dhungria.wallpaperapp.data.dao

import androidx.room.*
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpaperDao {
    @Query("SELECT * FROM wallpaper_table")
    fun getAll(): Flow<List<WallpaperModel>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wallpaperModel: WallpaperModel)
    @Query("SELECT EXISTS (SELECT 1 FROM wallpaper_table WHERE favorite = 1 AND id = :id)")
    suspend fun verifyWasFavorite(id: String): Boolean
    @Query("SELECT * FROM wallpaper_table WHERE favorite = 1")
    fun getAllFavoritesWallpapers(): Flow<List<WallpaperModel>>
    @Query("SELECT * FROM wallpaper_table WHERE popular = 1")
    fun getAllPopularWallpapers(): Flow<List<WallpaperModel>>
    @Query("DELETE FROM wallpaper_table WHERE id = :id")
    suspend fun delete(id: String)

}