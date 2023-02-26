package br.com.dhungria.wallpaperapp.ui.support

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.dhungria.wallpaperapp.databinding.SupportFragmentBinding
import br.com.dhungria.wallpaperapp.viewmodel.SupportViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SupportFragment: Fragment() {

    private lateinit var binding: SupportFragmentBinding
    private val viewModel: SupportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SupportFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSendSupportFragment.setOnClickListener {
            val title = binding.textInputEditTitleSupportFragment.text.toString()
            val text = binding.textInputEditTextSupportFragment.text.toString()
            viewModel.uploadSupport(
                title = title,
                text = text,
                context = context
            )
        }
    }

}