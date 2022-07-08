package com.example.kotlin_zem.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_zem.DataClass.ComplimentItem
import com.example.kotlin_zem.R
import com.example.kotlin_zem.database.Zem
import javax.security.auth.login.LoginException


class ComplimentAdapter(val context: Context, var compliments:List<ComplimentItem>):
    RecyclerView.Adapter<ComplimentAdapter.Hoder>(){
    private var mSelectedItem = -1
    private lateinit var itemClickListner: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Hoder {
        val view = LayoutInflater.from(context).inflate(R.layout.compliment_item, parent, false)
        return Hoder(view)
    }

    override fun onBindViewHolder(holder: Hoder, position: Int) {
        holder?.bind(compliments[position], position)
    }

    override fun getItemCount() = compliments.size

    inner class Hoder(itemView: View): RecyclerView.ViewHolder(itemView){
        val complimentimage = itemView.findViewById<ImageFilterView>(R.id.complimentimage)
        val sendbt = itemView.findViewById<Button>(R.id.sendbt)

        fun bind(compliment: ComplimentItem, position: Int){
            val imageId = context.resources.getIdentifier(compliment.complimentimage,"drawable", context.packageName)
            complimentimage.setImageResource(imageId)
            complimentimage.isSelected = position == mSelectedItem

            complimentimage.setOnClickListener{
                if(mSelectedItem == position){
                    mSelectedItem = -1
                    itemClickListner.onClick(it, compliment, mSelectedItem)
                }else {
                    mSelectedItem = position
                    itemClickListner.onClick(it, compliment, mSelectedItem)
                    complimentimage.isSelected = complimentimage.isSelected != true
                }
                notifyDataSetChanged()
            }
        }
    }
    interface ItemClickListener {
        fun onClick(view: View, compliment: ComplimentItem, mSelectedItem: Int)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}