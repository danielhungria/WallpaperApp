package br.com.dhungria.wallpaperapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import br.com.dhungria.wallpaperapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallpaperViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

//    private val _wallpaperModel = MutableLiveData<List<WallpaperModel>>()
//    val wallpaperModel: LiveData<List<WallpaperModel>>
//        get() = _wallpaperModel
//
//    private val wallpaperList: MutableLiveData<List<WallpaperModel>> by lazy {
//        MutableLiveData<List<WallpaperModel>>().also {
//            loadWallpaperData()
//        }
//    }
//
//    private fun loadWallpaperData() {
//        repository.queryWallpaper().addOnCompleteListener {
//            if (it.isSuccessful && !it.result.isEmpty) {
//                wallpaperList.value = it.result.toObjects(WallpaperModel::class.java)
//            }
//        }
//    }
//
//    fun getWallpaperList(): LiveData<List<WallpaperModel>> {
//        return wallpaperList
//    }

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
                Toast.makeText(context, "Favoritado com Sucesso!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    suspend fun verifyWasFavorite(id: String): Boolean {
        return (repository.verifyWasFavorite(id))
    }

}