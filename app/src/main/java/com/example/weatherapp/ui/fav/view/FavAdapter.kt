package com.example.weatherapp.ui.fav.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.FavoriteLayoutBinding
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity

class FavAdapter(
    private val countries: ArrayList<FavoriteEntity> = arrayListOf(),
    private val listener: FavAdapterInterface
) :
    RecyclerView.Adapter<FavAdapter.FavViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setCountries(newList: ArrayList<FavoriteEntity>) {
        countries.clear()
        countries.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder =
        FavViewHolder(
            FavoriteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) =
        holder.bind(countries[position].locationName)

    override fun getItemCount(): Int = if (countries.isNullOrEmpty()) 0 else countries.size

    inner class FavViewHolder(val binding: FavoriteLayoutBinding, listener: FavAdapterInterface) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imgDelete.setOnClickListener { listener.onDeleteImageClick(adapterPosition) }
            binding.parent.setOnClickListener { listener.onItemClick(adapterPosition) }
        }

        fun bind(country: String) {
            binding.data = country
            binding.executePendingBindings()
        }
    }

    interface FavAdapterInterface {
        fun onDeleteImageClick(pos: Int)
        fun onItemClick(pos: Int)
    }
}