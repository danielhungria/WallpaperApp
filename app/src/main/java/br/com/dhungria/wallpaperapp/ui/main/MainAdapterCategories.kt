package br.com.dhungria.wallpaperapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.wallpaperapp.databinding.CardRecyclerCategoriesFragmentMainBinding

class MainAdapterCategories(): ListAdapter<String, MainAdapterCategories.ItemViewHolder>(DiffCallback()) {

    inner class  ItemViewHolder(private val binding: CardRecyclerCategoriesFragmentMainBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: String, position: Int) {
            binding.textviewCategoriesCardFragmentMain.text = "teste categoria $position"
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

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }

}