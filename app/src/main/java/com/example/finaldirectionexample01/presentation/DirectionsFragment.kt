package com.example.finaldirectionexample01.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finaldirectionexample01.FinalDirectionApplication
import com.example.finaldirectionexample01.data.AppContainer
import com.example.finaldirectionexample01.data.Directions1Container
import com.example.finaldirectionexample01.databinding.FragmentDirectionsBinding

class DirectionsFragment : Fragment() {
    private var _binding: FragmentDirectionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DirectionsViewModel1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDirectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appContainer = (requireActivity().application as FinalDirectionApplication).appContainer
        appContainer.directions1Container = Directions1Container(appContainer.getDirectionsUseCase)

        appContainer.directions1Container?.let {
            viewModel = ViewModelProvider(
                this,
                it.directionsViewModel1Factory
            )[DirectionsViewModel1::class.java]
        }

        binding.searchButton.setOnClickListener {
            val origin = binding.originEditText.text.toString()
            val destination = binding.destinationEditText.text.toString()
            val mode = "transit" //이거 스피너로 바꾸기
            viewModel.getDirections(origin, destination, mode)
        }

//        viewModel.directionsResult.observe(viewLifecycleOwner, { directions ->
//            val resultText = buildString {
//                directions.routes.forEachIndexed { routeIndex, route ->
//                    append("Route ${routeIndex + 1}:\n")
//                    append("Summary: ${route.summary}\n")
//                    append("Warnings: ${route.warnings.joinToString(", ")}\n")
//                    append("Legs:\n")
//                    route.legs.forEachIndexed { legIndex, leg ->
//                        append("  Leg ${legIndex + 1}:\n")
//                        append("    Start Address: ${leg.totalStartAddress}\n")
//                        append("    End Address: ${leg.totalEndAddress}\n")
//                        append("    Duration: ${leg.totalDuration.text}\n")
//                        append("    Distance: ${leg.totalDistance.text}\n")
//                        append("    Arrival time: ${leg.totalArrivalTime.text}\n")
//                        append("    Departure time: ${leg.totalDepartureTime.text}\n")
//                        append("    Steps:\n")
//                        leg.steps.forEachIndexed { stepIndex, step ->
//                            append("      Step ${stepIndex + 1}:\n")
//                            append("        Instruction: ${step.htmlInstructions}\n")
//                            append("        Duration: ${step.stepDuration.text}\n")
//                            append("        Distance: ${step.distance.text}\n")
//                            append("        Travel Mode: ${step.travelMode}\n")
//                            if (step.travelMode == "TRANSIT") {
//                                step.transitDetails?.let { transitDetails ->
//                                    append("        Transit Details:\n")
//                                    append("          Arrival Stop Name: ${transitDetails.arrivalStop.name}\n")
//                                    append("          Arrival Stop Location: ${transitDetails.arrivalStop.location}\n")
//                                    append("          Arrival Time: ${transitDetails.arrivalTime.text}\n")
//                                    append("          Departure Stop Name: ${transitDetails.departureStop.name}\n")
//                                    append("          Departure Stop Location: ${transitDetails.departureStop.location}\n")
//                                    append("          Departure Time: ${transitDetails.departureTime.text}\n")
//                                    append("          Head Sign: ${transitDetails.headSign}\n")
//                                    append("          Head Way: ${transitDetails.headWay}\n")
//                                    append("          Line Name: ${transitDetails.line.name}\n")
//                                    append("          Agencies:\n")
//                                    transitDetails.line.agencies.forEachIndexed { agencyIndex, agency ->
//                                        append("            Agency ${agencyIndex + 1}:\n")
//                                        append("              Name: ${agency.name}\n")
//                                        append("              Phone: ${agency.phone}\n")
//                                        append("              URL: ${agency.url}\n")
//                                    }
//                                    append("          Color: ${transitDetails.line.color}\n")
//                                    append("          Icon: ${transitDetails.line.icon}\n")
//                                    append("          Short Name: ${transitDetails.line.shortName}\n")
//                                    append("          Text Color: ${transitDetails.line.textColor}\n")
//                                    append("          URL: ${transitDetails.line.url}\n")
//                                    append("          Vehicle Name: ${transitDetails.line.vehicle.name}\n")
//                                    append("          Vehicle Type: ${transitDetails.line.vehicle.type}\n")
//                                    append("          Vehicle Icon: ${transitDetails.line.vehicle.icon}\n")
//                                    append("          Vehicle Local Icon: ${transitDetails.line.vehicle.localIcon}\n")
//                                    append("          Number of Stops: ${transitDetails.numStops}\n")
//                                    append("          Trip Short Name: ${transitDetails.tripShortName}\n")
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            binding.resultTextView.text = resultText
//        })

        viewModel.directionsResult.observe(viewLifecycleOwner, { directions ->
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
                            //...
                        }
                    }
                }
                // TextView
                binding.resultTextView.text = resultText.toString()
            }
        })
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
