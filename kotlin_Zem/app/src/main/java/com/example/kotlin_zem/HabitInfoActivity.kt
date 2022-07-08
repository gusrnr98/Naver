package com.example.kotlin_zem

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import com.example.kotlin_zem.Adapter.ZemCompletionAdapter
import com.example.kotlin_zem.Fragment.AddHabitStep3Fragment
import com.example.kotlin_zem.Fragment.IngFragment
import com.example.kotlin_zem.Fragment.ZemconFragment
import com.example.kotlin_zem.database.*
import com.example.kotlin_zem.databinding.ActivityHabitInfoBinding
import java.text.SimpleDateFormat

class HabitInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHabitInfoBinding
    var db: ZemDB? =null
    var dbc: ZemCompletionDB? =null
    var dbed: ZemEditDB? = null
    var dbend: ZemEndDB? =null
    private lateinit var zemimage:String
    private var zemlist = listOf<Zem>()
    private var zemendlist = listOf<ZemEnd>()
    private var zemelist = listOf<ZemEdit>()
    private var zemCompletionlist = listOf<ZemCompletion>()
    private val fragmentManager = supportFragmentManager
    private val transaction: FragmentTransaction = fragmentManager.beginTransaction()
    private val fragmentStep3 = AddHabitStep3Fragment()
    val sdf= SimpleDateFormat("yyyy.MM.dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHabitInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.infolayout.setOnClickListener {
            binding.zemconbanner.visibility = View.GONE
        }

        binding.zemcontext.setOnClickListener {
            binding.zemconbanner.visibility = View.VISIBLE
        }

        val dataId = intent.getLongExtra("ID",0)
        val getId = intent.getStringExtra("PUT")
        when(getId){
            "WAIT" -> {
                binding.editDelete.visibility = View.GONE
                binding.buttonlayout.visibility = View.VISIBLE
                binding.finish.visibility = View.GONE
                binding.habitstate.text ="등록 대기"
                binding.habitstate.setTextColor(Color.parseColor("#5CD1E5"))
                binding.habitstate.setBackgroundColor(Color.parseColor("#E8FFFF"))
            }
            "ING" ->{
                binding.editDelete.visibility = View.VISIBLE
                binding.buttonlayout.visibility = View.GONE
                binding.finish.visibility = View.VISIBLE
                binding.habitstate.text ="진행중"
                binding.habitstate.setTextColor(Color.parseColor("#86E57F"))
                binding.habitstate.setBackgroundColor(Color.parseColor("#F2FFEB"))
            }
            "END" ->{
                binding.editDelete.visibility = View.VISIBLE
                binding.edit.visibility = View.GONE
                binding.buttonlayout.visibility = View.GONE
                binding.finish.visibility = View.GONE
                binding.habitstate.text ="완료"
                binding.habitstate.setTextColor(Color.parseColor("#86E57F"))
                binding.habitstate.setBackgroundColor(Color.parseColor("#F2FFEB"))
            }
        }

        val tabstate: (String) -> Int = {
            when(it){ //리턴을 해줘야하기때문에 else필요
                "완료 요청","진행중" -> 0
                "완료" -> 1
                "등록 대기","수정 요청" -> 2
                else -> 3
            }
        }
        db = ZemDB.getInstance(this)
        dbc = ZemCompletionDB.getInstance(this)
        dbed = ZemEditDB.getInstance(this)
        dbend = ZemEndDB.getInstance(this)


        infoShow(dataId, getId!!)

        binding.back.setOnClickListener {
            var data =  Intent(this, HabitMainActivity::class.java)
            data.putExtra("TABSTATE", tabstate(binding.habitstate.text.toString()))
            startActivity(data)
        }
        binding.cancel.setOnClickListener {
            zemlistdelete(dataId, getId)
            var data =  Intent(this, HabitMainActivity::class.java)
            data.putExtra("TABSTATE", tabstate(binding.habitstate.text.toString()))
            startActivity(data)
        }

        binding.clear.setOnClickListener {
            addhabit(dataId, getId)
        }
        binding.delete.setOnClickListener {
            zemlistdelete(dataId, getId)
            var data =  Intent(this, HabitMainActivity::class.java)
            data.putExtra("TABSTATE", tabstate(binding.habitstate.text.toString()))
            startActivity(data)
        }
        binding.edit.setOnClickListener {
            binding.finish.visibility = View.GONE
            var bundle = Bundle()
            bundle.putLong("ZEMID",dataId)
            fragmentStep3.arguments = bundle
            transaction.replace(R.id.infolayout, fragmentStep3).addToBackStack(null).commit()
        }
        binding.finish.setOnClickListener{
            zemupadate(dataId)
            var data =  Intent(this, HabitMainActivity::class.java)
            data.putExtra("TABSTATE", tabstate(binding.habitstate.text.toString()))
            startActivity(data)
        }
    }
    fun zemupadate(id: Long){
        var r = Thread(
            Runnable {
                dbc?.ZemCompletionDao()?.updateById(id)
            }
        )
        r.start()
        r.join()
    }

    fun addhabit(dataId: Long, getId: String){
        var r = Thread(
            Runnable {
                when(getId){
                    "WAIT" ->{
                        val newZemc = ZemCompletion()
                        newZemc.dayofweek = binding.habitdayofweek.text.toString()
                        newZemc.date = binding.habitdate.text.toString()
                        newZemc.habittitle = binding.habittitle.text.toString()
                        newZemc.habitimage = zemimage
                        newZemc.habitname = binding.habitname.text.toString()
                        newZemc.alram = binding.habitalram.text.toString()
                        newZemc.zemcon = binding.zemconcount.text.toString()
                        newZemc.zemconinfo = binding.zemconinfo.text.toString()

                        dbc?.ZemCompletionDao()?.insert(newZemc)
                    }
                }
            }
        )
        r.start()


        var data =  Intent(this, HabitMainActivity::class.java)
        data.putExtra("TABSTATE", 0)
        Log.e("asdasd","11111111")
        startActivity(data)
        zemlistdelete(dataId, getId)
    }

    fun infoShow(id: Long, getId: String){
        var r = Thread(
            Runnable {
                when(getId){
                    "WAIT" -> {
                        zemlist = db?.ZemDao()?.selectUserById(id)!!
                        zemimage = zemlist[0].habitimage
                        val imageId = this.resources?.getIdentifier(zemlist[0].habitimage,"drawable",this.packageName)
                        binding.habitimage.setImageResource(imageId!!)
                        binding.habittitle.text = zemlist[0].habittitle
                        binding.habitname.text = zemlist[0].habitname
                        binding.habitdayofweek.text = zemlist[0].dayofweek
                        binding.habitalram.text = zemlist[0].alram
                        binding.zemconcount.text = zemlist[0].zemcon
                        binding.zemconinfo.text = zemlist[0].zemconinfo
                        binding.habitdate.text = zemlist[0].date

                        if(zemlist[0].zemeditcheck == 1){
                            binding.editbuttonlayout.visibility = View.VISIBLE
                            binding.buttonlayout.visibility = View.GONE
                            binding.habitstate.text = "수정 요청"
                            binding.habitstate.setTextColor(Color.parseColor("#D941C5"))
                            binding.habitstate.setBackgroundColor(Color.parseColor("#FAEBFF"))

                            zemelist = dbed?.ZemEditDao()?.selectUserById(zemlist[0].id!!)!!
                            if(zemlist[0].date != zemelist[0].date){
                                binding.habitedate.visibility = View.VISIBLE
                                binding.habitedate.text = zemelist[0].date
                                binding.habitdate.setTextColor(Color.parseColor("#BDBDBD"))
                                binding.habitdate.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                            }else binding.habitedate.text = zemlist[0].date

                            if (zemlist[0].dayofweek != zemelist[0].dayofweek) {
                                binding.habitedayofweek.visibility = View.VISIBLE
                                binding.habitedayofweek.text = zemelist[0].dayofweek
                                binding.habitdayofweek.setTextColor(Color.parseColor("#BDBDBD"))
                                binding.habitdayofweek.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                            } else {
                                binding.habitedayofweek.text = zemlist[0].dayofweek
                            }

                            if(zemlist[0].alram != zemelist[0].alram){
                                binding.habitealram.visibility = View.VISIBLE
                                binding.habitealram.text = zemelist[0].alram
                                binding.habitalram.setTextColor(Color.parseColor("#BDBDBD"))
                                binding.habitalram.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                            }else binding.habitealram.text = zemlist[0].alram

                            if(zemlist[0].zemcon != zemelist[0].zemcon){
                                binding.ezemconcount.visibility = View.VISIBLE
                                binding.ezemconcount.text = zemelist[0].zemcon
                                binding.zemconcount.setTextColor(Color.parseColor("#BDBDBD"))
                                binding.zemconcount.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                            }else binding.ezemconcount.text = zemlist[0].zemcon

                            var date = binding.habitedate.text.toString().substring(0..9)
                            var startDate = sdf.parse(date).time
                            date = binding.habitedate.text.toString().substring(11)
                            var endDate = sdf.parse(date).time

                            var day = ((endDate - startDate) / (60*60*24*1000) + 1).toInt()
                            binding.zemconinfo.text = "빠짐없이 한다면 ${day}회 x ${binding.ezemconcount.text}잼콘 = 최대 ${(binding.ezemconcount.text.toString().toInt()*day)}잼콘"


                            binding.editclear.setOnClickListener {
                                addehabit(zemlist[0].id!!,"EDIT")
                            }
                            binding.editcancel.setOnClickListener {
                                addehabit(zemlist[0].id!!, "NOEDIT")
                            }
                        }
                    }
                    "ING" -> {
                        zemCompletionlist = dbc?.ZemCompletionDao()?.selectUserById(id)!!
                        Log.e("zemCom",zemCompletionlist[0].toString())
                        zemimage = zemCompletionlist[0].habitimage
                        val imageId = this.resources?.getIdentifier(zemimage,"drawable",this.packageName)
                        binding.habitimage.setImageResource(imageId!!)
                        binding.habittitle.text = zemCompletionlist[0].habittitle
                        binding.habitname.text = zemCompletionlist[0].habitname
                        binding.habitdate.text = zemCompletionlist[0].date
                        binding.habitdayofweek.text = zemCompletionlist[0].dayofweek
                        binding.habitalram.text = zemCompletionlist[0].alram
                        binding.zemconcount.text = zemCompletionlist[0].zemcon
                        binding.zemconinfo.text = zemCompletionlist[0].zemconinfo


                        if(zemCompletionlist[0].zemcheck == 1) {
                            binding.finish.visibility = View.GONE
                            binding.habitstate.text = "완료 요청"
                            binding.editDelete.visibility = View.GONE
                            binding.habitstate.setTextColor(Color.parseColor("#1266FF"))
                            binding.habitstate.setBackgroundColor(Color.parseColor("#F9F9F9"))
                        }
                    }
                    "END" ->{
                        zemendlist = dbend?.ZemEndDao()?.selectUserById(id)!!
                        zemimage = zemendlist[0].habitimage
                        val imageId = this.resources?.getIdentifier(zemimage,"drawable",this.packageName)
                        binding.habitimage.setImageResource(imageId!!)
                        binding.habittitle.text =zemendlist[0].habittitle
                        binding.habitname.text = zemendlist[0].habitname
                        binding.habitdate.text = zemendlist[0].date
                        binding.habitdayofweek.text = zemendlist[0].dayofweek
                        binding.habitalram.text = zemendlist[0].alram
                        binding.zemconcount.text = zemendlist[0].zemcon
                        binding.zemconinfo.text = zemendlist[0].zemconinfo

                    }
                }
                Log.e("TAG",zemlist.toString())
            }
        )
        r.start()
        r.join()
    }
    fun addehabit(dataId: Long, getId: String){
        var r = Thread(
            Runnable {
                when(getId){
                    "EDIT" ->{
                        zemlist = db?.ZemDao()?.selectUserById(dataId)!!
                        val newZemc = ZemCompletion()
                        newZemc.dayofweek = binding.habitedayofweek.text.toString()
                        newZemc.date = binding.habitedate.text.toString()
                        newZemc.habittitle = binding.habittitle.text.toString()
                        newZemc.habitimage = zemlist[0].habitimage
                        newZemc.habitname = binding.habitname.text.toString()
                        newZemc.alram = binding.habitealram.text.toString()
                        newZemc.zemcon = binding.ezemconcount.text.toString()
                        newZemc.zemconinfo = binding.zemconinfo.text.toString()

                        dbc?.ZemCompletionDao()?.insert(newZemc)

                        Log.e("EDIT",dbed?.ZemEditDao()?.getALL().toString())

                    }
                    "NOEDIT" ->{
                        zemlist = db?.ZemDao()?.selectUserById(dataId)!!
                        val newZemc = ZemCompletion()
                        newZemc.dayofweek = binding.habitedayofweek.text.toString()
                        newZemc.date = binding.habitedate.text.toString()
                        newZemc.habittitle = binding.habittitle.text.toString()
                        newZemc.habitimage = zemlist[0].habitimage
                        newZemc.habitname = binding.habitname.text.toString()
                        newZemc.alram = binding.habitealram.text.toString()
                        newZemc.zemcon = binding.ezemconcount.text.toString()
                        newZemc.zemconinfo = binding.zemconinfo.text.toString()

                        dbc?.ZemCompletionDao()?.insert(newZemc)
                        Log.e("EDIT",zemlist.toString())
                    }
                }
            }
        )
        r.start()
        var intent = Intent( this, HabitMainActivity::class.java)
        startActivity(intent)

        zemlistdelete(dataId, getId)
    }

    fun zemlistdelete(id: Long, getId: String){
        var r = Thread(
            Runnable {
                when(getId){
                    "WAIT" -> {
                        db?.ZemDao()?.deleteUserById(id)!!
                    }
                    "ING" ->{
                        dbc?.ZemCompletionDao()?.deleteUserById(id)!!
                    }
                    "END" ->{
                        dbend?.ZemEndDao()?.deleteUserById(id)!!
                    }
                    "NOEDIT"->{
                        db?.ZemDao()?.deleteUserById(id)!!
                    }
                    "EDIT"->{
                        db?.ZemDao()?.deleteUserById(id)!!
                    }
                }
            }
        )
        r.start()
        r.join()
    }
}