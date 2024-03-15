package com.sucis400.BeginToBeFit.ui.home

import com.sucis400.BeginToBeFit.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.LineChart
import com.sucis400.BeginToBeFit.databinding.FragmentHomeBinding
import android.graphics.Color
import android.util.Log
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class HomeFragment : Fragment() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var lineChart: LineChart? = null
    private var xValues: List<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val HomeViewModel: HomeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.homeHeader
        HomeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = "Begin To BeFit"
        }
        lineChart = root.findViewById<LineChart>(R.id.homeStepChart)
        lineChart?.axisRight?.setDrawLabels(false)
        xValues = mutableListOf("Saturday", "Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val xAxis = lineChart?.xAxis
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.valueFormatter = IndexAxisValueFormatter(xValues)
        xAxis?.labelCount = 7
        xAxis?.granularity = 1f
        val yAxis = lineChart?.axisLeft
        yAxis?.axisMinimum = 0f
        yAxis?.axisMaximum = 10000f
        yAxis?.axisLineWidth = 2f
        yAxis?.axisLineColor = Color.BLACK
        yAxis?.labelCount = 10
        var stepsArray = loadDataFromJson("steps")
        val entries1: MutableList<Entry> = ArrayList()
        entries1.add(Entry(0f, stepsArray[0].toFloat()))
        entries1.add(Entry(1f, stepsArray[1].toFloat()))
        entries1.add(Entry(2f, stepsArray[2].toFloat()))
        entries1.add(Entry(3f, stepsArray[3].toFloat())) 
        entries1.add(Entry(4f, stepsArray[4].toFloat()))
        entries1.add(Entry(5f, stepsArray[5].toFloat()))
        entries1.add(Entry(6f, stepsArray[6].toFloat()))
        val dataSet1 = LineDataSet(entries1, "Steps")
        dataSet1.color = Color.BLUE
        val lineData = LineData(dataSet1)
        lineChart?.data = lineData
        lineChart!!.invalidate()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recentWorkoutButtonOne = requireView().findViewById<Button>(R.id.recentWorkoutButtonOne)
        val recentWorkoutButtonTwo = requireView().findViewById<Button>(R.id.recentWorkoutButtonTwo)
        val recentWorkoutButtonThree = requireView().findViewById<Button>(R.id.recentWorkoutButtonThree)
        val recentWorkoutButtonFour = requireView().findViewById<Button>(R.id.recentWorkoutButtonFour)
        val recentWorkoutButtonFive = requireView().findViewById<Button>(R.id.recentWorkoutButtonFive)
        val recentWorkoutButtonSix = requireView().findViewById<Button>(R.id.recentWorkoutButtonSix)
        val recentWorkoutButtonSeven = requireView().findViewById<Button>(R.id.recentWorkoutButtonSeven)

        binding.recentWorkoutButtonOne.setOnClickListener {
            navigateToRecentWorkoutFragment("workout_data_sunday")
        }
        binding.recentWorkoutButtonTwo.setOnClickListener {
            navigateToRecentWorkoutFragment("workout_data_saturday")
        }
        binding.recentWorkoutButtonThree.setOnClickListener {
            navigateToRecentWorkoutFragment("workout_data_friday")
        }
        binding.recentWorkoutButtonFour.setOnClickListener {
            navigateToRecentWorkoutFragment("workout_data_thursday")
        }
        binding.recentWorkoutButtonFive.setOnClickListener {
            navigateToRecentWorkoutFragment("workout_data_wednesday")
        }
        binding.recentWorkoutButtonSix.setOnClickListener {
            navigateToRecentWorkoutFragment("workout_data_tuesday")
        }
        binding.recentWorkoutButtonSeven.setOnClickListener {
            navigateToRecentWorkoutFragment("workout_data_monday")
        }
    }

    private fun navigateToRecentWorkoutFragment(filename: String) {
        val fragment = RecentWorkout(filename)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutNav, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun loadDataFromJson(filePath: String): ArrayList<Int> {
        val stepsList = ArrayList<Int>()
        try {
            val jsonString = JsonDataFromAsset(filePath)
            val jsonObject = JSONObject(jsonString)
            val stepsObject = jsonObject.getJSONObject("steps")
            val dowArray = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
            if (stepsObject != null) {
                for (i in dowArray) {
                    val steps = stepsObject.getInt(i)
                    stepsList.add(steps)
                }
            } else {
                Log.e("Error", "Steps array is null or not found in JSON")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d("StepsList", stepsList.toString())
        return stepsList
    }

    private fun JsonDataFromAsset(filePath: String): String? {
        var json: String? = null
        json = try {
            val inputStream = resources.openRawResource(resources.getIdentifier(filePath, "raw", requireContext().packageName))
            val sizeOfFile = inputStream.available()
            val bufferData = ByteArray(sizeOfFile)
            inputStream.read(bufferData)
            inputStream.close()
            String(bufferData, charset("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return json
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

