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
class MainViewModel @Inject constructor(
    private val firebaseRepository: Repository
) : ViewModel() {

    private var field: String = "popular"
    var value: Boolean = true

    private val _wallpaperModel = MutableLiveData<List<WallpaperModel>>()
    val wallpaperModel: LiveData<List<WallpaperModel>>
        get() = _wallpaperModel


    private val categoryList: MutableLiveData<List<CategoryModel>> by lazy {
        MutableLiveData<List<CategoryModel>>().also {
            loadCategoryData()
        }
    }

    fun loadCategoryData() {
        viewModelScope.launch {
            firebaseRepository.queryCategory().addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    categoryList.value = it.result.toObjects(CategoryModel::class.java)
                }
            }
        }
    }

    fun getWallpaperDataFiltered() {
        viewModelScope.launch {
            firebaseRepository.queryWallpaperFiltered(field, value).addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    _wallpaperModel.value = it.result.toObjects(WallpaperModel::class.java)
                }
            }
        }
    }

    fun getCategoryList(): LiveData<List<CategoryModel>> {
        return categoryList
    }

}