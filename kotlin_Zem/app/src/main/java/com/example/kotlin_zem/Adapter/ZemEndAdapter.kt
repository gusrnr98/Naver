package com.example.kotlin_zem.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_zem.DataClass.ComplimentItem
import com.example.kotlin_zem.DataClass.HabitItem
import com.example.kotlin_zem.R
import com.example.kotlin_zem.database.Zem
import com.example.kotlin_zem.database.ZemCompletion
import com.example.kotlin_zem.database.ZemEnd
import com.example.kotlin_zem.database.ZemEndDB

class ZemEndAdapter(val context: Context, val zemend:List<ZemEnd>, val itemClick: (ZemEnd) -> Unit):
RecyclerView.Adapter<ZemEndAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.habit_wait_item, parent, false)
        return Holder(view,itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(zemend[position])
    }

    override fun getItemCount() = zemend.size

    inner class Holder(itemView: View,itemClick: (ZemEnd) -> Unit): RecyclerView.ViewHolder(itemView) {
        val dayofweek = itemView.findViewById<TextView>(R.id.dayofweek)
        val habitname  = itemView.findViewById<TextView>(R.id.habitname)
        val image = itemView.findViewById<ImageView>(R.id.habitimage)
        val habitstate = itemView.findViewById<TextView>(R.id.habitstate)
        val date = itemView.findViewById<TextView>(R.id.date)
        val cherrbt = itemView.findViewById<Button>(R.id.cheerbt)
        var dbe: ZemEndDB? =null
        var cheername = ""
        private var zemEnd = listOf<ZemEnd>()
        private val complimentlist = arrayListOf<ComplimentItem>(
            ComplimentItem("grow"),
            ComplimentItem("fist"),
            ComplimentItem("superman"),
            ComplimentItem("rich"),
            ComplimentItem("target"),
            ComplimentItem("ghost"),
            ComplimentItem("clap"),
            ComplimentItem("stamp"),
            ComplimentItem("collection"),
            ComplimentItem("flowerpot")
        )

        fun bind(zemend:ZemEnd){
            val imageId = context.resources.getIdentifier(zemend.habitimage,"drawable",context.packageName)
            dayofweek?.text = zemend.dayofweek
            habitname?.text = zemend.habitname
            cherrbt.visibility = View.VISIBLE
            habitstate.text = "완료"

            habitstate.setTextColor(Color.parseColor("#FFF612"))
            habitstate.setBackgroundColor(Color.parseColor("#FFFFE4"))
            image.setImageResource(imageId)
            date.visibility = View.GONE

            cherrbt.text = "칭찬하기"

            var id = zemend.id

            databaseconect(id!!)

            if(cherrbt.text == "칭찬함"){
                cherrbt.isEnabled = false
            }

            cherrbt.setOnClickListener {
                cherrbtOnclick(id!!)
            }
            itemView.setOnClickListener{itemClick(zemend)}
        }

        fun databaseconect(id: Long){
            dbe = ZemEndDB.getInstance(context)
            var r = Thread(
                Runnable {
                    Log.e("ID", id.toString())
                    zemEnd = dbe?.ZemEndDao()?.selectUserById(id!!)!!
                    if(zemEnd[0].zemcheck == 1){
                        cherrbt.text = "칭찬함"
                        Log.e("CHEER", "${zemEnd[0].id}활성화")
                    }
                }
            )
            r.start()
            r.join()
        }

        @SuppressLint("CutPasteId")
        fun cherrbtOnclick(id: Long){
            var view = LayoutInflater.from(context).inflate(R.layout.cheermess, null)
            var builder = AlertDialog.Builder(context)
            builder.setView(view)
            var alertDialog = builder.create()
            alertDialog.show()

            lateinit var mAdapter: ComplimentAdapter

            mAdapter = ComplimentAdapter(context, complimentlist)

            mAdapter.setItemClickListener(object : ComplimentAdapter.ItemClickListener{
                override fun onClick(view: View, compliment: ComplimentItem, mSelectedItem:Int) {
                    val complimentimage = view.findViewById<ImageFilterView>(R.id.complimentimage)
                    complimentimage.isSelected = complimentimage.isSelected != true
                }
            })

            view.findViewById<RecyclerView>(R.id.recyclerView).adapter = mAdapter
            view.findViewById<RecyclerView>(R.id.recyclerView).layoutManager = GridLayoutManager(context, 2)

            view.findViewById<Button>(R.id.cencle).setOnClickListener {
                alertDialog.dismiss()
            }
            view.findViewById<Button>(R.id.sendbt).setOnClickListener{
                var r = Thread(
                    Runnable {
                        dbe?.ZemEndDao()?.updateById(id)
                    }
                )
                r.start()
                r.join()
                val intent = (context as Activity).intent
                intent.putExtra("TABSTATE", 1)
                context.startActivity(intent) //현재 액티비티 재실행 실시
                context.overridePendingTransition(0, 0) //효과 없애기
            }
        }
    }

}