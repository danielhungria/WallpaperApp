package br.com.dhungria.wallpaperapp.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.dhungria.wallpaperapp.R
import br.com.dhungria.wallpaperapp.databinding.AdminScreenFragmentBinding
import br.com.dhungria.wallpaperapp.viewmodel.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminScreenFragment: Fragment() {

    private lateinit var binding: AdminScreenFragmentBinding
    private val viewModel: AdminViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AdminScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAddToFirebase.setOnClickListener {
            val name = binding.editTextInputEditTextName.text.toString()
            val image = binding.editTextInputEditTextImage.text.toString()
            val category = binding.editTextInputEditTextCategory.text.toString()
            val popularSwitch = binding.switchButtonPopular.isChecked
            val fileName = binding.editTextInputEditTextFileName.text.toString()
            viewModel.uploadWallpaper(
                name = name,
                image = image,
                category = category,
                popular = popularSwitch,
                fileName = fileName,
                context = context
            )
        }
    }

}