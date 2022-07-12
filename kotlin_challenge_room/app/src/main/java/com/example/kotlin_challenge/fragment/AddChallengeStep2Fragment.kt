package com.example.kotlin_challenge.fragment

import android.content.Intent
import android.graphics.Color
import android.icu.text.Transliterator
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
import com.example.kotlin_challenge.AddChallengeActivity
import com.example.kotlin_challenge.MainActivity
import com.example.kotlin_challenge.R
import com.example.kotlin_challenge.databinding.FragmentAddChallengeStep2Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddChallengeStep2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddChallengeStep2Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var result: String? = null
    private var _binding: FragmentAddChallengeStep2Binding? = null
    private val binding get() = _binding!!
    private val addChallengeStep3Fragment: Fragment = AddChallengeStep3Fragment()
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
        _binding=FragmentAddChallengeStep2Binding.inflate(inflater, container, false)

        binding.backBt.setOnClickListener {
            bottomDialog()
        }

        binding.backFragment.setOnClickListener {
            val intent = Intent(getActivity(), AddChallengeActivity::class.java)
            startActivity(intent)
        }

        selectResult()

        binding.select1.setOnClickListener {
            selectmenu(1)
        }
        binding.select2.setOnClickListener {
            selectmenu(2)
        }
        return binding.root
    }

    fun bottomDialog(){
        val bottomDialog = BottomDialogFragment()
        bottomDialog.show(childFragmentManager, bottomDialog.tag)
    }

    fun selectResult(){
        setFragmentResultListener("select"){ select, bundle ->
            result = bundle.getString("bundleKey")
            Log.e("fragmentStep2", result!!)
            when(result){
                "운동" -> {
                    binding.addChallengeStep2.setBackgroundColor(Color.parseColor("#877DE4"))
                    binding.selectChallenge.text = result
                }
                "생활습관" -> {
                    binding.addChallengeStep2.setBackgroundColor(Color.parseColor("#E2727A"))
                    binding.selectChallenge.text = result
                    binding.challengeImage.setImageResource(R.drawable.habit_person)
                    binding.select1.text = "하루 계획 세우고 완료하기"
                    binding.select2.text = "산책하기"
                    binding.select3.text = "일찍 일어나기"
                }
            }
        }
    }

    fun selectmenu(position: Int){
        var str: String
        when(position){
            1 -> {
                str = binding.select1.text.toString()
                setFragmentResult("position", bundleOf("bundleKey" to str))
                parentFragmentManager.beginTransaction()
                    .replace(R.id.addChallengeLayout, addChallengeStep3Fragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            2 -> {
                str = binding.select2.text.toString()
                setFragmentResult("position", bundleOf("bundleKey" to str))
                parentFragmentManager.beginTransaction()
                    .replace(R.id.addChallengeLayout, addChallengeStep3Fragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
        }
    }




    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddChallengeStep2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}