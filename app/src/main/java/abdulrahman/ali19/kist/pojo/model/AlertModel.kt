package abdulrahman.ali19.kist.pojo.model

import abdulrahman.ali19.kist.pojo.model.dbentities.AlertEntity
import abdulrahman.ali19.kist.ui.alert.view.AlertAdapter

data class AlertModel(
    var list: ArrayList<AlertEntity>?,
    var listener : AlertAdapter.AlertAdapterListener
    )
