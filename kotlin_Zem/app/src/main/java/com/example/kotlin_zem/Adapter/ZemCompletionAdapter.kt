package com.example.kotlin_zem.Adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.GLES30
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_zem.DataClass.ComplimentItem
import com.example.kotlin_zem.Fragment.ApprovalFragment
import com.example.kotlin_zem.Fragment.IngFragment
import com.example.kotlin_zem.HabitMainActivity
import com.example.kotlin_zem.R
import com.example.kotlin_zem.database.*
import java.text.SimpleDateFormat
import java.util.*


class ZemCompletionAdapter(val context: Context, val zem:List<ZemCompletion>, val itemClick: (ZemCompletion) -> Unit):
    RecyclerView.Adapter<ZemCompletionAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.habit_wait_item, parent, false)
        return Holder(view,itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(zem[position])
    }

    override fun getItemCount() = zem.size

    inner class Holder(itemView: View, itemClick: (ZemCompletion) -> Unit): RecyclerView.ViewHolder(itemView) {
        val dayofweek = itemView.findViewById<TextView>(R.id.dayofweek)
        val habitname  = itemView.findViewById<TextView>(R.id.habitname)
        val image = itemView.findViewById<ImageView>(R.id.habitimage)
        var habitstate = itemView.findViewById<TextView>(R.id.habitstate)
        val date = itemView.findViewById<TextView>(R.id.date)
        val cheerbt = itemView.findViewById<Button>(R.id.cheerbt)
        val a = itemView.findViewById<ConstraintLayout>(R.id.habit)
        val now = System.currentTimeMillis()
        val datenow = Date(now)
        val sdf= SimpleDateFormat("yyyy.MM.dd")
        val getTime = sdf.format(datenow)
        var dbc: ZemCompletionDB? =null
        var dbe: ZemEndDB? =null
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

        private val cheerList = arrayListOf<ComplimentItem>(
            ComplimentItem("star"),
            ComplimentItem("medal"),
            ComplimentItem("mountain"),
            ComplimentItem("excited"),
            ComplimentItem("doit"),
            ComplimentItem("decoration"),
            ComplimentItem("gift"),
            ComplimentItem("icezemcon"),
            ComplimentItem("doing"),
            ComplimentItem("fire"),
            ComplimentItem("lightning"),
            ComplimentItem("cabbage"),
            ComplimentItem("proud")
        )
        private var zemCompletionlist = listOf<ZemCompletion>()

        init {
            lateinit var mAdapter: ComplimentAdapter
            Log.e("asdasdasd",cheerList.toString())
            cheerbt.setOnClickListener {
                if(cheerbt.text == "승인하기"){
                    var id = zem[position].id
                    var view = LayoutInflater.from(context).inflate(R.layout.fragment_approval, null)
                    var builder = AlertDialog.Builder(context)
                    builder.setView(view)
                    var alertDialog = builder.create()
                    alertDialog.show()


                    mAdapter = ComplimentAdapter(context, complimentlist)


                    dbc = ZemCompletionDB.getInstance(context)
                    dbe = ZemEndDB.getInstance(context)
                    val newZemc = ZemEnd()
                    var r = Thread(
                        Runnable {
                            zemCompletionlist = dbc?.ZemCompletionDao()?.selectUserById(id!!)!!

                            newZemc.habittitle = zemCompletionlist[0].habittitle
                            newZemc.habitimage = zemCompletionlist[0].habitimage
                            newZemc.habitname = zemCompletionlist[0].habitname
                            newZemc.date = zemCompletionlist[0].date
                            newZemc.dayofweek = zemCompletionlist[0].dayofweek
                            newZemc.alram = zemCompletionlist[0].alram
                            newZemc.zemcon = zemCompletionlist[0].zemcon
                            newZemc.zemconinfo = zemCompletionlist[0].zemconinfo
                        }
                    )
                    r.start()
                    r.join()

                    mAdapter.setItemClickListener(object : ComplimentAdapter.ItemClickListener{
                        override fun onClick(view: View, compliment: ComplimentItem, mSelectedItem: Int) {
                                if(mSelectedItem == -1){
                                    newZemc.zemcheck = 0
                                    Log.e("newZemc.zemcheck","0")
                                }else {
                                    newZemc.zemcheck = 1
                                    Log.e("newZemc.zemcheck1","1")
                                }
                        }
                    })

                    view.findViewById<RecyclerView>(R.id.recyclerView).adapter = mAdapter
                    view.findViewById<RecyclerView>(R.id.recyclerView).layoutManager = GridLayoutManager(context, 2)
                    view.findViewById<TextView>(R.id.haitname).text = "'"+zemCompletionlist[0].habitname+"'"

                    view.findViewById<Button>(R.id.clear).setOnClickListener {
                        var r = Thread(
                            Runnable {

                                dbe?.ZemEndDao()?.insert(newZemc)
                                dbc?.ZemCompletionDao()?.deleteUserById(id!!)
                                Log.e("ing->end",dbe?.ZemEndDao()?.getALL().toString())
                            }
                        )
                        r.start()
                        r.join()

                        val intent = (context as Activity).intent
                        intent.putExtra("TABSTATE", 1)
                        context.startActivity(intent) //현재 액티비티 재실행 실시
                        context.overridePendingTransition(0, 0) //효과 없애기
                    }

                    view.findViewById<Button>(R.id.cancel).setOnClickListener {
                        alertDialog.dismiss()
                    }
                }
                else if(cheerbt.text == "응원하기"){
                    var view = LayoutInflater.from(context).inflate(R.layout.cheermess, null)
                    var builder = AlertDialog.Builder(context)
                    builder.setView(view)
                    var alertDialog = builder.create()
                    alertDialog.show()

                    mAdapter = ComplimentAdapter(context, cheerList)

                    mAdapter.setItemClickListener(object : ComplimentAdapter.ItemClickListener{
                        override fun onClick(view: View, compliment: ComplimentItem, mSelectedItem:Int) {
                            val complimentimage = view.findViewById<ImageFilterView>(R.id.complimentimage)
                            complimentimage.isSelected = complimentimage.isSelected != true
                        }
                    })

                    view.findViewById<RecyclerView>(R.id.recyclerView).adapter = mAdapter
                    view.findViewById<RecyclerView>(R.id.recyclerView).layoutManager = GridLayoutManager(context, 2)

                    view.findViewById<TextView>(R.id.zemconapproval).text = "응원 메시지 보내기"

                    view.findViewById<Button>(R.id.cencle).setOnClickListener {
                        alertDialog.dismiss()
                    }
                    view.findViewById<Button>(R.id.sendbt).setOnClickListener{

                        cheerbt.text = "응원함"
                        val intent = (context as Activity).intent
                        intent.putExtra("TABSTATE", 0)
                        context.startActivity(intent) //현재 액티비티 재실행 실시
                        context.overridePendingTransition(0, 0) //효과 없애기
                    }
                    
                }
            }
        }

        fun bind(zem:ZemCompletion){
            date.visibility = View.VISIBLE
            val imageId = context.resources.getIdentifier(zem.habitimage,"drawable",context.packageName)
            if(zem.zemcheck == 1){
                a.setBackgroundResource(R.drawable.habit_item_bg_c)
                habitstate.text = "완료 요청"
                habitstate.setTextColor(Color.parseColor("#1266FF"))
                habitstate.setBackgroundColor(Color.parseColor("#F9F9F9"))
                cheerbt.setBackgroundResource(R.drawable.habit_item_bt_c)
                cheerbt.setTextColor(Color.parseColor("#FFFFFF"))
                cheerbt.text = "승인하기"
                date.visibility = View.GONE
            }
            else{
                a.setBackgroundResource(R.drawable.habit_item_bg)
                habitstate.setTextColor(Color.parseColor("#86E57F"))
                habitstate.setBackgroundColor(Color.parseColor("#F2FFEB"))
                cheerbt.setBackgroundColor(R.drawable.habit_item_bt)
                cheerbt.setBackgroundResource(R.drawable.habit_item_bt)
                cheerbt.setTextColor(Color.parseColor("#000000"))
                habitstate.text = "진행 중"
                cheerbt.text = "응원하기"
            }
            date.text = getTime
            dayofweek.text = zem.dayofweek
            habitname.text = zem.habitname
            image.setImageResource(imageId)
            itemView.setOnClickListener{itemClick(zem)}
        }
    }

}