package com.example.finaldirectionexample01.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.finaldirectionexample01.FinalDirectionApplication
import com.example.finaldirectionexample01.R
import com.example.finaldirectionexample01.data.AppContainer
import com.example.finaldirectionexample01.data.Directions1Container
import com.example.finaldirectionexample01.databinding.FragmentDirectionsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng

class DirectionsFragment : Fragment() {
    private var _binding: FragmentDirectionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val LOCATION_PERMISSION_REQUEST_CODE = 1


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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDirectionsBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        checkPermission()
        getCurrentLocation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.searchButton.setOnClickListener {
            getCurrentLocation()
            val origin = sharedViewModel.getUserLocationString()
            if (origin != null) {
                //val origin = "${sharedViewModel.userLocation.value?.latitude}"+","+"${sharedViewModel.userLocation.value?.longitude}"
                Log.d("확인", "origin , ${origin}")
//            val origin = binding.originEditText.text.toString()
                val destination = binding.destinationEditText.text.toString()
                val mode = binding.typeEditText.text.toString() ?: "transit" //이거 스피너로 바꾸기
                sharedViewModel.getDirections(origin, destination, mode)
                binding.btnBottomSheet.isVisible = true
                binding.btnMap.isVisible = true
            } else {
                Log.d("확인 에러발생", "origin null")
            }
        }

        binding.btnBottomSheet.setOnClickListener {
            val bottomSheetDialogFragment = RouteDetailsBottomSheet()
            bottomSheetDialogFragment.show(parentFragmentManager, "tag")
        }
        binding.btnMap.setOnClickListener {
            val mapFragment = MapFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mapFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.resultTextView.text = sharedViewModel.directionExplanations.value
//        sharedViewModel.directionsResult.observe(viewLifecycleOwner, { directions ->
//            directions?.let { directions ->
//                val resultText = StringBuilder()
//                directions.routes.forEach { route ->
//                    route.legs.forEach { leg ->
//                        resultText.append("🗺️목적지까지 ${leg.totalDistance.text},\n")
//                        resultText.append("앞으로 ${leg.totalDuration.text} 뒤인\n")
//                        resultText.append("🕐${leg.totalArrivalTime.text}에 도착 예정입니다.\n")
//                        resultText.append("\n")
//                        var num = 1
//                        leg.steps.forEach { step ->
//                            resultText.append("${num}:\n")
//                            resultText.append("  상세설명: ${step.htmlInstructions}\n")
//                            resultText.append("  소요시간: ${step.stepDuration.text}\n")
//                            resultText.append("  구간거리: ${step.distance.text}\n")
//                            resultText.append("  이동수단: ${step.travelMode}")
//                            if (step.transitDetails != DirectionsTransitDetailsModel(
//                                    DirectionsTransitStopModel(LatLngModel(0.0, 0.0), ""),
//                                    TimeZoneTextValueObjectModel("", "", 0.0),
//                                    DirectionsTransitStopModel(LatLngModel(0.0, 0.0), ""),
//                                    TimeZoneTextValueObjectModel("", "", 0.0),
//                                    (""),
//                                    0,
//                                    DirectionsTransitLineModel(
//                                        emptyList(),
//                                        "",
//                                        "",
//                                        "",
//                                        "",
//                                        "",
//                                        "",
//                                        DirectionsTransitVehicleModel("", "", "", "")
//                                    ),
//                                    0,
//                                    ""
//                                )
//                            ) {
//                                resultText.append(" : ${step.transitDetails.line.shortName}, ${step.transitDetails.line.name}\n")
//                                resultText.append("    ${step.transitDetails.headSign} 행\n")
//                                resultText.append("    탑승 장소: ${step.transitDetails.departureStop.name}\n")
//                                resultText.append("    하차 장소: ${step.transitDetails.arrivalStop.name}\n")
//                                resultText.append("    ${step.transitDetails.numStops}")
//                                resultText.append(categorizeTransportation(step.transitDetails.line.vehicle.type))
//                                resultText.append("\n\n")
//
//                            } else {
//                                resultText.append("\n\n\n")
//                            }
//
//                            num++
//                        }
//                    }
//                }
//                // TextView
//                binding.resultTextView.text = resultText.toString()
//            }
//        })
        sharedViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }

        // 사용자의 현재 위치를 관찰
        sharedViewModel.userLocation.observe(viewLifecycleOwner, Observer { location ->
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        })
    }

//    private fun categorizeTransportation(transportationType: String): String {
//        when (transportationType) {
//            "BUS" -> {
//                return "개 정류장 이동🚍\n"
//            }
//            "CABLE_CAR" -> {
//                // 케이블 카에 대한 처리
//                return " 케이블 카 이용🚟\n"
//            }
//            "COMMUTER_TRAIN" -> {
//                // 통근 기차에 대한 처리
//                return "개 역 이동🚞\n"
//            }
//            "FERRY" -> {
//                // 페리에 대한 처리
//                return " 페리 이용⛴️\n"
//            }
//            "FUNICULAR" -> {
//                // 푸니큘러에 대한 처리
//                return " 푸니큘러 이용🚋\n"
//            }
//            "GONDOLA_LIFT" -> {
//                // 곤돌라 리프트에 대한 처리
//                return " 곤돌라 리프트 이용🚠\n"
//            }
//            "HEAVY_RAIL" -> {
//                // 대형 철도에 대한 처리
//                return "개 역 이동🛤️\n"
//            }
//            "HIGH_SPEED_TRAIN" -> {
//                // 고속 기차에 대한 처리
//                return "개 역 이동🚄\n"
//            }
//            "INTERCITY_BUS" -> {
//                // 시외 버스에 대한 처리
//                return "개 정류장 이동🚌\n"
//            }
//            "LONG_DISTANCE_TRAIN" -> {
//                // 장거리 기차에 대한 처리
//                return "개 역 이동🚂\n"
//            }
//            "METRO_RAIL" -> {
//                // 도시 철도에 대한 처리
//                return "개 역 이동🚇\n"
//            }
//            "MONORAIL" -> {
//                // 모노레일에 대한 처리
//                return "개 역 이동🚝\n"
//            }
//            "OTHER" -> {
//                // 기타에 대한 처리
//                return " 이동\n"
//            }
//            "RAIL" -> {
//                // 철도에 대한 처리
//                return "개 역 이동🚃\n"
//            }
//            "SHARE_TAXI" -> {
//                // 공유 택시에 대한 처리
//                return " 공유 택시 이용🚖\n"
//            }
//            "SUBWAY" -> {
//                // 지하철에 대한 처리
//                return "개 역 이동🚉\n"
//            }
//            "TRAM" -> {
//                // 트램에 대한 처리
//                return "개 역 트램으로 이동🚊\n"
//            }
//            "TROLLEYBUS" -> {
//                // 트롤리버스에 대한 처리
//                return "개 정류장 트롤리버스로 이동🚎\n"
//            }
//            else -> {
//                // 알 수 없는 경우 처리
//                return " 이동\n"
//            }
//        }
//    }
    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val userLatLng = LatLng(it.latitude, it.longitude)
                    sharedViewModel.setUserLocation(userLatLng)
                } ?: run {
                    Toast.makeText(requireContext(), "위치 얻기 실패", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "위치 얻기 실패", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // 권한이 부여되었으므로 현재 위치를 받아옴
                getCurrentLocation()
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
