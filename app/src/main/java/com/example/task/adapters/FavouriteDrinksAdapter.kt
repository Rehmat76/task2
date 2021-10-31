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
import com.example.task.models.DrinkDbModel

class FavouriteDrinksAdapter(private val listener: OnItemClickListener) :
    ListAdapter<DrinkDbModel, FavouriteDrinksAdapter.DrinksViewHolder>(DiffCallback()) {


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
            drink: DrinkDbModel
        ) {

            binding.tvDrinkTitle.text = drink.strDrink
            binding.tvDrinkDescription.text = drink.strInstructions
            if (drink.strAlcoholic == "Alcoholic")
                binding.chkAlcohol.isChecked = true


            binding.ivFavourite.setImageResource(R.drawable.ic_favourite_filled)

            Glide.with(binding.ivDrinkImage.context)
                .load(drink.strDrinkThumb)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivDrinkImage)

            binding.ivFavourite.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedDrink = getItem(position)
                    listener.onItemClick(selectedDrink, false, "delete")
                }
            }
            binding.chkAlcohol.setOnCheckedChangeListener{buttonView, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedDrink = getItem(position)
                    listener.onItemClick(selectedDrink, isChecked, "")
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(drink: DrinkDbModel, isChekBox: Boolean, isDelete : String)
    }


    class DiffCallback : DiffUtil.ItemCallback<DrinkDbModel>() {
        override fun areItemsTheSame(oldItem: DrinkDbModel, newItem: DrinkDbModel): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: DrinkDbModel, newItem: DrinkDbModel): Boolean {
            return oldItem == newItem
        }

    }


}