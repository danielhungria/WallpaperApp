package br.com.dhungria.wallpaperapp.ui.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.dhungria.wallpaperapp.R
import br.com.dhungria.wallpaperapp.adapter.MainAdapterCategories
import br.com.dhungria.wallpaperapp.adapter.MainAdapterPopular
import br.com.dhungria.wallpaperapp.adapter.WallpaperCategoryAdapter
import br.com.dhungria.wallpaperapp.databinding.FragmentCategoryBinding
import br.com.dhungria.wallpaperapp.databinding.FragmentMainBinding
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import br.com.dhungria.wallpaperapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WallpaperCategoryFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentCategoryBinding

    private val wallpaperCategoryAdapter = WallpaperCategoryAdapter()


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
        viewModel.getWallpaperList().observe(viewLifecycleOwner) {
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
