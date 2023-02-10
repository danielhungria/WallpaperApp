package br.com.dhungria.wallpaperapp.ui.wallpaper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.dhungria.wallpaperapp.databinding.WallpaperLayoutFragmentBinding
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import br.com.dhungria.wallpaperapp.viewmodel.MainViewModel
import br.com.dhungria.wallpaperapp.viewmodel.WallpaperViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WallpaperFragment: Fragment() {
    private lateinit var binding: WallpaperLayoutFragmentBinding
    private val wallpaperModel by lazy { arguments?.getParcelable<WallpaperModel>("IMAGE_TO_SHOW") }
    private val viewModel: WallpaperViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(context).load(wallpaperModel?.image).into(binding.imageViewWallpaperFragment)
        binding.buttonWallpaperFragment.setOnClickListener {
            wallpaperModel?.let {
                viewModel.onSaveEventFavorite(
                    name = it.name,
                    image = it.image,
                    category = it.category
                )
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WallpaperLayoutFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
