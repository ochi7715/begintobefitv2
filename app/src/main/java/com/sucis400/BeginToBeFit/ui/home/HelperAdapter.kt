package com.sucis400.BeginToBeFit.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sucis400.BeginToBeFit.R
import com.sucis400.BeginToBeFit.ui.home.HelperAdapter.MyViewClass

class HelperAdapter(var name: ArrayList<String>, var sets: ArrayList<Int>, var reps: ArrayList<Int>, var context: Context) :
    RecyclerView.Adapter<MyViewClass>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewClass {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recent_workout_table_row, parent, false)
        return MyViewClass(view)
    }

    override fun onBindViewHolder(holder: MyViewClass, position: Int) {
        holder.name.text = name[position]
        holder.sets.setText(sets[position].toString())
        holder.reps.setText(reps[position].toString())
        holder.itemView.setOnClickListener {
            Toast.makeText(
                context,
                "Item Clicked",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return name.size
    }

    inner class MyViewClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var sets: TextView
        var reps: TextView

        init {
            name = itemView.findViewById<View>(R.id.name) as TextView
            sets = itemView.findViewById<View>(R.id.sets) as TextView
            reps = itemView.findViewById<View>(R.id.reps) as TextView
        }
    }
}
