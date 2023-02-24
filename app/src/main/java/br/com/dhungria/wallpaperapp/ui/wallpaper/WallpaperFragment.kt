package br.com.dhungria.wallpaperapp.ui.wallpaper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import br.com.dhungria.wallpaperapp.R
import br.com.dhungria.wallpaperapp.databinding.WallpaperLayoutFragmentBinding
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import br.com.dhungria.wallpaperapp.viewmodel.WallpaperViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.Manifest
import android.app.WallpaperManager
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.drawToBitmap
import java.io.OutputStream
import java.lang.Exception
import java.util.Objects

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
        setPermission()
        setupNavigatePopBackStack()
        setupClickHideElements()
        setupButtonDownload()
        setupButtonExpanded()
        setupButtonSetWallpaper()
        checkStateButtons()
        wallpaperModel?.let { wallpaperModel ->
            setupImageView(wallpaperModel)
            setupFavoriteButton(wallpaperModel)
            setupFavoriteBackgroundButton(wallpaperModel)
        }
        viewModel.fetchWallpaper()

    }

    private fun setupButtonSetWallpaper() = with(binding) {
        buttonSetWallpaperBothScreenFragment.setOnClickListener {
            setWallpaper("Both")
        }
        buttonSetWallpaperHomeFragment.setOnClickListener {
            setWallpaper("Home")
        }
        buttonSetWallpaperLockFragment.setOnClickListener {
            setWallpaper("Lock")
        }
    }

    private fun setupButtonExpanded() {
        binding.buttonExpandedWallpaperFragment.setOnClickListener {
            viewModel.buttonExpanded = !viewModel.buttonExpanded
            checkStateButtons()
        }
    }

    private fun checkStateButtons() = with(binding) {
        if (viewModel.buttonExpanded) {
            buttonDownloadWallpaperFragment.visibility = View.VISIBLE
            buttonSetWallpaperHomeFragment.visibility = View.VISIBLE
            buttonSetWallpaperLockFragment.visibility = View.VISIBLE
            buttonSetWallpaperBothScreenFragment.visibility = View.VISIBLE
        } else {
            buttonDownloadWallpaperFragment.visibility = View.INVISIBLE
            buttonSetWallpaperHomeFragment.visibility = View.INVISIBLE
            buttonSetWallpaperLockFragment.visibility = View.INVISIBLE
            buttonSetWallpaperBothScreenFragment.visibility = View.INVISIBLE
        }
    }

    private fun setupButtonDownload() {
        binding.buttonDownloadWallpaperFragment.setOnClickListener {
            saveImage()
        }
    }

    private fun setWallpaper(screen: String) {
        val bitmap = binding.imageViewWallpaperFragment.drawToBitmap()
        val wallpaperManager = WallpaperManager.getInstance(context)
        when(screen){
            "Home" -> { wallpaperManager.setBitmap(bitmap) }
            "Both" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val flagLock = WallpaperManager.FLAG_LOCK
                    wallpaperManager.setBitmap(bitmap)
                    wallpaperManager.setBitmap(bitmap, null, true, flagLock)
                }
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val flagLock = WallpaperManager.FLAG_LOCK
                    wallpaperManager.setBitmap(bitmap, null, true, flagLock)
                }
            }
        }
    }

    private fun setupClickHideElements(){
        checkState()
        binding.imageViewWallpaperFragment.setOnClickListener {
            viewModel.hideElements = !viewModel.hideElements
            viewModel.buttonExpanded = false
            checkState()
        }
    }

    private fun checkState() = with(binding) {
        when (viewModel.hideElements) {
            true -> {
                buttonWallpaperFragment.visibility = View.GONE
                buttonDownloadWallpaperFragment.visibility = View.INVISIBLE
                buttonSetWallpaperBothScreenFragment.visibility = View.INVISIBLE
                buttonSetWallpaperHomeFragment.visibility = View.INVISIBLE
                buttonSetWallpaperLockFragment.visibility = View.INVISIBLE
                buttonExpandedWallpaperFragment.visibility = View.GONE
                toolbarWallpaperFragment.visibility = View.GONE
            }
            else -> {
                buttonWallpaperFragment.visibility = View.VISIBLE
                buttonExpandedWallpaperFragment.visibility = View.VISIBLE
                toolbarWallpaperFragment.visibility = View.VISIBLE
            }
        }
    }

    //passar funcao para o viewmodel e rodar em background (dispatchers.io)
    private fun saveImage() {
        val bitmap = binding.imageViewWallpaperFragment.drawToBitmap()
        val outputStream: OutputStream
        try {
            val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            }else{
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }
            val contentValues = ContentValues()
            val resolver = context?.contentResolver
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + ".jpg")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/*")
            val imageUri = resolver?.insert(uri, contentValues)
            outputStream = resolver?.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            Objects.requireNonNull(outputStream)
            Toast.makeText(context, "Imagem salva com sucesso", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("error", "onViewCreated: $e")
            Toast.makeText(context, "Erro ao salvar imagem", Toast.LENGTH_LONG).show()
        }
    }

    private fun setPermission() {
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (context?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                activity?.let { ActivityCompat.requestPermissions(it, permissions, 100) }
            }
        }
    }

    private fun setupNavigatePopBackStack() {
        binding.toolbarWallpaperFragment.setNavigationOnClickListener {
            findNavController().popBackStack()
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
            viewModel.viewModelScope.launch {
                if (viewModel.verifyWasFavorite(wallpaperModel.id)) {
                    binding.buttonWallpaperFragment.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                    viewModel.removeFavorite(wallpaperModel.id,requireContext())
                } else {
                    binding.buttonWallpaperFragment.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                    viewModel.onSaveEventFavorite(
                        id = wallpaperModel.id,
                        name = wallpaperModel.name,
                        image = wallpaperModel.image,
                        category = wallpaperModel.category,
                        context = requireContext()
                    )
                }
            }
        }
    }

}
