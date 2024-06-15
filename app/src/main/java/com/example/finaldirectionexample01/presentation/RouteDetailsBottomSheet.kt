package com.example.finaldirectionexample01.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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
        appContainer.directions1Container?.directionsViewModel1Factory
            ?: throw IllegalStateException("DirectionsViewModel1Factory not initialized properly")
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

        Log.d(
            "확인",
            "값들 : ${sharedViewModel.origin.value.toString()}, ${sharedViewModel.destination.value.toString()},${sharedViewModel.mode.value.toString()}"
        )

        binding.routeDetailsText.text = sharedViewModel.directionExplanations.value
//        sharedViewModel.directionsResult.observe(viewLifecycleOwner, { directions ->
//            directions?.let { directions ->
//                val resultText = StringBuilder()
//                directions.routes.forEach { route ->
//                    route.legs.forEach { leg ->
//                        resultText.append("Total Distance: ${leg.totalDistance.text}\n")
//                        resultText.append("Total Duration: ${leg.totalDuration.text}\n")
//                        leg.steps.forEach { step ->
//                            resultText.append("Step:\n")
//                            resultText.append("  Instruction: ${step.htmlInstructions}\n")
//                            resultText.append("  Duration: ${step.stepDuration.text}\n")
//                            resultText.append("  Distance: ${step.distance.text}\n")
//                            resultText.append("  Travel Mode: ${step.travelMode}\n")
//                        }
//                    }
//                }
//                binding.routeDetailsText.text = resultText.toString()
//            }
//        })

        sharedViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
        //addCheckBoxes()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun addCheckBoxes() {
//        val optionsLayout = binding.optionsLayout
//        val informationOptions = listOf(
//            "Total Distance",
//            "Total Duration",
//            "Step Duration",
//            "HTML Instructions"
//        )
//
//        val informationMap = mapOf(
//            "Total Distance" to { directions: DirectionsModel ->
//                val totalDistance = StringBuilder()
//                directions.routes.forEach { route ->
//                    route.legs.forEach { leg ->
//                        totalDistance.append("Total Distance: ${leg.totalDistance.text}\n")
//                    }
//                }
//                totalDistance.toString()
//            },
//            "Total Duration" to { directions: DirectionsModel ->
//                val totalDuration = StringBuilder()
//                directions.routes.forEach { route ->
//                    route.legs.forEach { leg ->
//                        totalDuration.append("Total Duration: ${leg.totalDuration.text}\n")
//                    }
//                }
//                totalDuration.toString()
//            },
//            "Step Duration" to { directions: DirectionsModel ->
//                val stepDuration = StringBuilder()
//                directions.routes.forEach { route ->
//                    route.legs.forEach { leg ->
//                        leg.steps.forEach { step ->
//                            stepDuration.append("Step Duration: ${step.stepDuration.text}\n")
//                        }
//                    }
//                }
//                stepDuration.toString()
//            },
//            "HTML Instructions" to { directions: DirectionsModel ->
//                val htmlInstructions = StringBuilder()
//                directions.routes.forEach { route ->
//                    route.legs.forEach { leg ->
//                        leg.steps.forEach { step ->
//                            htmlInstructions.append("HTML Instructions: ${step.htmlInstructions}\n")
//                        }
//                    }
//                }
//                htmlInstructions.toString()
//            }
//        )
//
//        informationOptions.forEach { option ->
//            val checkBox = CheckBox(requireContext()).apply {
//                text = option
//                setOnCheckedChangeListener { _, isChecked ->
//                    if (isChecked) {
//                        // 지금 저장된 directionsResult에서 해당 정보를 검색하여 TextView에 표시
//                        sharedViewModel.directionsResult.value?.let { directions ->
//                            binding.routeDetailsText.text = "${binding.routeDetailsText.text}\n${
//                                informationMap[option]?.invoke(directions)
//                            }"
//                        }
//                    } else {
//                        // 체크가 해제된 경우 해당 정보를 제거... 근데 여기 \n 계속 쌓이는듯
//                        val currentText = binding.routeDetailsText.text.toString()
//                        val newText = currentText.replace(
//                            informationMap[option]?.invoke(sharedViewModel.directionsResult.value!!)
//                                .toString(), ""
//                        )
//                        binding.routeDetailsText.text = newText
//                    }
//                }
//            }
//            optionsLayout.addView(checkBox)
//        }
//    }
}