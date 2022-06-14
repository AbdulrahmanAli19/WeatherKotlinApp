package abdulrahman.ali19.kist.pojo.model

import abdulrahman.ali19.kist.pojo.model.dbentities.FavoriteEntity
import abdulrahman.ali19.kist.ui.fav.view.FavAdapter

data class FavModel(
    val favAdapterInterface: FavAdapter.FavAdapterInterface,
    val countries: List<FavoriteEntity>
)
