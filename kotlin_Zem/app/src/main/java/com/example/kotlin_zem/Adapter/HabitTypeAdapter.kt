package com.example.kotlin_zem.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_zem.R

class HabitTypeAdapter(var context: Context, val habitType: List<String>, var itemClick: (String) -> Unit):
RecyclerView.Adapter<HabitTypeAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.habittypeitem, parent, false)
        return Holder(view,itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(habitType[position])
    }

    override fun getItemCount() = habitType.size

    inner class Holder(itemView: View, itemClick: (String) -> Unit): RecyclerView.ViewHolder(itemView){
        val habittypeText = itemView?.findViewById<TextView>(R.id.habittypetextView)
        fun bind(habitType: String){
            habittypeText.text = habitType

            itemView.setOnClickListener { itemClick(habitType) }
        }
    }



}