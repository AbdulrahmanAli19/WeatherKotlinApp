package abdulrahman.ali19.kist.data.pojo.model

import abdulrahman.ali19.kist.data.pojo.model.dbentities.FavoriteEntity
import abdulrahman.ali19.kist.ui.favorites.view.FavoritesAdapter

data class FavModel(
    val favAdapterInterface: FavoritesAdapter.FavAdapterInterface,
    val countries: List<FavoriteEntity>
)
