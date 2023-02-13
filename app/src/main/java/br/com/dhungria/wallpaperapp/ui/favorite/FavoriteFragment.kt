package br.com.dhungria.wallpaperapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.dhungria.wallpaperapp.R
import br.com.dhungria.wallpaperapp.adapter.FavoriteAdapter
import br.com.dhungria.wallpaperapp.adapter.WallpaperCategoriesAdapter
import br.com.dhungria.wallpaperapp.databinding.FavoriteFragmentBinding
import br.com.dhungria.wallpaperapp.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModels()
    private val favoriteAdapter = FavoriteAdapter(onClick = {
        findNavController().navigate(
            R.id.action_favorite_fragment_to_fragment_wallpaper_category,
            bundleOf("IMAGE_TO_SHOW" to it)
        )
    })
    private lateinit var binding: FavoriteFragmentBinding


    private fun setupRecyclerFavorite() {
        binding.recyclerviewFavoriteFragment.apply {
            adapter = favoriteAdapter
            val mLayoutManager = GridLayoutManager(requireContext(), 3)
            layoutManager = mLayoutManager
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerFavorite()
        viewModel.wallpaperModel.observe(viewLifecycleOwner){
            favoriteAdapter.updateList(it)
        }
        viewModel.getAllFavoritesWallpapers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
