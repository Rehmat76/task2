package com.example.task.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.task.R
import com.example.task.databinding.ListItemRecipeBinding
import com.example.task.models.Drink

class DrinksAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Drink, DrinksAdapter.DrinksViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder {
        val binding =
            ListItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrinksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {
        val item = getItem(position)
        item.let { holder.bind(it) }
    }

    inner class DrinksViewHolder(private val binding: ListItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            drink: Drink
        ) {

            binding.tvDrinkTitle.text = drink.strDrink
            binding.tvDrinkDescription.text = drink.strInstructions
            if (drink.strAlcoholic == "Alcoholic")
                binding.chkAlcohol.isChecked = true

            if (mdrink.favourite) {
                binding.ivFavourite.setImageResource(R.drawable.ic_favourite_filled)
            } else {
                binding.ivFavourite.setImageResource(R.drawable.ic_star_black)
            }

            binding.ivFavourite.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedDrink = getItem(position)
                    listener.onItemClick(selectedDrink)
                }
            }

            Glide.with(binding.ivDrinkImage.context)
                .load(drink.strDrinkThumb)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivDrinkImage)

        }

    }


    interface OnItemClickListener {
        fun onItemClick(drink: Drink)
    }


    class DiffCallback : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem == newItem
        }

    }


}