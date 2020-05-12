package epf.com.office.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import epf.com.office.data.dao.EpfOfficeDao
import epf.com.office.model.EpfOffice

@Database(entities = [EpfOffice::class],version = 1,exportSchema = false)
abstract class EpfDatabase :RoomDatabase(){


    abstract fun epfOfficeDao():EpfOfficeDao

    companion object{

        @Volatile
        private var INSTANCE: EpfDatabase?=null

        fun getDatabase(context:Context): EpfDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EpfDatabase::class.java,
                    "epf_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}