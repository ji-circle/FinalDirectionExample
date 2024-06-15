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

    //private var isPolylineVisible = false

    private var polylineVm: PolylineOptions?=null


    private lateinit var locationPermission: ActivityResultLauncher<Array<String>>
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val appContainer: AppContainer by lazy {
        (requireActivity().application as FinalDirectionApplication).appContainer
    }

    // DirectionsViewModel1Factory 가져오기
    private val directionsViewModel1Factory: DirectionsViewModel1Factory by lazy {
        appContainer.directions1Container.directionsViewModel1Factory
            ?: throw IllegalStateException("DirectionsViewModel1Factory not initialized properly")
    }

    // SharedViewModel 가져오기
    private val sharedViewModel: DirectionsViewModel1 by activityViewModels { directionsViewModel1Factory }

    //sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

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

//    private fun togglePolyline(){
//        if(monitorPolyline == null){
//            //monitorPolyline = googleMap?.addPolyline()
//        }else{
//            monitorPolyline?.remove()
//            monitorPolyline=null
//        }
//    }
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
//            isPolylineVisible = !isPolylineVisible
            // 경로 나타내기와 숨기기 버튼 구현
//            googleMap?.clear()
            googleMap?.let { it1 -> setLine(it1) }
//            googleMap?.let { it1 -> setMarker(it1) }
        }

        binding.btnZoomIn.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomIn())
        }
        binding.btnZoomOut.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomOut())
        }

        initializeMapView()


    }
    private fun initializeMapView() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }
//    private fun initializeMapView() {
//
//        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
//        mapFragment?.getMapAsync { map ->
//            googleMap = map
//            // Optionally enable the user location
//            if (ContextCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                googleMap?.isMyLocationEnabled = true
//
//            }
//
//
//        }
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

    private fun setMarker(myMap: GoogleMap){
        googleMap = myMap
        Log.d("라인확인", "onmapready")
        val markerOrigin = MarkerOptions().position(sharedViewModel.getOrigin()).title("출발지")
        val markerDestination = MarkerOptions().position(sharedViewModel.getDestination()).title("목적지")

        myMap.addMarker(markerOrigin)
        myMap.addMarker(markerDestination)

        // 경로를 포함하는 영역 계산하여 지도의 중심을 이동
        val latLngBounds = LatLngBounds.builder()
        sharedViewModel.latLngBounds.value?.forEach {
            latLngBounds.include(LatLng(it.lat, it.lng))
        }
        val bounds = latLngBounds.build()
        val padding = 100
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
    }

    private fun setLine(myMap: GoogleMap){
        googleMap = myMap
        
        sharedViewModel.polyLine.observe(viewLifecycleOwner, Observer { polylines->
            //if(isPolylineVisible){
                polylines.forEach{polyline ->
                    googleMap?.addPolyline(polyline)
                }
           // }
//            else{
//                googleMap?.clear()
//                setMarker(googleMap!!)
//            }
        })
//        // 경로를 포함하는 영역 계산하여 지도의 중심을 이동
//        val latLngBounds = LatLngBounds.builder()
//        sharedViewModel.latLngBounds.value?.forEach {
//            latLngBounds.include(LatLng(it.lat, it.lng))
//        }
//        val bounds = latLngBounds.build()
//        val padding = 100
//        googleMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
    }
    override fun onMapReady(myMap: GoogleMap) {
        googleMap = myMap
        setMarker(myMap)
//        setLine(myMap)
    }

}