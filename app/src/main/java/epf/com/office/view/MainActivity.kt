package epf.com.office.view

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import epf.com.office.Constant.Companion.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import epf.com.office.Constant.Companion.PERMISSIONS_REQUEST_ENABLE_GPS
import epf.com.office.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var mLocationPermissionGranted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false);

        val fragment: Fragment = BranchListFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, BranchListFragment::class.java.toString())
            .commitNow()

        if(isGPSEnabled()){
            if(!checkPermission()){
                getLocationPermission()
            }
        }else{
            buildAlertMessageNoGps()
        }
    }



    private fun isGPSEnabled(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false
        }

        return true
    }

    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                val enableGpsIntent =
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS)
            })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PERMISSIONS_REQUEST_ENABLE_GPS -> {
                if (!mLocationPermissionGranted) {
                    getLocationPermission()
                }
            }
        }

    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun getLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Log.i("lalalala","not granted")
                    getLocationPermission()
                }

            }
        }
    }

    fun getLocation(){
        val frag: BranchListFragment? = supportFragmentManager
            .findFragmentByTag(BranchListFragment::class.java.toString()) as BranchListFragment?
        frag!!.updateLocation()
    }

}
