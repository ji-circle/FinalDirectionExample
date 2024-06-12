package com.example.finaldirectionexample01.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finaldirectionexample01.databinding.FragmentRouteDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RouteDetailsBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentRouteDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRouteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setRouteDetails(details: String) {
        binding.routeDetailsText.text = details
    }
}