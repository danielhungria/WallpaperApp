package br.com.dhungria.wallpaperapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "wallpaper_list")
@Parcelize
data class WallpaperModel(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val category: String = "",
    val new: Boolean = false,
    val popular: Boolean = false,
    val date: String? = "",
    val favorite: Boolean = false
):Parcelable
