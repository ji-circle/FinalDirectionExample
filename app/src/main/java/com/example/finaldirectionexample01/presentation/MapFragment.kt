package com.example.finaldirectionexample01.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.finaldirectionexample01.FinalDirectionApplication
import com.example.finaldirectionexample01.R
import com.example.finaldirectionexample01.data.AppContainer
import com.example.finaldirectionexample01.databinding.FragmentDirectionsBinding
import com.example.finaldirectionexample01.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var googleMap: GoogleMap? = null


    private lateinit var locationPermission: ActivityResultLauncher<Array<String>>
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val appContainer: AppContainer by lazy {
        (requireActivity().application as FinalDirectionApplication).appContainer
    }

    // DirectionsViewModel1Factory 가져오기
    private val directionsViewModel1Factory: DirectionsViewModel1Factory by lazy {
        appContainer.directions1Container?.directionsViewModel1Factory
            ?: throw IllegalStateException("DirectionsViewModel1Factory not initialized properly")
    }

    // SharedViewModel 가져오기
    private val sharedViewModel: DirectionsViewModel1 by activityViewModels { directionsViewModel1Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Register the permissions result callback
        locationPermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { results ->
            if (results.all { it.value }) {
                initializeMapView()
            } else {
                Toast.makeText(requireContext(), "권한 승인이 필요합니다.", Toast.LENGTH_LONG).show()
            }
        }

        // Request the permissions
        requestLocationPermission()

    }

    private fun requestLocationPermission() {
        locationPermission.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMapBottomSheet.setOnClickListener {
            val bottomSheetDialogFragment = RouteDetailsBottomSheet()
            bottomSheetDialogFragment.show(parentFragmentManager, "tag")
        }
        binding.line.setOnClickListener {
            // 경로 유무
        }

        binding.btnZoomIn.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomIn())
        }
        binding.btnZoomOut.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomOut())
        }

        sharedViewModel.directionsResult.observe(viewLifecycleOwner, { directions ->
            directions?.let {
                //drawRouteOnMap(it)
            }
        })

        initializeMapView()


    }

    private fun initializeMapView() {

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment?.getMapAsync { map ->
            googleMap = map
            // Optionally enable the user location
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                googleMap?.isMyLocationEnabled = true

                val polylinePoints = sharedViewModel.polyLine.value

                if (polylinePoints != null) {
                    googleMap?.addPolyline(polylinePoints)
                }


                // 경로를 포함하는 영역 계산하여 지도의 중심을 이동
                val latLngBounds = LatLngBounds.builder()
                sharedViewModel.latLngBounds.value?.forEach {
                    latLngBounds.include(
                        LatLng(
                            it.latitude, it.longitude
                        )
                    )
                }
                val bounds = latLngBounds.build()
                val padding = 100
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
            }


        }
    }


//    private fun focusOnPolyline(polylinePoints: List<LatLng>) {
//        val builder = LatLngBounds.builder()
//        polylinePoints.forEach { point ->
//            val latLng = LatLng(point.lat, point.lng)
//            builder.include(latLng)
//        }
//        val bounds = builder.build()
//        val padding = 100
//        googleMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
//    }


    override fun onResume() {
        super.onResume()
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment?.onResume()
    }

    override fun onPause() {
        super.onPause()
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment?.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment?.onDestroyView()
        _binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment?.onLowMemory()
    }

    override fun onMapReady(p0: GoogleMap) {
        val markerOrigin = MarkerOptions().position(sharedViewModel.getOrigin())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        //updateLocation()
    }

//    private fun updateLocation() {
//        val locationRequest = LocationRequest.create().apply {
//            interval = 1000
//            fastestInterval = 500
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        }
//
////        locationCallback = object : LocationCallback() {
////            //1초에 한번씩 변경된 위치 정보가 onLocationResult 으로 전달된다.
////            override fun onLocationResult(locationResult: LocationResult) {
////                locationResult?.let {
////                    for (location in it.locations) {
////                        Log.d("위치정보", "위도: ${location.latitude} 경도: ${location.longitude}")
////                        setLastLocation(location) //계속 실시간으로 위치를 받아오고 있기 때문에 맵을 확대해도 다시 줄어든다.
////                    }
////                }
////            }
////        }
//        //권한 처리
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//
//        fusedLocationClient.requestLocationUpdates(
//            locationRequest, locationCallback,
//            Looper.myLooper()!!
//        )
//    }

//    fun setLastLocation(lastLocation: Location) {
//        val LATLNG = LatLng(lastLocation.latitude, lastLocation.longitude)
//
//        val makerOptions = MarkerOptions().position(LATLNG).title("나 여기 있어용~")
//        val cameraPosition = CameraPosition.Builder().target(LATLNG).zoom(15.0f).build()
//
//        googleMap?.addMarker(makerOptions)
//        googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
//    }


}


