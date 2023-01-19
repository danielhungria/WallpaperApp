package br.com.dhungria.wallpaperapp.models

import com.google.firebase.Timestamp

data class WallpaperModel(
    val name: String = "",
    val image: String = "",
    val thumbnail: String = "",
    val category: String = "",
    val new: Boolean = false,
    val popular: Boolean = false,
    val date: Timestamp? = null
)
