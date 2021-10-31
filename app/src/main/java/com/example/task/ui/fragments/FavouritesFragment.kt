package com.example.task.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.task.adapters.FavouriteDrinksAdapter
import com.example.task.databinding.FragmentFavouritesBinding
import com.example.task.models.Drink
import com.example.task.models.DrinkDbModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment(), FavouriteDrinksAdapter.OnItemClickListener {

    private val mViewModel: DrinksViewModel by viewModels()
    private lateinit var mBinding: FragmentFavouritesBinding
    private var mAdapter = FavouriteDrinksAdapter(this)
    private var mDrinks: ArrayList<DrinkDbModel> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFavouritesBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.getFavouriteDrink()
//        mAdapter = FavouriteDrinksAdapter()
        mBinding.rvRecipes.adapter = mAdapter

//        mDrinks.clear()
//        mDrinks.addAll(mViewModel.favouriteDrinks)
//        Log.e("mDrinks ","mDrinks ${mDrinks.size}")
//        mAdapter.submitList(mDrinks)

    }

    override fun onResume() {
        super.onResume()
        mDrinks.clear()
        mDrinks.addAll(mViewModel.getFavouriteDrink())
        mAdapter.submitList(mDrinks)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(drink: DrinkDbModel, isChekBox: Boolean, isDelete: String) {
//        TODO("Not yet implemented")

        if (isDelete.isEmpty()) {
            if (isChekBox)
                drink.strAlcoholic = "Alcoholic"
            else
                drink.strAlcoholic = "not"
            mViewModel.updateFavoriteDrink(drink)
            Toast.makeText(
                requireContext(),
                drink.idDrink.toString() + " Update",
                Toast.LENGTH_SHORT
            ).show()
            onResume()
        } else {
            mViewModel.deleteFavoriteDrink(drink)
            mDrinks.remove(drink)
            mAdapter.submitList(mDrinks)
            mAdapter.notifyDataSetChanged()
            Toast.makeText(
                requireContext(),
                drink.idDrink.toString() + " Delete",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}