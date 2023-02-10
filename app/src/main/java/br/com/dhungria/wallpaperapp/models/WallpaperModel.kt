package br.com.dhungria.wallpaperapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Entity(tableName = "wallpaper_table")
@Parcelize
data class WallpaperModel(
    @PrimaryKey(autoGenerate = true)
    val idDB: Int = 0,
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val category: String = "",
    val popular: Boolean = false,
    val favorite: Boolean = false
):Parcelable
