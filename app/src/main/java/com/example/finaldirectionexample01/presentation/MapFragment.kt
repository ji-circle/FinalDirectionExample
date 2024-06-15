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
import androidx.lifecycle.Observer
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
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

class MapFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var googleMap: GoogleMap? = null

    private var isUserInteracting = false
    private var shouldFocusMapOnBounds = true

    private var polylinePl: Polyline? = null


    private lateinit var locationPermission: ActivityResultLauncher<Array<String>>
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val appContainer: AppContainer by lazy {
        (requireActivity().application as FinalDirectionApplication).appContainer
    }

    // DirectionsViewModel1Factory 가져오기
    private val directionsViewModel1Factory: DirectionsViewModel1Factory by lazy {
        appContainer.directions1Container.directionsViewModel1Factory
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

        initializeMapView()

        setupClickListener()


//        googleMap?.setOnCameraIdleListener {
//            // 경로를 포함하는 영역 계산하여 지도의 중심을 이동
//            val latLngBoundsBuilder = LatLngBounds.builder()
//            sharedViewModel.latLngBounds.value?.forEach {
//                latLngBoundsBuilder.include(LatLng(it.lat, it.lng))
//            }
//            val bounds = latLngBoundsBuilder.build()
//            val padding = 100
//
//            // 현재 지도에 보이는 bounds와 새로 계산된 bounds가 다를 때에만 지도 이동
//            val currentBounds = googleMap!!.projection.visibleRegion.latLngBounds
//            if (!currentBounds.contains(bounds.center)) {
//                googleMap!!.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
//            }
//        }


    }

    private fun setupClickListener() {
        binding.btnMapBottomSheet.setOnClickListener {
            val bottomSheetDialogFragment = RouteDetailsBottomSheet()
            bottomSheetDialogFragment.show(parentFragmentManager, "tag")
        }

        binding.btnZoomIn.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomIn())
        }
        binding.btnZoomOut.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomOut())
        }

    }
    private fun setupMapListeners() {
        googleMap?.setOnCameraMoveStartedListener { reason ->
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                isUserInteracting = true
                //shouldFocusMapOnBounds = false
            }
        }

        googleMap?.setOnCameraIdleListener {
//            if (!isUserInteracting&& shouldFocusMapOnBounds) {
//                focusMapOnBounds()
//            } else {
//                isUserInteracting = false
//            }
            if (isUserInteracting) {
                val currentBounds = googleMap!!.projection.visibleRegion.latLngBounds

                // 경로를 포함하는 영역 계산하여 지도의 중심을 이동
                val latLngBounds = LatLngBounds.builder()
                sharedViewModel.latLngBounds.value?.forEach {
                    latLngBounds.include(LatLng(it.lat, it.lng))
                }

                val routeBounds = latLngBounds.build()
                if (!currentBounds.contains(routeBounds.northeast) || !currentBounds.contains(routeBounds.southwest)) {
                    focusMapOnBounds()
                }
                isUserInteracting = false
            }
        }
    }
    private fun initializeMapView() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }


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

    private fun setMarker(myMap: GoogleMap) {
        googleMap = myMap
        Log.d("라인확인", "onmapready")
        val markerOrigin = MarkerOptions().position(sharedViewModel.getOrigin()).title("출발지")
        val markerDestination =
            MarkerOptions().position(sharedViewModel.getDestination()).title("목적지")

        myMap.addMarker(markerOrigin)
        myMap.addMarker(markerDestination)

        focusMapOnBounds()
//        // 경로를 포함하는 영역 계산하여 지도의 중심을 이동
//        val latLngBounds = LatLngBounds.builder()
//        sharedViewModel.latLngBounds.value?.forEach {
//            latLngBounds.include(LatLng(it.lat, it.lng))
//        }
//        val bounds = latLngBounds.build()
//        val padding = 100
//        googleMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
    }

    private fun focusMapOnBounds(){
        // 경로를 포함하는 영역 계산하여 지도의 중심을 이동
        val latLngBounds = LatLngBounds.builder()
        sharedViewModel.latLngBounds.value?.forEach {
            latLngBounds.include(LatLng(it.lat, it.lng))
        }
        val bounds = latLngBounds.build()

        //위 줄 지우기
        val padding = 100
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
    }
    private fun setLine(myMap: GoogleMap) {
        googleMap = myMap
        sharedViewModel.polyLine.observe(viewLifecycleOwner, Observer { polylines ->

            polylines.forEach { polyline ->
                googleMap?.addPolyline(polyline)
            }

        })
    }

    override fun onMapReady(myMap: GoogleMap) {
        googleMap = myMap
        setLine(myMap)
        setMarker(myMap)
//

//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
//        updateLocation()

        setupMapListeners()
        // 사용자 위치 설정
//        val userLocation = LatLng(/* 위도 */, /* 경도 */)
//        sharedViewModel.setUserLocation(userLocation)
        //
        // 사용자 위치 설정 (예시로 현재 위치를 임의의 값으로 설정)
        //val userLocation = LatLng(37.5665, 126.9780)  // 서울 시청 좌표
        //sharedViewModel.setUserLocation(userLocation)

//        // 지도 초기 포커스 설정
//        focusMapOnBounds()
    }

//    private fun updateLocation() {
//        val locationRequest = LocationRequest.create().apply {
//            interval = 1000
//            fastestInterval = 500
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        }
//
//        locationCallback = object : LocationCallback(){
//            //1초에 한번씩 변경된 위치 정보가 onLocationResult 으로 전달된다.
//            override fun onLocationResult(locationResult: LocationResult) {
//                locationResult.let{
//                    for (location in it.locations){
//                        Log.d("위치정보",  "위도: ${location.latitude} 경도: ${location.longitude}")
//                        setLastLocation(location) //계속 실시간으로 위치를 받아오고 있기 때문에 맵을 확대해도 다시 줄어든다.
//                    }
//                }
//            }
//        }
//        //권한 처리
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback,
//            Looper.myLooper()!!
//        )
//    }

}