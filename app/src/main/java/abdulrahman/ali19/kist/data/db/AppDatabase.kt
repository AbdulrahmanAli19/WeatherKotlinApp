package abdulrahman.ali19.kist.data.db

import abdulrahman.ali19.kist.data.db.dao.AlertDao
import abdulrahman.ali19.kist.data.db.dao.CashedDao
import abdulrahman.ali19.kist.data.db.dao.FavoriteDao
import abdulrahman.ali19.kist.data.pojo.model.dbentities.AlertEntity
import abdulrahman.ali19.kist.data.pojo.model.dbentities.CashedEntity
import abdulrahman.ali19.kist.data.pojo.model.dbentities.FavoriteEntity
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CashedEntity::class, FavoriteEntity::class, AlertEntity::class], version = 1)
@TypeConverters(RoomConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    abstract fun cashedDao(): CashedDao

    abstract fun alertDao(): AlertDao
}
