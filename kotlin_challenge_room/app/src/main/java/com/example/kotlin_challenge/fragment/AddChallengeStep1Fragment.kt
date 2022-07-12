package com.example.kotlin_challenge.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResult
import com.example.kotlin_challenge.AddChallengeActivity
import com.example.kotlin_challenge.MainActivity
import com.example.kotlin_challenge.R
import com.example.kotlin_challenge.databinding.FragmentAddChallengeStep1Binding
import com.example.kotlin_challenge.databinding.FragmentChallengeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddChallengeStep1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddChallengeStep1Fragment : Fragment() {
    private var _binding: FragmentAddChallengeStep1Binding? = null
    private val binding get() = _binding!!
    private var param1: String? = null
    private var param2: String? = null
    private val addChallengeStep2Fragment: Fragment = AddChallengeStep2Fragment()
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
        _binding = FragmentAddChallengeStep1Binding.inflate(inflater, container, false)
        imageClip()

        binding.backBt.setOnClickListener {
            (activity as AddChallengeActivity).mainActivity()
        }
        binding.exerciseImageView.setOnClickListener{
            challengeSelect("exercise")
        }
        binding.habitImageView.setOnClickListener{
            challengeSelect("habit")
        }
        return binding.root
    }

    fun challengeSelect(select: String){
        when(select){
            "exercise"-> {
                var str = "운동"
                Log.e("select",select)
                setFragmentResult("select", bundleOf("bundleKey" to str))
                parentFragmentManager.beginTransaction()
                    .replace(R.id.addChallengeLayout, addChallengeStep2Fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()

                setFragmentResult("selector", bundleOf("bundleKeys" to str))
                    parentFragmentManager.beginTransaction()
                    .add(R.id.addChallengeLayout, addChallengeStep3Fragment)
            }
            "habit" -> {
                var str = "생활습관"
                Log.e("select",select)
                setFragmentResult("select", bundleOf("bundleKey" to str))
                parentFragmentManager.beginTransaction()
                    .replace(R.id.addChallengeLayout, addChallengeStep2Fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                setFragmentResult("selector", bundleOf("bundleKeys" to str))
                parentFragmentManager.beginTransaction()
                    .add(R.id.addChallengeLayout, addChallengeStep3Fragment)
            }
        }
    }

    fun imageClip(){
        binding.exerciseImageView.clipToOutline = true
        binding.companyImageView.clipToOutline = true
        binding.hobbyImageView.clipToOutline = true
        binding.habitImageView.clipToOutline = true
        binding.learnImageView.clipToOutline = true
        binding.timeImageView.clipToOutline = true
        binding.readImageView.clipToOutline = true
        binding.mManagementImageView.clipToOutline = true

    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddChallengeStep1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}