package epf.com.office.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "epfoffice_table")
data class EpfOffice(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")

    var id: Int,
    var ads: String,
    var efd: String,
    var ste: String,
    var lon: Double,
    var nam: String,
    var fax: String,
    var key: String,
    var lat: Double,

    var dist: Double=0.0

)
