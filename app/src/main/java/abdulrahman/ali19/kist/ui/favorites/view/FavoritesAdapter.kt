package abdulrahman.ali19.kist.ui.favorites.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import abdulrahman.ali19.kist.databinding.FavoriteLayoutBinding
import abdulrahman.ali19.kist.data.pojo.model.dbentities.FavoriteEntity

class FavoritesAdapter(
    private val countries: ArrayList<FavoriteEntity> = arrayListOf(),
    private val listener: FavAdapterInterface
) :
    RecyclerView.Adapter<FavoritesAdapter.FavViewHolder>() {

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

    override fun getItemCount(): Int = if (countries.isEmpty()) 0 else countries.size

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