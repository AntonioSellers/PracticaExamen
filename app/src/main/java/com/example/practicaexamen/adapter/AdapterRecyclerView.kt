package com.example.practicaexamen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.practicaexamen.R
import com.example.practicaexamen.component.Recipe
import com.example.practicaexamen.databinding.ItemFragmentBinding

class AdapterRecyclerView(val recipeHistory: MutableList<Recipe>?): RecyclerView.Adapter<AdapterRecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)= holder.bind(recipeHistory!![position])

    override fun getItemCount(): Int = recipeHistory!!.size

    inner class ViewHolder(historyItem: View) : RecyclerView.ViewHolder(historyItem) {

        private val binding = ItemFragmentBinding.bind(historyItem)

        fun bind(recipe : Recipe) {
            binding.imgFr.setImageDrawable(recipe.photo)
            binding.ingredientNameFr.text = recipe.ingredient
            binding.recipeNameFr.text = recipe.recipe
        }
    }

}