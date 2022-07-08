package com.example.kotlin_zem.Fragment

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Dimension
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.kotlin_zem.databinding.FragmentDayofweekBinding
import com.google.android.material.snackbar.Snackbar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DayofweekFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DayofweekFragment: DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentDayofweekBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    interface ButtonClickListener{
        fun onClicked(text: ArrayList<String>)
    }
    fun setOnClickListener(listener: ButtonClickListener){
        onClickListener = listener
    }
    private lateinit var onClickListener: ButtonClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayofweekBinding.inflate(inflater,container,false)

        binding.monday.setOnClickListener {
            select(binding.monday)
        }
        binding.tuesday.setOnClickListener {
            select(binding.tuesday)
        }
        binding.wednesday.setOnClickListener {
            select(binding.wednesday)
        }
        binding.thursday.setOnClickListener {
            select(binding.thursday)
        }
        binding.friday.setOnClickListener {
            select(binding.friday)
        }
        binding.saturday.setOnClickListener {
            select(binding.saturday)
        }
        binding.sunday.setOnClickListener {
            select(binding.sunday)
        }
        binding.everyday.setOnClickListener {
            binding.monday.isChecked = false
            binding.tuesday.isChecked = false
            binding.wednesday.isChecked = false
            binding.thursday.isChecked = false
            binding.friday.isChecked = false
            binding.saturday.isChecked = false
            binding.sunday.isChecked = false
        }
        binding.clear.setOnClickListener {
            if(binding.monday.isChecked ||  binding.tuesday.isChecked || binding.wednesday.isChecked ||binding.thursday.isChecked ||binding.friday.isChecked ||
                binding.saturday.isChecked ||binding.sunday.isChecked ||binding.everyday.isChecked ){
                clear()
            }else {
               //Toast.makeText(requireContext(),"최소 하나의 요일을 선택하세요.",Toast.LENGTH_SHORT).show()
                toastm()
            }
        }

        binding.cancel.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    fun clear(){
        var day: ArrayList<String> = arrayListOf()
        if(binding.monday.isChecked){
            day.add("월")
        }
        if(binding.tuesday.isChecked){
            day.add("화")
        }
        if(binding.wednesday.isChecked){
            day.add("수")
        }
        if(binding.thursday.isChecked){
            day.add("목")
        }
        if(binding.friday.isChecked){
            day.add("금")
        }
        if(binding.saturday.isChecked){
            day.add("토")
        }
        if(binding.sunday.isChecked){
            day.add("일")
        }
        if(binding.everyday.isChecked || (binding.monday.isChecked &&  binding.tuesday.isChecked && binding.wednesday.isChecked &&
                    binding.thursday.isChecked &&binding.friday.isChecked && binding.saturday.isChecked &&binding.sunday.isChecked)){
            day = arrayListOf("매일")
        }
        Log.e("TAG", day.toString())

        onClickListener.onClicked(day)
        dismiss()
    }

    fun toastm(){
        var view = layoutInflater.inflate(com.example.kotlin_zem.R.layout.toast_border, null)
        var textView: TextView = view.findViewById(com.example.kotlin_zem.R.id.text)
        textView.setText("최소 하나의 요일을 선택하세요.")
        view.setBackgroundResource(android.R.drawable.toast_frame)

        var toast = Toast(requireContext())
        toast.view = view
        toast.show()

    }

    fun select(checkbox: CheckBox){
        binding.everyday.isChecked = false
        var check = checkbox.isChecked
        when(checkbox){
            binding.monday ->{
                if(check){
                    binding.monday.setTextSize(Dimension.SP, 19F)
                } else binding.monday.setTextSize(Dimension.SP, 16F)
            }
            binding.tuesday ->{
                if(check){
                    binding.tuesday.setTextSize(Dimension.SP, 19F)
                } else binding.tuesday.setTextSize(Dimension.SP, 16F)
            }
            binding.wednesday ->{
                if(check){
                    binding.wednesday.setTextSize(Dimension.SP, 19F)
                } else binding.wednesday.setTextSize(Dimension.SP, 16F)
            }
            binding.thursday ->{
                if(check){
                    binding.thursday.setTextSize(Dimension.SP, 19F)
                } else binding.thursday.setTextSize(Dimension.SP, 16F)
            }
            binding.friday ->{
                if(check){
                    binding.friday.setTextSize(Dimension.SP, 19F)
                } else binding.friday.setTextSize(Dimension.SP, 16F)
            }
            binding.saturday ->{
                if(check){
                    binding.saturday.setTextSize(Dimension.SP, 19F)
                } else binding.saturday.setTextSize(Dimension.SP, 16F)
            }
            binding.sunday ->{
                if(check){
                    binding.sunday.setTextSize(Dimension.SP, 19F)
                } else binding.sunday.setTextSize(Dimension.SP, 16F)
            }
        }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DayofweekFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DayofweekFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}