package br.com.dhungria.wallpaperapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.dhungria.wallpaperapp.R
import br.com.dhungria.wallpaperapp.adapter.SearchAdapter
import br.com.dhungria.wallpaperapp.databinding.SearchFragmentBinding
import br.com.dhungria.wallpaperapp.viewmodel.SearchViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private lateinit var mAdView: AdView
    private val searchAdapter = SearchAdapter(onClick = {
        findNavController().navigate(
            R.id.action_search_fragment_to_fragment_wallpaper,
            bundleOf("IMAGE_TO_SHOW" to it)
        )
    })
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
        setupToolbarClick()
        setupBannerAd()
        viewModel.queryAllWallpaper()
        viewModel.wallpaperModel.observe(viewLifecycleOwner) {
            setupButtonDeleteTag()
            recoverySearch()
            searchAdapter.updateList(it)
        }

    }

    private fun setupBannerAd() {
        binding.adViewBannerSearchFragment.visibility = View.VISIBLE
        mAdView = binding.adViewBannerSearchFragment
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun setupButtonDeleteTag() {
        binding.buttonCardViewDeleteTagSearchFragment.setOnClickListener {
            viewModel.newText = ""
            viewModel.query = ""
            binding.cardViewTagSearchFragment.visibility = View.GONE
            viewModel.queryAllWallpaper()
        }
    }

    private fun recoverySearch() {
        if (viewModel.newText.isNotBlank()) {
            searchAdapter.filter.filter(viewModel.newText)
            binding.textViewCardViewTagSearchFragment.text = viewModel.newText
            binding.cardViewTagSearchFragment.visibility = View.VISIBLE
        } else if (viewModel.query.isNotBlank()) {
            searchAdapter.filter.filter(viewModel.query)
            binding.textViewCardViewTagSearchFragment.text = viewModel.query
            binding.cardViewTagSearchFragment.visibility = View.VISIBLE
        }else{
            binding.cardViewTagSearchFragment.visibility = View.GONE
        }
    }

    private fun setupToolbarClick() {
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
                viewModel.query = query.toString()
                searchAdapter.filter.filter(viewModel.query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.newText = newText.toString()
                searchAdapter.filter.filter(viewModel.newText)
                return true
            }
        })
        searchView.setOnCloseListener {
            searchAdapter.filter.filter("")
            true
        }
    }


}


