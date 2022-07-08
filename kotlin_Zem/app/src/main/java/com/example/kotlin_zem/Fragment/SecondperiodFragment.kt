package com.example.kotlin_zem.Fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.example.kotlin_zem.R
import com.example.kotlin_zem.databinding.FragmentFirstperiodBinding
import com.example.kotlin_zem.databinding.FragmentSecondperiodBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondperiodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondperiodFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentSecondperiodBinding? = null
    private val binding get() = _binding!!

    interface ButtonClickListener{
        fun onClicked(text: String)
    }
    fun setOnClickListener(listener: ButtonClickListener){
        onClickListener = listener
    }
    private lateinit var onClickListener: ButtonClickListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondperiodBinding.inflate(inflater,container,false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var time = arguments?.getString("TIME")

        val formatter = NumberPicker.Formatter {
            when(it){
                1 -> return@Formatter "01"
                2 -> return@Formatter "02"
                3 -> return@Formatter "03"
                4 -> return@Formatter "04"
                5 -> return@Formatter "05"
                6 -> return@Formatter "06"
                7 -> return@Formatter "07"
                8 -> return@Formatter "08"
                9 -> return@Formatter "09"
                else -> return@Formatter it.toString()
            }
        }

        //순환 안되게설정
        binding.years.wrapSelectorWheel = false

        //최소값 설정
        binding.years.minValue = 2022
        binding.month.minValue = 1
        binding.day.minValue = 1

        //최대값 설정
        binding.years.maxValue = 2024
        binding.month.maxValue = 12
        binding.day.maxValue = 31

        binding.month.setFormatter(formatter)
        binding.day.setFormatter(formatter)

        binding.years.value = time?.substring(0 until 4)!!.toInt()
        binding.month.value = time.substring(5 until 7).toInt()
        binding.day.value = time.substring(8 until 10).toInt()

        binding.cancel.setOnClickListener {
            dismiss()
        }

        binding.clear.setOnClickListener {
            var etime = binding.years.value.toString()+"."
            if(binding.month.value<10){
                etime += "0"+binding.month.value.toString()+"."
            }else etime += binding.month.value.toString() + "."
            if(binding.day.value<10){
                etime += "0" + binding.day.value.toString()
            }else etime += binding.day.value.toString()
            onClickListener.onClicked(etime)
            dismiss()
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondperiodFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondperiodFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}