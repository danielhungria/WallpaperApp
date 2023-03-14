package br.com.dhungria.wallpaperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.wallpaperapp.models.CategoryModel
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import br.com.dhungria.wallpaperapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var adLoad: Int = 0
    var query = ""
    var newText = ""

    private val _wallpaperModel = MutableLiveData<List<WallpaperModel>>()
    val wallpaperModel: LiveData<List<WallpaperModel>>
        get() = _wallpaperModel


    fun queryAllWallpaper() {
        viewModelScope.launch {
            repository.queryAllWallpaper().addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    _wallpaperModel.value = it.result.toObjects(WallpaperModel::class.java)
                }
            }
        }
    }

}