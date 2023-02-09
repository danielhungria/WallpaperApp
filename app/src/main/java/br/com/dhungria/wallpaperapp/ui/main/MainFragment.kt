package br.com.dhungria.wallpaperapp.ui.main

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
import br.com.dhungria.wallpaperapp.databinding.FragmentMainBinding
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import br.com.dhungria.wallpaperapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    private val mainAdapterPopular = MainAdapterPopular(onClick = {
        findNavController().navigate(R.id.action_fragment_main_to_wallpaper_fragment, bundleOf("IMAGE_TO_SHOW" to it))
    })
    private val mainAdapterCategories = MainAdapterCategories(onClick = {
        findNavController().navigate(R.id.action_fragment_main_category_to_fragment_wallpaper_category, bundleOf("CATEGORY_TO_SHOW" to it))
    })


    private fun setupRecyclerViewPopular() {
        binding.recyclerviewPopularFragmentMain.apply {
            adapter = mainAdapterPopular
            val mLayoutManager = LinearLayoutManager(requireContext())
                mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = mLayoutManager
        }
    }
    private fun setupRecyclerViewCategories() {
        binding.recyclerviewCategoriesFragmentMain.apply {
            adapter = mainAdapterCategories
            val mLayoutManager = GridLayoutManager(requireContext(), 2)
            layoutManager = mLayoutManager

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewPopular()
        setupRecyclerViewCategories()
        viewModel.getWallpaperList().observe(viewLifecycleOwner, Observer{
            mainAdapterPopular.updateList(it)
        })
        viewModel.getCategoryList().observe(viewLifecycleOwner) {
            mainAdapterCategories.updateList(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }
}
