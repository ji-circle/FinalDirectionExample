package com.example.finaldirectionexample01.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.finaldirectionexample01.FinalDirectionApplication
import com.example.finaldirectionexample01.data.AppContainer
import com.example.finaldirectionexample01.databinding.FragmentRouteDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RouteDetailsBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentRouteDetailsBinding? = null
    private val binding get() = _binding!!

    private val appContainer: AppContainer by lazy {
        (requireActivity().application as FinalDirectionApplication).appContainer
    }

    // DirectionsViewModel1Factory 가져오기
    private val directionsViewModel1Factory: DirectionsViewModel1Factory by lazy {
        appContainer.directions1Container?.directionsViewModel1Factory ?: throw IllegalStateException("DirectionsViewModel1Factory not initialized properly")
    }

    // SharedViewModel 가져오기
    private val sharedViewModel: DirectionsViewModel1 by activityViewModels { directionsViewModel1Factory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRouteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.directionsResult.observe(viewLifecycleOwner, { directions ->
            directions?.let { directions ->
                val resultText = StringBuilder()
                directions.routes.forEach { route ->
                    route.legs.forEach { leg ->
                        resultText.append("Total Distance: ${leg.totalDistance.text}\n")
                        resultText.append("Total Duration: ${leg.totalDuration.text}\n")
                        leg.steps.forEach { step ->
                            resultText.append("Step:\n")
                            resultText.append("  Instruction: ${step.htmlInstructions}\n")
                            resultText.append("  Duration: ${step.stepDuration.text}\n")
                            resultText.append("  Distance: ${step.distance.text}\n")
                            resultText.append("  Travel Mode: ${step.travelMode}\n")
                            // 추가적인 정보는 필요에 따라 처리
                        }
                    }
                }
                // TextView에 결과를 표시
                binding.routeDetailsText.text = resultText.toString()
            }
        })

        // 공유 뷰모델에서 오류 메시지를 관찰하여 필요한 경우 토스트 메시지로 표시
        sharedViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}