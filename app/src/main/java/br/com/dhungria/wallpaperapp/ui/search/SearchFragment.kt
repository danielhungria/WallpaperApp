package br.com.dhungria.wallpaperapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.dhungria.wallpaperapp.R
import br.com.dhungria.wallpaperapp.adapter.SearchAdapter
import br.com.dhungria.wallpaperapp.databinding.SearchFragmentBinding
import br.com.dhungria.wallpaperapp.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private val searchAdapter = SearchAdapter()
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigatePopBackStack()
        setupRecyclerSearch()
        viewModel.queryAllWallpaper()
        viewModel.wallpaperModel.observe(viewLifecycleOwner){
            searchAdapter.updateList(it)
        }
        binding.toolbarSearchFragment.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_search -> {
                    val search = it.actionView as SearchView
                    setupSearchView(search)
                    true
                }

                else -> false
            }
        }
    }

    private fun setupNavigatePopBackStack() {
        binding.toolbarSearchFragment.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerSearch() {
        binding.recyclerviewSearchFragment.apply {
            adapter = searchAdapter
            val mLayoutManager = GridLayoutManager(requireContext(), 3)
            layoutManager = mLayoutManager
        }
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchAdapter.filter.filter(newText)
                return false
            }
        })
    }

}
