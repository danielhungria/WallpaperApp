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

    var field: String = "category"
    var value: Any = ""

    private val wallpaperList: MutableLiveData<List<WallpaperModel>> by lazy {
        MutableLiveData<List<WallpaperModel>>().also {
            loadWallpaperData()
        }
    }

    private val wallpaperListFiltered: MutableLiveData<List<WallpaperModel>> by lazy {
        MutableLiveData<List<WallpaperModel>>().also {
            getWallpaperDataFiltered()
        }
    }

    private val categoryList: MutableLiveData<List<CategoryModel>> by lazy {
        MutableLiveData<List<CategoryModel>>().also {
            loadCategoryData()
        }
    }

    private fun loadWallpaperData() {
        firebaseRepository.queryWallpaper().addOnCompleteListener {
            if (it.isSuccessful && !it.result.isEmpty) {
                wallpaperList.value = it.result.toObjects(WallpaperModel::class.java)
            }
        }
    }

    private fun loadCategoryData() {
        viewModelScope.launch {
            firebaseRepository.queryCategory().addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    categoryList.value = it.result.toObjects(CategoryModel::class.java)
                }
            }
        }
    }

    //    fun loadCategoryDataFiltered(){
//        viewModelScope.launch {
//            firebaseRepository.queryCategoryFiltered("name", "Car").addOnCompleteListener {
//                if (it.isSuccessful && !it.result.isEmpty) {
//                    categoryList.value = it.result.toObjects(CategoryModel::class.java)
//                }
//            }
//        }
//    }
    private fun getWallpaperDataFiltered() {
        viewModelScope.launch {
            firebaseRepository.queryWallpaperFiltered(field, value).addOnCompleteListener {
                if (it.isSuccessful && !it.result.isEmpty) {
                    wallpaperListFiltered.value = it.result.toObjects(WallpaperModel::class.java)
                }
            }
        }
    }

    fun getWallpaperList(): LiveData<List<WallpaperModel>> {
        return wallpaperList
    }

    fun getWallpaperListFiltered(): LiveData<List<WallpaperModel>> {
        return wallpaperListFiltered
    }

    fun getCategoryList(): LiveData<List<CategoryModel>> {
        return categoryList
    }


}