package com.example.kotlin_challenge.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_challenge.AddChallengeActivity
import com.example.kotlin_challenge.R
import com.example.kotlin_challenge.databinding.FragmentAddChallengeStep1Binding
import com.example.kotlin_challenge.databinding.FragmentBottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private val addChallengeStep2Fragment: Fragment = AddChallengeStep2Fragment()
/**
 * A simple [Fragment] subclass.
 * Use the [BottomDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BottomDialogFragment : BottomSheetDialogFragment(){
    private var _binding: FragmentBottomDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomDialogBinding.inflate(inflater, container, false)
        binding.cancel.setOnClickListener{
            dismiss()
        }
        binding.clear.setOnClickListener{
            (activity as AddChallengeActivity).mainActivity()
        }
        return binding.root
    }

}