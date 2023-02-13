package br.com.dhungria.wallpaperapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.wallpaperapp.databinding.CardRecyclerFragmentCategoryBinding
import br.com.dhungria.wallpaperapp.databinding.CardRecyclerFragmentMainBinding
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import com.bumptech.glide.Glide

class SearchAdapter(
//    val onClick: (WallpaperModel) -> Unit
): ListAdapter<WallpaperModel, SearchAdapter.ItemViewHolder>(DiffCallback()), Filterable {

    private var fullList = mutableListOf<WallpaperModel>()

    private val customFilter = object : Filter() {
        override fun performFiltering(query: CharSequence?): FilterResults {
            val filteredList = mutableListOf<WallpaperModel>()

            if (query == null || query.toString().isEmpty()) {
                filteredList.addAll(fullList)
            }else {
                filteredList.addAll(filterListBy(query))
            }

            return FilterResults().apply {
                values = filteredList
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            results?.values?.let { submitList(it as List<WallpaperModel>) }
        }

    }

    private fun filterListBy(query: CharSequence) = fullList.filter {
        it.name.lowercase().trim().contains(query.toString().lowercase().trim())
    }

    fun updateList(wallpaperList: List<WallpaperModel>) {
        fullList = wallpaperList.toMutableList()
        submitList(fullList)
    }

    inner class  ItemViewHolder(private val binding: CardRecyclerFragmentCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: WallpaperModel) = with(binding){
            Glide.with(itemView.context).load(item.image).into(imageview)
            root.setOnClickListener {
//                onClick(item)
            }
        }
    }

    override fun getFilter(): Filter {
        return customFilter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CardRecyclerFragmentCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        return holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<WallpaperModel>() {
        override fun areItemsTheSame(oldItem: WallpaperModel, newItem: WallpaperModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: WallpaperModel, newItem: WallpaperModel) =
            oldItem == newItem
    }




}