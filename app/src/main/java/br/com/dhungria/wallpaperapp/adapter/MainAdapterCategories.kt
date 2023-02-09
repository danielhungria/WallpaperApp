package br.com.dhungria.wallpaperapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.wallpaperapp.databinding.CardRecyclerCategoriesFragmentMainBinding
import br.com.dhungria.wallpaperapp.models.CategoryModel
import br.com.dhungria.wallpaperapp.models.WallpaperModel
import com.bumptech.glide.Glide

class MainAdapterCategories(
        val onClick: (CategoryModel) -> Unit
): ListAdapter<CategoryModel, MainAdapterCategories.ItemViewHolder>(
    DiffCallback()
) {
    private var fullList = mutableListOf<CategoryModel>()

    fun updateList(wallpaperList: List<CategoryModel>) {
        fullList = wallpaperList.toMutableList()
        submitList(fullList)
    }

    inner class  ItemViewHolder(private val binding: CardRecyclerCategoriesFragmentMainBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: CategoryModel, position: Int) = with(binding) {
            Glide.with(itemView.context).load(item.image).into(imageViewCategory)
            textviewCategoriesCardFragmentMain.text = item.name
            root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CardRecyclerCategoriesFragmentMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        return holder.bind(currentItem, position)
    }

    class DiffCallback : DiffUtil.ItemCallback<CategoryModel>() {
        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel) =
            oldItem == newItem
    }

}