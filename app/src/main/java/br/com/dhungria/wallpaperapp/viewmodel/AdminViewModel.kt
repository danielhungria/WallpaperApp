package br.com.dhungria.wallpaperapp.viewmodel

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
        image: String,
        category: String,
        popular: Boolean
    ){
        val uuid = UUID.randomUUID().toString()
        val saveWallpaperModel = WallpaperModel(
            id = uuid,
            name = name,
            image = image,
            category = category,
            popular = popular
        )
        repository.uploadWallpaper(saveWallpaperModel, uuid)
    }

}