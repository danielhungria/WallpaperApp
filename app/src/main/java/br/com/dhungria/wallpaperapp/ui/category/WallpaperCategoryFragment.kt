package br.com.dhungria.wallpaperapp.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import br.com.dhungria.wallpaperapp.adapter.WallpaperCategoriesAdapter
import br.com.dhungria.wallpaperapp.databinding.FragmentCategoryBinding
import br.com.dhungria.wallpaperapp.models.CategoryModel
import br.com.dhungria.wallpaperapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WallpaperCategoryFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private val wallpaperCategoryAdapter = WallpaperCategoriesAdapter()
    private val category by lazy { arguments?.getParcelable<CategoryModel>("CATEGORY_TO_SHOW") }
    private lateinit var binding: FragmentCategoryBinding


    private fun setupRecyclerViewCategory() {
        binding.recyclerviewCategoriesFragmentCategory.apply {
            adapter = wallpaperCategoryAdapter
            val mLayoutManager = GridLayoutManager(requireContext(), 3)
            layoutManager = mLayoutManager
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewCategory()
        viewModel.value = category?.name ?: ""
        viewModel.getWallpaperListFiltered().observe(viewLifecycleOwner) {
            wallpaperCategoryAdapter.updateList(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }
}
