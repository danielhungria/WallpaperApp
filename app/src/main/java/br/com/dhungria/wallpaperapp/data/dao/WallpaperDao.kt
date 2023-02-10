package br.com.dhungria.wallpaperapp.data.dao

import androidx.room.*
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import br.com.dhungria.wallpaperapp.models.WallpaperModel

@Dao
interface WallpaperDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wallpaperModel: WallpaperModel)
    @Query("SELECT EXISTS (SELECT 1 FROM wallpaper_table WHERE favorite = true AND id = :id)")
    suspend fun verifyWasFavorite(id: String): Boolean

}