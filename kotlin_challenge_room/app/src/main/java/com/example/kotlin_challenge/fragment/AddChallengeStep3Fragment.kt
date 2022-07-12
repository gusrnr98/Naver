package com.example.kotlin_challenge.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.room.Room
import com.example.kotlin_challenge.AddChallengeActivity
import com.example.kotlin_challenge.R
import com.example.kotlin_challenge.database.Challenge
import com.example.kotlin_challenge.database.ChallengeDB
import com.example.kotlin_challenge.databinding.FragmentAddChallengeStep3Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddChallengeStep3Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddChallengeStep3Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAddChallengeStep3Binding? = null
    private val binding get() = _binding!!
    private var result: String? = null
    private var selectchallenge: String? = null
    private lateinit var Rresult:String
    private var db:ChallengeDB? = null
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
        _binding = FragmentAddChallengeStep3Binding.inflate(inflater, container, false)
        selectChallenge()

        binding.backBtStep3.setOnClickListener {
            bottomDialog()
        }
        Log.e("step3","createview")

        db = ChallengeDB.getInstance(requireContext())
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            Log.e("Id", checkedId.toString())
            when(checkedId){
                binding.dateRBt1.id ->  Rresult = "매일"
                binding.dateRBt2.id ->  Rresult = "매주"
                binding.dateRBt3.id -> Rresult = "매월"
            }
        }

        val addRunnable = Runnable {
            val newChallenge = Challenge()
            newChallenge.challengeTittle = binding.selectChallenge.text.toString()
            newChallenge.challengeMenu = binding.select1Challenge.text.toString()
            newChallenge.date = Rresult
            db?.ChallengeDao()?.insert(newChallenge)
        }

        binding.endTextView.setOnClickListener {
            val addThread = Thread(addRunnable)
            addThread.start()

            (activity as AddChallengeActivity).mainActivity()
            requireActivity().finish()
        }
        return binding.root
    }

    fun bottomDialog(){
        val bottomDialog = BottomDialogFragment()
        bottomDialog.show(childFragmentManager, bottomDialog.tag)
    }

    fun selectChallenge(){
        setFragmentResultListener("selector"){ select, bundle ->
            result = bundle.getString("bundleKeys")
            Log.e("result",result!!)
            when(result){
                "운동" -> {
                    binding.selectChallenge.text = result
                }
                "생활습관" -> {
                    binding.addChallengeStep3.setBackgroundColor(Color.parseColor("#E2727A"))
                    binding.selectChallenge.text = result
                    binding.selectChallenge.setTextColor(Color.parseColor("#E2727A"))
                    binding.selectImage.setImageResource(R.drawable.habitpersonremovebg)
                    binding.endTextView.setBackgroundColor(Color.parseColor("#E2727A"))
                }
            }
        }
        setFragmentResultListener("position"){ position, bundle ->
            selectchallenge = bundle.getString("bundleKey")
            Log.e("selectChallenge",selectchallenge!!)
            binding.select1Challenge.setText(selectchallenge)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddChallengeStep3Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}