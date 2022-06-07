package com.example.kotlin_challenge.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_challenge.R
import com.example.kotlin_challenge.database.Challenge
import com.example.kotlin_challenge.databinding.FragmentChallenge1EndBinding
import com.example.kotlin_challenge.databinding.FragmentChallenge1IngBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [challenge_1_end.newInstance] factory method to
 * create an instance of this fragment.
 */
class Challenge1EndFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentChallenge1EndBinding? = null
    private val binding get() = _binding!!
    private var challengeList1: Long = 0

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
        _binding= FragmentChallenge1EndBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            load()
        }
        return binding.root
    }

    fun load(){
        parentFragmentManager.setFragmentResultListener(
            "requestKey13",
            viewLifecycleOwner
        ) { key, bundle ->
            challengeList1 = bundle.getLong("bundleKey")
            Log.e("END",challengeList1.toString())
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment challenge_1_end.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Challenge1EndFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}