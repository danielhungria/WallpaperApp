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
class WallpaperCategoryViewModel @Inject constructor(
    private val firebaseRepository: Repository
) : ViewModel() {

    private var field: String = "category"
    var value: Any = ""


    private val wallpaperListFiltered: MutableLiveData<List<WallpaperModel>> by lazy {
        MutableLiveData<List<WallpaperModel>>().also {
            getWallpaperDataFiltered()
        }
    }

    private fun getWallpaperDataFiltered() {
        viewModelScope.launch {
            firebaseRepository.queryWallpaperFiltered(field, value).addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    wallpaperListFiltered.value = it.result.toObjects(WallpaperModel::class.java)
                }
            }
        }
    }

    fun getWallpaperListFiltered(): LiveData<List<WallpaperModel>> {
        return wallpaperListFiltered
    }

}