package com.example.kotlin_zem.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_zem.DataClass.HabitItem
import com.example.kotlin_zem.Fragment.AddHabitStep1Fragment
import com.example.kotlin_zem.R

class HabitAdapter(val context: Context, val habit: List<HabitItem>, val itemClick: (HabitItem) -> Unit):
RecyclerView.Adapter<HabitAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.habit_item, parent, false)
        return Holder(view, itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
       holder.bind(habit[position])
    }

    override fun getItemCount() = habit.size

    inner class Holder(itemView: View, itemClick: (HabitItem) -> Unit): RecyclerView.ViewHolder(itemView) {
        val habitImage = itemView.findViewById<ImageView>(R.id.habitimage)
        val habitsubtitle = itemView.findViewById<TextView>(R.id.habitsubtitle)
        val habittitle = itemView.findViewById<TextView>(R.id.habittitle)
        fun bind(habit: HabitItem) {
            val imageId = context.resources.getIdentifier(habit.habitimage,"drawable",context.packageName)

            if(habit.habitsubtitle.equals("")){
                habitsubtitle.visibility = View.GONE
            }

            habitsubtitle.text = habit.habitsubtitle
            habittitle.text = habit.habittitle
            habitImage.setImageResource(imageId)

            itemView.setOnClickListener { itemClick(habit) }
        }
    }

}