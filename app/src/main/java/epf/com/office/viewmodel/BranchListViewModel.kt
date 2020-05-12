package epf.com.office.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.arch.core.util.Function
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.sqlite.db.SimpleSQLiteQuery
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import epf.com.office.Constant.Companion.SORT_BY_NAME
import epf.com.office.Constant.Companion.STATE_CODE_JSON_NAME
import epf.com.office.model.EpfOffice
import epf.com.office.model.StateCode
import epf.com.office.repository.EpfOfficeRepository
import epf.com.office.service.DistanceCalculator
import epf.com.office.service.LocationService
import epf.com.office.utils.AppPreferences
import epf.com.office.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_branch_detail.*

class BranchListViewModel(application: Application) : AndroidViewModel(application) {
    private var epfOfficeRepository: EpfOfficeRepository = EpfOfficeRepository(application)

    var queryName: String = ""
    private val query = MutableLiveData<String>()
    init {
        updateData();
    }

    @SuppressLint("CheckResult")
    fun getBranchFromApi() {
        if (!AppPreferences.isDataFecthFromApi) {
            epfOfficeRepository.requestEpfOfficeFromApi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ its ->
                    AppPreferences.isDataFecthFromApi = true
                    epfOfficeRepository.insertEpfOffice(its.lis)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                        }, {
                        })

                }, {

                })
        }


    }

    @SuppressLint("CheckResult")
    fun updateDisntace(latitude: Double, longitude: Double) {
        epfOfficeRepository.updateDistance(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {})
    }

    fun getBranch() = Transformations.switchMap(query, ::getData)

    fun updateData() {
        var q =
            "SELECT * FROM epfoffice_table where( nam LIKE '%${queryName}%' or ads LIKE '%${queryName}%' )"

        if(!AppPreferences.filterBy.equals("00") && !AppPreferences.filterBy.equals("") ){
            q += " and ste = '${AppPreferences.filterBy}'"
        }
        if (AppPreferences.sortBy!!.equals(SORT_BY_NAME)) {
            q += " order by nam ASC"
        } else {
            q += " order by dist ASC"
        }
        query.value = q
    }


    private fun getData(query: String) = epfOfficeRepository.getData(query)

    fun getCurrentLocation(context: Context){
        LocationService.getCurrentLocation(context).onLocationUpdate ={gpsPoint ->
            updateDisntace(gpsPoint.latitude, gpsPoint.longitude)
        }
    }


}
