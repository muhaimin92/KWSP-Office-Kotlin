package epf.com.office.service

import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*
import epf.com.office.model.EpfOffice
import epf.com.office.model.GPSPoint


object LocationService {

    private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
    private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var locationCallback: LocationCallback? = null
    private var locationRequest: LocationRequest? = null


    var onLocationUpdate: ((gpsPoint: GPSPoint) -> Unit)? = null

    fun getCurrentLocation(context: Context):LocationService {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        locationRequest = LocationRequest()
        locationRequest!!.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest!!.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    onLocationUpdate?.invoke(GPSPoint(location.latitude, location.longitude))
                }


            }
        }

        mFusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback, Looper.myLooper()
        );

        return this

    }


}