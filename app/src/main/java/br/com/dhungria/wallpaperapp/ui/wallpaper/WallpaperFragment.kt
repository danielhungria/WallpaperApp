package br.com.dhungria.wallpaperapp.ui.wallpaper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import br.com.dhungria.wallpaperapp.R
import br.com.dhungria.wallpaperapp.databinding.WallpaperLayoutFragmentBinding
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import br.com.dhungria.wallpaperapp.viewmodel.WallpaperViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WallpaperFragment : Fragment() {

    private lateinit var binding: WallpaperLayoutFragmentBinding
    private val wallpaperModel by lazy { arguments?.getParcelable<WallpaperModel>("IMAGE_TO_SHOW") }
    private val viewModel: WallpaperViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WallpaperLayoutFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wallpaperModel?.let { wallpaperModel ->
            setupImageView(wallpaperModel)
            setupFavoriteButton(wallpaperModel)
            setupFavoriteBackgroundButton(wallpaperModel)
        }
    }

    private fun setupFavoriteBackgroundButton(wallpaperModel: WallpaperModel) =
        viewModel.viewModelScope.launch {
            if (viewModel.verifyWasFavorite(wallpaperModel.id)) {
                binding.buttonWallpaperFragment.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            }
        }
    private fun setupImageView(wallpaperModel: WallpaperModel) {
        context?.let {
            Glide.with(it).load(wallpaperModel.image).into(binding.imageViewWallpaperFragment)
        }
    }
    private fun setupFavoriteButton(wallpaperModel: WallpaperModel) {
        binding.buttonWallpaperFragment.setOnClickListener {
            viewModel.onSaveEventFavorite(
                id = wallpaperModel.id,
                name = wallpaperModel.name,
                image = wallpaperModel.image,
                category = wallpaperModel.category,
                context = requireContext()
            )
            binding.buttonWallpaperFragment.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
        }
    }

}
