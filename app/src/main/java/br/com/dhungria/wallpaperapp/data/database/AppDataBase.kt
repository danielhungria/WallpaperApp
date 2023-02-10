package br.com.dhungria.wallpaperapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.dhungria.wallpaperapp.data.dao.WallpaperDao
import br.com.dhungria.wallpaperapp.models.WallpaperModel

@Database(
    entities = [WallpaperModel::class],
    version = 1
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getWallpaperDao(): WallpaperDao

}