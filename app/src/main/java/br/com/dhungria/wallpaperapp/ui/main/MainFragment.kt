package br.com.dhungria.wallpaperapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.dhungria.wallpaperapp.R
import br.com.dhungria.wallpaperapp.adapter.MainAdapterCategories
import br.com.dhungria.wallpaperapp.adapter.MainAdapterPopular
import br.com.dhungria.wallpaperapp.databinding.FragmentMainBinding
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import br.com.dhungria.wallpaperapp.viewmodel.MainViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.interstitial.InterstitialAd
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    private lateinit var mAdView: AdView
    private var mInterstitialAd: InterstitialAd? = null
    private val mainAdapterPopular = MainAdapterPopular(onClick = {
        findNavController().navigate(R.id.action_fragment_main_to_wallpaper_fragment, bundleOf("IMAGE_TO_SHOW" to it))
    })
    private val mainAdapterCategories = MainAdapterCategories(onClick = {
        findNavController().navigate(R.id.action_fragment_main_category_to_fragment_wallpaper_category, bundleOf("CATEGORY_TO_SHOW" to it))
    })


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewPopular()
        setupRecyclerViewCategories()
        setupSwipeRefresh()
        viewModel.getWallpaperDataFiltered()
        updateWallpaperObserve()
        updateCategoryObserve()
        setupBannerAd()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun setupBannerAd() {
        binding.adViewBannerMainFragment.visibility = View.VISIBLE
        mAdView = binding.adViewBannerMainFragment
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun setupRecyclerViewPopular() {
        binding.recyclerviewPopularFragmentMain.apply {
            adapter = mainAdapterPopular
            val mLayoutManager = LinearLayoutManager(requireContext())
                mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = mLayoutManager
        }
    }
    private fun setupRecyclerViewCategories() {
        binding.recyclerviewCategoriesFragmentMain.apply {
            adapter = mainAdapterCategories
            val mLayoutManager = GridLayoutManager(requireContext(), 3)
            mLayoutManager.orientation = GridLayoutManager.HORIZONTAL
            layoutManager = mLayoutManager

        }
    }

    private fun setupSwipeRefresh() = with(binding) {
        mainFragmentSwipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.getWallpaperDataFiltered()
                viewModel.loadCategoryData()
                mainFragmentSwipeRefresh.isRefreshing = false

            }
        }
    }

    private fun updateCategoryObserve() {
        viewModel.getCategoryList().observe(viewLifecycleOwner) {
            mainAdapterCategories.updateList(it)
        }
    }

    private fun updateWallpaperObserve() {
        viewModel.wallpaperModel.observe(viewLifecycleOwner, Observer {
            mainAdapterPopular.updateList(it)
        })
    }
}
