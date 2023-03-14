package br.com.dhungria.wallpaperapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.wallpaperapp.R
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import br.com.dhungria.wallpaperapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallpaperViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var adLoad: Int = 0
    var hideElements: Boolean = false
    var buttonExpanded: Boolean = false


    private val _wallpaperModel = MutableLiveData<List<WallpaperModel>>()
    val wallpaperModel: LiveData<List<WallpaperModel>>
        get() = _wallpaperModel

    fun fetchWallpaper(){
        viewModelScope.launch {
            repository.getAll().collect(){
                _wallpaperModel.postValue(it)
            }
        }
    }

    fun onSaveEventFavorite(
        id: String,
        name: String,
        image: String,
        category: String,
        context: Context
    ) {
        viewModelScope.launch {
            val wallpaperToSave = WallpaperModel(
                id = id,
                name = name,
                image = image,
                category = category,
                favorite = true
            )
            if (!repository.verifyWasFavorite(id)) {
                repository.insert(wallpaperToSave)
                Toast.makeText(context, context.getString(R.string.favorite_image_success), Toast.LENGTH_SHORT).show()
            }
        }
    }

    suspend fun verifyWasFavorite(id: String): Boolean {
        return (repository.verifyWasFavorite(id))
    }

    fun removeFavorite(id: String, context: Context) {
        viewModelScope.launch {
            repository.delete(id)
        }
        Toast.makeText(context, context.getString(R.string.remove_favorite), Toast.LENGTH_LONG).show()
    }
}