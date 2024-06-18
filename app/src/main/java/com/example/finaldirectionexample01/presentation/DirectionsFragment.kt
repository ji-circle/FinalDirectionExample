package com.example.finaldirectionexample01.presentation

import android.Manifest
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.finaldirectionexample01.FinalDirectionApplication
import com.example.finaldirectionexample01.R
import com.example.finaldirectionexample01.data.AppContainer
import com.example.finaldirectionexample01.databinding.FragmentDirectionsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.Locale

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
        appContainer.directions1Container.directionsViewModel1Factory
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
                Log.d("확인", "origin , ${origin}")
                val destination = binding.destinationEditText.text.toString()
                val mode = binding.typeEditText.text.toString() ?: "transit" //이거 스피너로 바꾸기
                val travelMode = binding.TravelModeEditText.text.toString()
                val departureTime = sharedViewModel.getUnixTimestamp().toString().toInt()
                Log.d("확인 확인", "$departureTime")
                val transitRoutingPreference = binding.RoutePrfEditText.text.toString()
                sharedViewModel.getDirectionsWithDepartureTmRp(origin, destination, departureTime, travelMode, transitRoutingPreference)
                //sharedViewModel.getDirections(origin, destination, mode)
                binding.btnBottomSheet.isVisible = true
                binding.btnMap.isVisible = true
            } else {
                Log.d("확인 에러발생", "origin null")
            }
        }

        setUpTimePicker()
        observeViewModel()

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

        sharedViewModel.directionExplanations.observe(viewLifecycleOwner, Observer {
            binding.resultTextView.text = it
        })

        sharedViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }

        // 사용자의 현재 위치를 관찰
        sharedViewModel.userLocation.observe(viewLifecycleOwner, Observer { location ->
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        })
    }

    private fun observeViewModel() {
        sharedViewModel.selectedTime.observe(viewLifecycleOwner, { time ->
            time?.let {
                val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", it.hour, it.minute)
                binding.DepTimeEditText.setText(selectedTime)
            }
        })
    }

    private fun setUpTimePicker() {
        binding.DepTimeEditText.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun showTimePickerDialog() {
        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentTime.get(Calendar.MINUTE)

        TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
            binding.DepTimeEditText.setText(selectedTime)

            sharedViewModel.setTime(hourOfDay, minute)

        }, currentHour, currentMinute, true).show()
    }


    private fun convertToUnixTimestamp(hour: Int, minute: Int): Long {
        val currentDate = LocalDate.now()
        val currentTime = LocalTime.now()
        var dateTime = LocalDateTime.of(currentDate, LocalTime.of(hour, minute))

        if (dateTime.toLocalTime().isBefore(currentTime)) {
            dateTime = dateTime.plusDays(1)
        }

        val zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.systemDefault())
        return zonedDateTime.toEpochSecond()

    }

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
                    Toast.makeText(requireContext(), "1 위치 얻기 실패", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "2 위치 얻기 실패", Toast.LENGTH_SHORT)
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
