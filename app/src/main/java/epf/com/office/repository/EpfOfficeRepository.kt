package epf.com.office.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import epf.com.office.data.database.EpfDatabase
import epf.com.office.model.EpfOffice
import epf.com.office.model.EpfOfficeRequest
import epf.com.office.service.DistanceCalculator
import epf.com.office.service.EpfApiService
import io.reactivex.Completable
import io.reactivex.Single

class EpfOfficeRepository(application: Application) {

    private val epfApiService by lazy {
        EpfApiService.create()
    }

    private val epfOfficeDao by lazy {
        EpfDatabase.getDatabase(application).epfOfficeDao()
    }

    fun requestEpfOfficeFromApi() =
        epfApiService.requestOfficeLoacation(EpfOfficeRequest(3, "EN", "0"))

    fun insertEpfOffice(list: List<EpfOffice>) = Completable.create {
        for (epf in list) {
            epfOfficeDao.insert(epf)
        }
        it.onComplete()
    }

    fun getData(query:String) = epfOfficeDao.getByQuery(SimpleSQLiteQuery(query))

    fun updateDistance(latitude: Double,longitude: Double) = Completable.create {
        var list =epfOfficeDao.getOfficeList2()
        list.forEach {
                epfOffice: EpfOffice ->  epfOffice.dist= DistanceCalculator().distance(latitude, longitude, epfOffice.lat, epfOffice.lon, "K")
                epfOfficeDao.update(epfOffice)
        }

        it.onComplete()
    }


    fun getDataById(id: Int): LiveData<EpfOffice> = epfOfficeDao.getById(id)


}