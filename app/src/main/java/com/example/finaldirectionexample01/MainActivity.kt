package com.example.finaldirectionexample01

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.finaldirectionexample01.data.AppContainer
import com.example.finaldirectionexample01.data.Directions1Container
import com.example.finaldirectionexample01.data.DirectionsRepositoryImpl
import com.example.finaldirectionexample01.databinding.ActivityMainBinding
import com.example.finaldirectionexample01.domain.usecase.GetDirectionsUseCase
import com.example.finaldirectionexample01.presentation.DirectionsFragment
//import com.example.finaldirectionexample01.presentation.DirectionsViewModel
import com.example.finaldirectionexample01.presentation.DirectionsViewModel1
//import com.example.finaldirectionexample01.presentation.DirectionsViewModelFactory
//import com.example.finaldirectionexample01.presentation.RouteBottomSheetFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.PolylineOptions
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//class MainActivity : AppCompatActivity(), OnMapReadyCallback {
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var viewModel: DirectionsViewModel
//    private lateinit var map: GoogleMap
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        enableEdgeToEdge()
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//
//        val viewModelFactory = AppContainer.provideDirectionsViewModelFactory()
//        viewModel = ViewModelProvider(this, viewModelFactory).get(DirectionsViewModel::class.java)
//
//        val transportModes = resources.getStringArray(R.array.transport_modes)
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, transportModes)
//        binding.modeSpinner.adapter = adapter
//
//        binding.directionsButton.setOnClickListener {
//            val origin = binding.originEditText.text.toString()
//            val destination = binding.destinationEditText.text.toString()
//            val mode = binding.modeSpinner.selectedItem.toString()
//
//            if (origin.isBlank() || destination.isBlank()) {
//                Toast.makeText(this, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show()
//            } else {
//                viewModel.getDirections(origin, destination, mode)
//            }
//        }
//
//        viewModel.directionsLiveData.observe(this, Observer { routes ->
//            if (routes.isNotEmpty()) {
//                val route = routes.first()
//                val points = route.overviewPolyline.points.decodePath()
//                map.addPolyline(PolylineOptions().addAll(points))
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(points.first(), 10f))
//
//                val leg = route.legs.first()
//                val durationInSeconds = leg.duration.value
//                val departureTime = leg.departureTime.value * 1000
//                val arrivalTime = leg.arrivalTime.value * 1000
//
//                setupAlarm(departureTime - durationInSeconds * 1000)
//
//                Toast.makeText(this, "Departure time: ${formatEpochTime(departureTime)}, Arrival time: ${formatEpochTime(arrivalTime)}", Toast.LENGTH_LONG).show()
//
//                binding.toggleBottomSheetButton.visibility = View.VISIBLE
//            } else {
//                Toast.makeText(this, "No routes available", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        binding.toggleBottomSheetButton.setOnClickListener {
//            val bottomSheetFragment = RouteBottomSheetFragment()
//            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
//        }
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        map = googleMap
//    }
//
//    private fun parseTimeToEpoch(time: String): Long {
//        return SimpleDateFormat("HH:mm", Locale.getDefault()).parse(time).time
//    }
//
//    private fun formatEpochTime(epochTime: Long): String {
//        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(epochTime))
//    }
//
//    private fun setupAlarm(timeInMillis: Long) {
//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(this, AlarmReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(
//            this,
//            0,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE
//        ) // FLAG_IMMUTABLE 플래그 추가
//
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
//    }
//}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val sharedViewModel: DirectionsViewModel1 by lazy {
        val appContainer = (application as FinalDirectionApplication).appContainer
        val directions1Container = appContainer.directions1Container
        val directionsViewModel1Factory = directions1Container?.directionsViewModel1Factory
        directionsViewModel1Factory?.let {
            ViewModelProvider(this, it).get(DirectionsViewModel1::class.java)
        } ?: throw IllegalStateException("DirectionsViewModel1Factory not initialized properly")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DirectionsFragment())
                .commit()
        }

    }

}