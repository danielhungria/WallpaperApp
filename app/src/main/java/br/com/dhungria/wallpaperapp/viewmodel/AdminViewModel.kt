package br.com.dhungria.wallpaperapp.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import br.com.dhungria.wallpaperapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: Repository
    ): ViewModel() {


    fun uploadWallpaper(
        name: String,
        fileName: String,
        image: String,
        category: String,
        popular: Boolean,
        context: Context?
    ) {
        try {
            val uuid = UUID.randomUUID().toString()
            val saveWallpaperModel = WallpaperModel(
                id = uuid,
                name = name,
                fileName = fileName,
                image = image,
                category = category,
                popular = popular
            )
            if (name.isNotBlank() && fileName.isNotBlank() && image.isNotBlank() && category.isNotBlank()) {
                repository.uploadWallpaper(saveWallpaperModel, uuid)
                context?.let {
                    Toast.makeText(it, "Success", Toast.LENGTH_LONG).show()
                }
            }else{
                context?.let {
                    Toast.makeText(it, "Error", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            Log.e("AdminViewModel", "uploadWallpaper: $e")
        }
    }

}