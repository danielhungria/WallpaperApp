package br.com.dhungria.wallpaperapp.ui.wallpaper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.dhungria.wallpaperapp.databinding.WallpaperLayoutFragmentBinding
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WallpaperFragment: Fragment() {
    private lateinit var binding: WallpaperLayoutFragmentBinding
    private val image by lazy { arguments?.getParcelable<WallpaperModel>("IMAGE_TO_SHOW") }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(context).load(image?.image).into(binding.imageViewWallpaperFragment)
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
