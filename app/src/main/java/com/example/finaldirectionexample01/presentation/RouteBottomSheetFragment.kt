package com.example.finaldirectionexample01.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
//import com.example.finaldirectionexample01.data.model.Leg
//import com.example.finaldirectionexample01.data.model.Route
import com.example.finaldirectionexample01.databinding.FragmentRouteBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.Route
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
//
//class RouteBottomSheetFragment : BottomSheetDialogFragment() {
//
//    private var _binding: FragmentRouteBottomSheetDialogBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        _binding = FragmentRouteBottomSheetDialogBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        arguments?.let { args ->
//            val routes = args.getParcelableArrayList<Route>("routes")
//            if (routes != null && routes.isNotEmpty()) {
//                val route = routes[0] // 첫 번째 경로만 표시
//                binding.textViewRoute.text = route.summary
//
//                val leg = route.legs[0] // 첫 번째 경로의 첫 번째 leg만 표시
//                binding.textViewDuration.text = leg.duration.text
//                binding.textViewStartAddress.text = leg.startAddress
//                binding.textViewEndAddress.text = leg.endAddress
//
//                if (leg.steps.isNotEmpty()) {
//                    val transitDetails = leg.steps[0].transitDetails
//                    if (transitDetails != null) {
//                        binding.textViewTransit.visibility = View.VISIBLE
//                        binding.textViewTransit.text = "Transit details: ${transitDetails.line.name}, ${transitDetails.line.vehicle.name}"
//                    } else {
//                        binding.textViewTransit.visibility = View.GONE
//                    }
//                } else {
//                    binding.textViewTransit.visibility = View.GONE
//                }
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    private fun formatEpochTime(epochTime: Long): String {
//        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
//        return sdf.format(Date(epochTime))
//    }
//}
//
////class RouteBottomSheetFragment : BottomSheetDialogFragment() {
////
////    private lateinit var binding: FragmentRouteBottomSheetDialogBinding
////
////    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
////        binding = FragmentRouteBottomSheetDialogBinding.inflate(inflater, container, false)
////        return binding.root
////    }
////
////    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
////        super.onViewCreated(view, savedInstanceState)
////
////        // 바텀 시트의 내용을 여기에 설정하세요.
////        binding.textViewRoute.text = "Your route description goes here."
////        binding.textViewDuration.text = "Duration: 1 hour"
////        binding.textViewTransport.text = "Transport: Bus"
////        binding.textViewCost.text = "Cost: $5"
////        binding.textViewArrivalTime.text = "Arrival time: 10:00 AM"
////    }
////}
//
