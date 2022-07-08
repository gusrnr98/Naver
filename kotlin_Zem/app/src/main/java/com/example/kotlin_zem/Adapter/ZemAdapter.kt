package com.example.kotlin_zem.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_zem.DataClass.HabitItem
import com.example.kotlin_zem.R
import com.example.kotlin_zem.database.Zem

class ZemAdapter(val context: Context, val zem:List<Zem>, val itemClick: (Zem) -> Unit):
RecyclerView.Adapter<ZemAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.habit_wait_item, parent, false)
        return Holder(view, itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(zem[position])
    }

    override fun getItemCount() = zem.size

    inner class Holder(itemView: View, itemClick: (Zem) -> Unit): RecyclerView.ViewHolder(itemView) {
        val dayofweek = itemView.findViewById<TextView>(R.id.dayofweek)
        val habitname  = itemView.findViewById<TextView>(R.id.habitname)
        val image = itemView.findViewById<ImageView>(R.id.habitimage)
        val habitstate = itemView.findViewById<TextView>(R.id.habitstate)
        val date = itemView.findViewById<TextView>(R.id.date)
        val cherrbt = itemView.findViewById<Button>(R.id.cheerbt)

        fun bind(zem:Zem){
            if(zem.zemeditcheck == 0){
                habitstate.text = "등록 대기"
                habitstate.setTextColor(Color.parseColor("#5CD1E5"))
                habitstate.setBackgroundColor(Color.parseColor("#ECFFFF"))
            }else{
                habitstate.text = "수정 요청"
                habitstate.setTextColor(Color.parseColor("#D941C5"))
                habitstate.setBackgroundColor(Color.parseColor("#FAEBFF"))
            }
            val imageId = context.resources.getIdentifier(zem.habitimage,"drawable",context.packageName)

            dayofweek?.text = zem.dayofweek
            habitname?.text = zem.habitname
            image.setImageResource(imageId)

            date.visibility = View.GONE
            cherrbt.visibility = View.GONE

            itemView.setOnClickListener{itemClick(zem)}

        }
    }

}