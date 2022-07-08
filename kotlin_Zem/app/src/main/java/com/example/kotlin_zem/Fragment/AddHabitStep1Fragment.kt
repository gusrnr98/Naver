package com.example.kotlin_zem.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlin_zem.Adapter.HabitAdapter
import com.example.kotlin_zem.DataClass.HabitItem
import com.example.kotlin_zem.HabitMainActivity
import com.example.kotlin_zem.R
import com.example.kotlin_zem.databinding.FragmentAddHabitStep1Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddHabitStep1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddHabitStep1Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAddHabitStep1Binding? = null
    private val binding get() = _binding!!
    private val fragmentStep2 = AddHabitStep2Fragment()
    private var habitList = arrayListOf<HabitItem>(
        HabitItem("건강한","몸","zemh"),
        HabitItem("꾸준한","공부","zempen"),
        HabitItem("긍정적인","언어 사용","zemlanguage"),
        HabitItem("스스로","시간 관리","zemtime"),
        HabitItem("올바른","스마트폰 사용","zemphone"),
        HabitItem("부지런한","집안 생활","zemhome"),
        HabitItem("똑똑한","학교 생할","zemschool"),
        HabitItem("","직접입력","zemetc")
    )
    lateinit var mAdapter: HabitAdapter
    private val fragmentStep3 = AddHabitStep3Fragment()

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
        // Inflate the layout for this fragment
        _binding = FragmentAddHabitStep1Binding.inflate(inflater,container,false)

        binding.back.setOnClickListener {
            var data =  Intent(requireContext(), HabitMainActivity::class.java)
            startActivity(data)
        }
        mAdapter = HabitAdapter(requireContext(), habitList) { habitItem ->
            var list = arrayListOf<String>()
            list.add(habitItem.habitsubtitle)
            list.add(habitItem.habittitle)
            list.add(habitItem.habitimage)

            if (habitItem.habittitle == "직접입력") {
                var habitTypeList =
                    arrayListOf<String>("", habitItem.habittitle, habitItem.habitimage, "어떤 습관인가요?")
                setFragmentResult("habittype", bundleOf("bundleKey" to habitTypeList))
                parentFragmentManager.beginTransaction()
                    .replace(R.id.addhabitstepview, fragmentStep3!!)
                    .commit()
            } else {
                setFragmentResult("habitInfo", bundleOf("bundleKey" to list))
                parentFragmentManager.beginTransaction()
                    .replace(R.id.addhabitstepview, fragmentStep2)
                    .commit()
                Log.e("FRAGMENTSTEP1-2", list.toString())

            }
        }
        binding.habitRecyclerView.adapter = mAdapter
        binding.habitRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddHabitStep1Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddHabitStep1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}