package epf.com.office.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import epf.com.office.model.EpfOffice

@Dao
interface EpfOfficeDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(vararg epfOffice: EpfOffice)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(epfOffice: EpfOffice)

    @Query("SELECT * FROM epfoffice_table ")
    fun getOfficeList(): LiveData<List<EpfOffice>>

    @Query("SELECT * FROM epfoffice_table ")
    fun getOfficeList2(): List<EpfOffice>

    @RawQuery(observedEntities = [EpfOffice::class])
    fun getByQuery(query: SupportSQLiteQuery?): LiveData<List<EpfOffice>>

    @Query("SELECT * FROM epfoffice_table where id=:id")
    fun getById(id:Int): LiveData<EpfOffice>

    @Update
    fun update(epfOffice: EpfOffice)

}