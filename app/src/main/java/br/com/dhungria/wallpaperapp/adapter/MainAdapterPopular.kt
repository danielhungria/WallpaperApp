package br.com.dhungria.wallpaperapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.wallpaperapp.databinding.CardRecyclerFragmentMainBinding
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import com.bumptech.glide.Glide

class MainAdapterPopular(
    val onClick: (WallpaperModel) -> Unit
): ListAdapter<WallpaperModel, MainAdapterPopular.ItemViewHolder>(DiffCallback()) {

    private var fullList = mutableListOf<WallpaperModel>()

    fun updateList(wallpaperList: List<WallpaperModel>) {
        fullList = wallpaperList.toMutableList()
        submitList(fullList)
    }

    inner class  ItemViewHolder(private val binding: CardRecyclerFragmentMainBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: WallpaperModel) = with(binding){
            Glide.with(itemView.context).load(item.image).into(imageviewPopular)
            root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CardRecyclerFragmentMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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