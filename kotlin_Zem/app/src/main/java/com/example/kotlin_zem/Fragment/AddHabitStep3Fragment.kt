package com.example.kotlin_zem.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.kotlin_zem.AddHabitActivity
import com.example.kotlin_zem.HabitInfoActivity
import com.example.kotlin_zem.HabitMainActivity
import com.example.kotlin_zem.R
import com.example.kotlin_zem.database.*
import com.example.kotlin_zem.databinding.FragmentAddHabitStep2Binding
import com.example.kotlin_zem.databinding.FragmentAddHabitStep3Binding
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*
import kotlin.collections.ArrayList
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddHabitStep3Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddHabitStep3Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAddHabitStep3Binding? = null
    private val binding get() = _binding!!
    private var db:ZemDB? = null
    var dbc: ZemCompletionDB? =null
    var dbed: ZemEditDB? = null
    var zemCompletionlist = listOf<ZemCompletion>()
    var day = 30
    val now = System.currentTimeMillis()
    val date = Date(now)
    val sdf= SimpleDateFormat("yyyy.MM.dd")
    var cal = Calendar.getInstance()
    val getTime = sdf.format(date)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddHabitStep3Binding.inflate(inflater,container,false)
        cal.add(MONTH,1)

        binding.firstperiod.text = getTime
        binding.secondperiod.text = sdf.format(cal.time)

        db = ZemDB.getInstance(requireContext())
        dbc = ZemCompletionDB.getInstance(requireContext())
        dbed = ZemEditDB.getInstance(requireContext())

        binding.btlayout.visibility = View.VISIBLE
        binding.ebtlayout.visibility = View.GONE

        var id = arguments?.getLong("ZEMID")
        if(id !==null){
            editHabit(id)
        }
        binding.addhabitstep3.setOnClickListener {
            binding.zemconbanner.visibility = View.GONE
        }

        binding.question.setOnClickListener {
            if(binding.zemconbanner.visibility == View.GONE ){
                binding.zemconbanner.visibility = View.VISIBLE
            }else {
                binding.zemconbanner.visibility = View.GONE
            }
        }

        setFragmentResultListener("habittype"){ habittype, bundle ->
            var str = bundle.getStringArrayList("bundleKey")!!

            show(str)

            binding.back.setOnClickListener {
                back(str)
            }

            val addRunnable = Runnable {
                val newZem = Zem()
                newZem.habittitle = binding.habittitle.text.toString()
                newZem.habitimage = str[2]
                newZem.habitname = binding.habitedit.text.toString()
                newZem.date = binding.firstperiod.text.toString()+"~"+binding.secondperiod.text.toString()
                newZem.dayofweek = binding.selectday.text.toString()
                newZem.alram = binding.alram.text.toString()
                newZem.zemcon = binding.zemconcount.text.toString()
                newZem.zemconinfo = binding.zemconinfo.text.toString()
                db?.ZemDao()?.insert(newZem)
            }
            binding.clear.setOnClickListener {
                val addThread = Thread(addRunnable)
                addThread.start()
                var data =  Intent(requireContext(), HabitMainActivity::class.java)
                data.putExtra("TABSTATE", 2)
                startActivity(data)
            }
        }

        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                timeselect()
            }else{
                binding.alram.setText("없음")
            }
        }

        binding.radio.setOnCheckedChangeListener{ radioGroup, i ->
            var str = binding.firstperiod.text.toString()
            sdf.parse(str)?.let { cal.time = it }
            when(i){
                binding.seven.id -> {
                    day = 7
                    cal.add(DATE,day-1)
                }
                binding.fourteen.id ->{
                    day = 14
                    cal.add(DATE,day-1)
                }
                binding.thirty.id ->{
                    day = 30
                    cal.add(DATE,day-1)
                }
                binding.oneHundred.id ->{
                    day = 100
                    cal.add(DATE,day-1)
                }
            }
            binding.secondperiod.text = sdf.format(cal.time)
            binding.zemconinfo.text = "빠짐없이 한다면 ${day}회 x ${binding.zemconcount.text}잼콘 = 최대 ${day*(binding.zemconcount.text.toString().toInt())}잼콘"
        }

        binding.alram.setOnClickListener {
            timeselect()
        }

        binding.zemconp.setOnClickListener {
            var zemcon: Int = binding.zemconcount.text.toString().toInt()+5
            Log.e("zemcon",zemcon.toString())
            if(zemcon >= 50){
                binding.zemconp.isEnabled = false
                val imageId = context?.resources?.getIdentifier("button_plus_lock","drawable",context?.packageName)
                binding.zemconp.setImageResource(imageId!!)
            }
            val imageId = context?.resources?.getIdentifier("minus","drawable",context?.packageName)
            binding.zemconm.setImageResource(imageId!!)
            binding.zemconm.isEnabled = true
            binding.zemconcount.text = zemcon.toString()
            binding.zemconinfo.text = "빠짐없이 한다면 ${day}회 x ${binding.zemconcount.text}잼콘 = 최대 ${(zemcon*day)}잼콘"
        }


        binding.zemconm.setOnClickListener {
            var zemcon: Int = binding.zemconcount.text.toString().toInt()-5
            if(zemcon < 50){
                binding.zemconp.isEnabled = true
                val imageId = context?.resources?.getIdentifier("button_plus","drawable",context?.packageName)
                binding.zemconp.setImageResource(imageId!!)
            }
            if (zemcon <= 0){
                binding.zemconm.isEnabled = false
                val imageId = context?.resources?.getIdentifier("minus_lock","drawable",context?.packageName)
                binding.zemconm.setImageResource(imageId!!)
            }
            binding.zemconcount.text = zemcon.toString()
            binding.zemconinfo.text = "빠짐없이 한다면 ${day}회 x ${binding.zemconcount.text}잼콘 = 최대 ${(zemcon*day)}잼콘"
        }
        binding.firstperiod.setOnClickListener {
            dateDialog1()
        }
        binding.secondperiod.setOnClickListener {
            dateDialog2()
        }

        binding.selectday.setOnClickListener {
            selectdateDailog()
        }
        binding.edit.setOnClickListener {
            //Dao update문 생성해서 전부 다 다시 업데이트
            var r = Thread(
                Runnable {
                    zemCompletionlist = dbc?.ZemCompletionDao()?.selectUserById(id!!)!!
                    Log.e("zemCom", zemCompletionlist.toString())
                    val newZemEdit = ZemEdit()
                    newZemEdit.name = id!!
                    newZemEdit.date = binding.firstperiod.text.toString()+"~"+binding.secondperiod.text.toString()
                    newZemEdit.dayofweek = binding.selectday.text.toString()
                    newZemEdit.alram = binding.alram.text.toString()
                    newZemEdit.zemcon = binding.zemconcount.text.toString()
                    newZemEdit.zemconinfo = binding.zemconinfo.text.toString()
                    dbed?.ZemEditDao()?.insert(newZemEdit)

                    val newZem = Zem()
                    newZem.id = zemCompletionlist[0].id
                    newZem.habittitle = zemCompletionlist[0].habittitle
                    newZem.habitimage = zemCompletionlist[0].habitimage
                    newZem.habitname = zemCompletionlist[0].habitname
                    newZem.date = zemCompletionlist[0].date
                    newZem.dayofweek = zemCompletionlist[0].dayofweek
                    newZem.alram = zemCompletionlist[0].alram
                    newZem.zemcon = zemCompletionlist[0].zemcon
                    newZem.zemconinfo = zemCompletionlist[0].zemconinfo
                    newZem.zemeditcheck = 1

                    Log.e("zemedit",dbed?.ZemEditDao()?.getALL().toString())

                    db?.ZemDao()?.insert(newZem)
                    dbc?.ZemCompletionDao()?.deleteUserById(id!!)
                }
            )
            r.start()
            r.join()

            var data =  Intent(requireContext(), HabitMainActivity::class.java)
            data.putExtra("TABSTATE", 2)
            startActivity(data)
        }

        return binding.root
    }



    fun editHabit(id: Long){
        binding.btlayout.visibility = View.GONE
        binding.ebtlayout.visibility = View.VISIBLE
        binding.habitedit.visibility = View.GONE
        binding.habitnametext.visibility = View.VISIBLE

        binding.back.setOnClickListener {
            cancelbt()
        }
        binding.ecancel.setOnClickListener {
            cancelbt()
        }

        var r = Thread(
            Runnable {
                zemCompletionlist = dbc?.ZemCompletionDao()?.selectUserById(id)!!

                var zemimage = zemCompletionlist[0].habitimage
                val imageId = this.resources?.getIdentifier(zemimage,"drawable",context?.packageName)
                binding.habitimage.setImageResource(imageId!!)
                binding.habittitle.text = zemCompletionlist[0].habittitle
                binding.habitnametext.setText(zemCompletionlist[0].habitname)
                binding.selectday.text = zemCompletionlist[0].dayofweek
                binding.alram.text = zemCompletionlist[0].alram
                binding.zemconcount.text = zemCompletionlist[0].zemcon
                binding.zemconinfo.text = zemCompletionlist[0].zemconinfo
                binding.firstperiod.text = zemCompletionlist[0].date.substring(0..9)
                Log.e("asdfds",zemCompletionlist[0].date.substring(11))
                binding.secondperiod.text = zemCompletionlist[0].date.substring(11)

                when(zemCompletionlist[0].zemconinfo.substring(8..11)){
                    " 7회 " -> binding.seven.isChecked = true
                    " 14회" -> binding.fourteen.isChecked = true
                    " 30회" -> binding.thirty.isChecked = true
                    " 100" -> binding.oneHundred.isChecked = true
                    else -> binding.thirty.isChecked = false
                }
                if(zemCompletionlist[0].alram == "없음"){
                    binding.switch1.setChecked(false)
                }else binding.switch1.setChecked(true)
            }
        )
        r.start()
        r.join()

    }

    fun cancelbt(){
        val cancelFragment = CancelFragment()
        cancelFragment.show(childFragmentManager, cancelFragment.tag)
        cancelFragment.setOnClickListener(object : CancelFragment.ButtonClickListener{
            override fun onClicked() {
                fragmentManager?.beginTransaction()?.remove(AddHabitStep3Fragment())?.commit()
                fragmentManager?.popBackStack()
            }
        })

    }


    fun timeselect(){
        val alramFragment = AlramFragment()
        alramFragment.show(childFragmentManager,alramFragment.tag)
        alramFragment.setOnClickListener(object: AlramFragment.ButtonClickListener{
            override fun onClicked(text: String) {
                binding.alram.setText(text)
                binding.switch1.isChecked = binding.alram.text != "없음"
            }
        })
    }

    fun selectdateDailog(){
        val dayofweekFragment = DayofweekFragment()
        dayofweekFragment.show(childFragmentManager, dayofweekFragment.tag)
        dayofweekFragment.setOnClickListener(object: DayofweekFragment.ButtonClickListener{
            override fun onClicked(text: ArrayList<String>) {
                binding.selectday.text = text.toString().replace("]","").replace("[","")
            }
        })
    }

    fun dateDialog1(){
        val time = binding.firstperiod.text.toString()
        Log.e("time",time)
        val fristFragment = FirstperiodFragment()
        val bundle = Bundle()
        bundle.putString("TIME",time)
        fristFragment.arguments = bundle
        fristFragment.show(childFragmentManager,fristFragment.tag)
        fristFragment.setOnClickListener(object: FirstperiodFragment.ButtonClickListener{
            override fun onClicked(text: String) {
                var startDate = sdf.parse(text).time
                var endDate = sdf.parse(binding.secondperiod.text.toString()).time
                if(sdf.parse(getTime).time > startDate){
                    Log.e("first", "더작음")
                }else{
                    binding.firstperiod.text = text
                    day = ((endDate - startDate) / (60*60*24*1000) + 1).toInt()
                    binding.zemconinfo.text = "빠짐없이 한다면 ${day}회 x ${binding.zemconcount.text}잼콘 = 최대 ${(binding.zemconcount.text.toString().toInt()*day)}잼콘"
                }
            }
        })
        dayuncheck()
    }
    fun dateDialog2(){
        val time = binding.secondperiod.text.toString()
        Log.e("time",time)
        val secondperiodFragment = SecondperiodFragment()
        val bundle = Bundle()
        bundle.putString("TIME",time)
        secondperiodFragment.arguments = bundle
        secondperiodFragment.show(childFragmentManager,secondperiodFragment.tag)
        secondperiodFragment.setOnClickListener(object: SecondperiodFragment.ButtonClickListener{
            override fun onClicked(text: String) {
                binding.secondperiod.text = text
                val dateFormat = SimpleDateFormat("yyyy.MM.dd")
                var startDate = dateFormat.parse(binding.firstperiod.text.toString()).time
                var endDate = dateFormat.parse(text).time


                day = ((endDate - startDate) / (60*60*24*1000) + 1).toInt()
                binding.zemconinfo.text = "빠짐없이 한다면 ${day}회 x ${binding.zemconcount.text}잼콘 = 최대 ${(binding.zemconcount.text.toString().toInt()*day)}잼콘"

            }
        })
        dayuncheck()
    }
    fun dayuncheck(){
        binding.seven.isChecked = false
        binding.fourteen.isChecked = false
        binding.thirty.isChecked = false
        binding.oneHundred.isChecked = false
    }

    fun back(str: ArrayList<String>){
        setFragmentResult("fragment2", bundleOf("bundleKey" to str))
        parentFragmentManager.beginTransaction()
            .replace(R.id.addhabitstepview, AddHabitStep2Fragment())
            .commit()
    }

    fun show(list: ArrayList<String>){
        Log.e("FRAGMENT", list.toString())
        val imageId = context?.resources?.getIdentifier(list[2],"drawable",context?.packageName)
        binding.habitimage.setImageResource(imageId!!)
        binding.habittitle.text =  list[0] +" "+ list[1]
        binding.habitedit.setText(list[3])
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddHabitStep3Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}