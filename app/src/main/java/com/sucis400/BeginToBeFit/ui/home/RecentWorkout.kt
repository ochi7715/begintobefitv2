package com.sucis400.BeginToBeFit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sucis400.BeginToBeFit.R
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class RecentWorkout(private val filePath: String) : Fragment() {
    var recyclerView: RecyclerView? = null
    var name = ArrayList<String>()
    var sets = ArrayList<Int>()
    var reps = ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recent_workout, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView?.layoutManager = linearLayoutManager
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDataFromJson(filePath)
    }

    private fun loadDataFromJson(filePath: String) {
        try {
            val jsonObject = JSONObject(JsonDataFromAsset(filePath))
            val jsonArray = jsonObject.getJSONArray("exercises")
            for (i in 0 until jsonArray.length()) {
                val userData = jsonArray.getJSONObject(i)
                name.add(userData.getString("name"))
                sets.add(userData.getInt("sets"))
                reps.add(userData.getInt("reps"))
            }
            val helperAdapter = HelperAdapter(name, sets, reps, requireContext())
            recyclerView?.adapter = helperAdapter
        } catch (e: JSONException) {
            e.printStackTrace()
        }
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
}
