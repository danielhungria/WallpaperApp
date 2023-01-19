package br.com.dhungria.wallpaperapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.dhungria.wallpaperapp.databinding.FragmentMainBinding
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentMainBinding

    private val mainAdapterPopular = MainAdapterPopular()

    private val mainAdapterCategories = MainAdapterCategories()

    var newItem: List<WallpaperModel> = emptyList()


    private fun setupRecyclerViewPopular() {
        binding.recyclerviewPopularFragmentMain.apply {
            adapter = mainAdapterPopular.apply {
               submitList(List(10){
                   arrayListOf("teste").toString()
               })
            }
            val mLayoutManager = LinearLayoutManager(requireContext())
                mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = mLayoutManager
        }
    }

    private fun setupRecyclerViewCategories() {
        binding.recyclerviewCategoriesFragmentMain.apply {
            adapter = mainAdapterCategories.apply {
                submitList(List(12){
                    arrayListOf("teste").toString()
                })
            }
            val mLayoutManager = GridLayoutManager(requireContext(), 2)
            layoutManager = mLayoutManager

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewPopular()
        setupRecyclerViewCategories()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }
}
