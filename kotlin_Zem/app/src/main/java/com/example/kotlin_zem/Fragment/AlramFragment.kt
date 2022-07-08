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
import com.example.kotlin_zem.databinding.FragmentAlramBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AlramFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlramFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAlramBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    interface ButtonClickListener{
        fun onClicked(text: String)
    }
    fun setOnClickListener(listener: ButtonClickListener){
        onClickListener = listener
    }
    private lateinit var onClickListener: ButtonClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlramBinding.inflate(inflater,container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        //순환 안되게설정
        binding.ampm.wrapSelectorWheel = false
        val formatter = NumberPicker.Formatter {
            when(it){
                0 -> return@Formatter "00"
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

        //최소값 설정
        binding.ampm.minValue = 0
        binding.hour.minValue = 1
        binding.minute.minValue = 0

        //최대값 설정
        binding.ampm.maxValue = 1
        binding.hour.maxValue = 12
        binding.minute.maxValue = 59

        binding.hour.setFormatter(formatter)
        binding.minute.setFormatter(formatter)

        binding.ampm.displayedValues = arrayOf("오전","오후")

        binding.cancel.setOnClickListener {
            onClickListener.onClicked("없음")
            dismiss()
        }
        binding.clear.setOnClickListener {
            var timestr =""
            when(binding.ampm.value){
                0 -> timestr += "오전 "
                1 -> timestr += "오후 "
            }
            timestr += binding.hour.value.toString() + "시 " + binding.minute.value + "분"
            onClickListener.onClicked(timestr)
            dismiss()
            Log.e("Tag",timestr)
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
         * @return A new instance of fragment AlramFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlramFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}