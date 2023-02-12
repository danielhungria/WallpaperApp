package br.com.dhungria.wallpaperapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.wallpaperapp.databinding.CardRecyclerFragmentCategoryBinding
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import com.bumptech.glide.Glide

class WallpaperCategoriesAdapter(
    val onClick: (WallpaperModel) -> Unit
): ListAdapter<WallpaperModel, WallpaperCategoriesAdapter.ItemViewHolder>(DiffCallback()) {

    private var fullList = mutableListOf<WallpaperModel>()

    fun updateList(wallpaperList: List<WallpaperModel>) {
        fullList = wallpaperList.toMutableList()
        submitList(fullList)
    }

    inner class  ItemViewHolder(private val binding: CardRecyclerFragmentCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: WallpaperModel) = with(binding){
            Glide.with(itemView.context).load(item.image).into(imageview)
            root.setOnClickListener {
                onClick(item)
            }
        }
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