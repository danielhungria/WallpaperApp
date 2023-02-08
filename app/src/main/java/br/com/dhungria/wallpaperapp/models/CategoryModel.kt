package br.com.dhungria.wallpaperapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(
    val name: String = "",
    val image: String = ""
):Parcelable
