package br.com.dhungria.wallpaperapp.ui.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.dhungria.wallpaperapp.R
import br.com.dhungria.wallpaperapp.adapter.WallpaperCategoriesAdapter
import br.com.dhungria.wallpaperapp.databinding.FragmentCategoryBinding
import br.com.dhungria.wallpaperapp.models.CategoryModel
import br.com.dhungria.wallpaperapp.viewmodel.WallpaperCategoryViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WallpaperCategoryFragment : Fragment() {

    private val viewModel: WallpaperCategoryViewModel by viewModels()
    private lateinit var mAdView: AdView
    private var mInterstitialAd: InterstitialAd? = null
    private val wallpaperCategoryAdapter = WallpaperCategoriesAdapter(onClick = {
        findNavController().navigate(
            R.id.action_wallpaper_category_to_fragment_wallpaper_category,
            bundleOf("IMAGE_TO_SHOW" to it)
        )
    }

    )
    private val category by lazy { arguments?.getParcelable<CategoryModel>("CATEGORY_TO_SHOW") }
    private lateinit var binding: FragmentCategoryBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewCategory()
        setupToolbarTitle()
        setupBannerAd()
        setupAdInterstitial()
        viewModel.adLoad++
        viewModel.value = category?.name ?: ""
        viewModel.getWallpaperListFiltered().observe(viewLifecycleOwner) {
            wallpaperCategoryAdapter.updateList(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupBannerAd() {
        binding.adViewBannerCategoryFragment.visibility = View.VISIBLE
        mAdView = binding.adViewBannerCategoryFragment
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun setupAdInterstitial() {
        if (viewModel.adLoad>=2){
            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(
                requireContext(),
                getString(R.string.ad_interstitial2),
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        adError.toString().let { Log.d("Fragment", it) }
                        mInterstitialAd = null
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        Log.d("Fragment", "Ad was loaded.")
                        mInterstitialAd = interstitialAd
                        mInterstitialAd?.show(requireActivity())
                        viewModel.adLoad=0
                    }
                }
            )
        }

    }

    private fun setupRecyclerViewCategory() {
        binding.recyclerviewCategoriesFragmentCategory.apply {
            adapter = wallpaperCategoryAdapter
            val mLayoutManager = GridLayoutManager(requireContext(), 3)
            layoutManager = mLayoutManager
        }
    }

    private fun setupToolbarTitle() = with(binding.toolbarCategoryFragment) {
        title = category?.name ?: context.getString(R.string.category)
        setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}
