package com.example.kotlin_zem.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_zem.Adapter.HabitTypeAdapter
import com.example.kotlin_zem.R
import com.example.kotlin_zem.databinding.FragmentAddHabitStep2Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddHabitStep2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddHabitStep2Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAddHabitStep2Binding? = null
    private val binding get() = _binding!!
    private var typeList = listOf<String>()
    lateinit var mAdapter: HabitTypeAdapter
    private val fragmentStep3 = AddHabitStep3Fragment()
    var list = arrayListOf<String>()

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
        _binding = FragmentAddHabitStep2Binding.inflate(inflater,container,false)

        show()

        setFragmentResultListener("fragment2"){ habitInfo, bundle ->
            list = bundle.getStringArrayList("bundleKey")!!

            val imageId = context?.resources?.getIdentifier(list[2],"drawable",context?.packageName)
            binding.habitimage.setImageResource(imageId!!)
            binding.habittitle.text = list[0] +" "+ list[1]

            habittypelist(list)
        }

        binding.back.setOnClickListener {
            var step1Fragment = AddHabitStep1Fragment()

            parentFragmentManager.beginTransaction().remove(this).commit()
            parentFragmentManager.beginTransaction().replace(R.id.addhabitstepview, step1Fragment).commit()
        }

        return binding.root
    }

    fun show(){
        setFragmentResultListener("habitInfo"){ habitInfo, bundle ->
            list = bundle.getStringArrayList("bundleKey")!!

            val imageId = context?.resources?.getIdentifier(list[2],"drawable",context?.packageName)
            binding.habitimage.setImageResource(imageId!!)
            binding.habittitle.text = list[0] +" "+ list[1]

            habittypelist(list)
        }
    }

    fun habittypelist(list: ArrayList<String>){
        when(list[0]){
            "건강한" -> {
                typeList = listOf("줄넘기 100번하기","계단 오르기", "홈트레이닝 하기","철봉 매달리기", "우유 3컵 마시기", "편식 안하기", "간식 조절하기", "치실 하기", "기타")
            }
            "꾸준한" ->{
                typeList = listOf("한글책 읽기", "영어책 읽기","영어 단어 외우기", "수학 연산 풀기","학원 숙제 하기","공부 시간 지키기","기타")
            }
            "긍정적인" ->{
                typeList = listOf("주변 어른에게 인사 잘하기","친구와 고운말 쓰기","소리지르지 않기","식사 인사하기","문안 인사하기","기타")
            }
            "스스로" ->{
                typeList = listOf("일찍 자고 일찍 일어나기","스스로 등교 준비하기", "학교 5분 전에 도착하기","학원 5분전에 도착하기","쉬는 시간 지키기","기타")
            }
            "올바른" ->{
                typeList = listOf("게임 시간 지키기","동영상 시청시간 지키기","정해진 시간에 SNS 하기","전화 예절 지키기","가족끼리 전화 끊을때 사랑해 말하기","기타")
            }
            "부지런한" ->{
                typeList = listOf("나만의 집안일 정하기", "식사준비 돕기","다먹은 그릇 정리하기","책상 정리하기","이불 정돈하기","빨랫감 제자리 두기","외출복 걸어두기","애완동물 산책시키기", "기타")
            }
            "똑똑한" ->{
                typeList = listOf("알림장 준비물 챙기기","필통 챙기기","일기 쓰기", "예쁘게 글씨쓰기","기타")
            }
        }
        mAdapter = HabitTypeAdapter(requireContext(), typeList) { String ->
            var habitTypeList = arrayListOf<String>(list[0], list[1], list[2], String)
            setFragmentResult("habittype", bundleOf("bundleKey" to habitTypeList))
            parentFragmentManager.beginTransaction()
                .replace(R.id.addhabitstepview, fragmentStep3!!)
                .commit()
            }
        binding.habittypeReView.adapter = mAdapter
        binding.habittypeReView.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddHabitStep2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}