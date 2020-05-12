package epf.com.office.view

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import epf.com.office.Constant.Companion.MAP_VIEW_BUNDLE_KEY
import epf.com.office.Constant.Companion.OFFICE_ID
import epf.com.office.R
import epf.com.office.service.DistanceCalculator
import epf.com.office.utils.Utils
import epf.com.office.viewmodel.BranchDetailViewModel
import kotlinx.android.synthetic.main.activity_branch_detail.*

class BranchDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: MapView
    private lateinit var gMap: GoogleMap
    private lateinit var viewModel: BranchDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch_detail)

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        mMap= findViewById(R.id.mapView)
        mMap.onCreate(mapViewBundle);
        mMap.getMapAsync(this);

        viewModel = ViewModelProviders.of(this).get(BranchDetailViewModel::class.java)
        viewModel.getOfficeDetail(intent.getIntExtra(OFFICE_ID,-1)).observe(this, Observer {
            name_tv.text = "EPF ${it.nam} Office"
            address_tv.text = it.ads
            faxnumber_tv.text = it.fax
            distance_tv.text ="${Utils().roundOffDecimal(it.dist)} km"
            gMap.moveCamera(CameraUpdateFactory.newLatLng( LatLng(it.lat, it.lon)))
            gMap.addMarker(MarkerOptions().position(LatLng(it.lat, it.lon)).title("EPF ${it.nam} Office"))


        })


    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        gMap.setMinZoomPreference(12f)
        gMap.isMyLocationEnabled=true
    }

    override fun onResume() {
        super.onResume()
        mMap.onResume()
    }

    override fun onStart() {
        super.onStart()
        mMap.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMap.onStop()
    }

    override fun onPause() {
        mMap.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mMap.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMap.onLowMemory()
    }
}
