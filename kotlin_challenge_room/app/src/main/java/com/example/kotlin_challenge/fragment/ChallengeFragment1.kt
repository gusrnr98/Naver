package com.example.kotlin_challenge.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.*
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.kotlin_challenge.R
import com.example.kotlin_challenge.database.ChallengeAdapter
import com.example.kotlin_challenge.databinding.FragmentChallenge1Binding
import com.example.kotlin_challenge.databinding.FragmentChallengeBinding
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [challengeFragment_1.newInstance] factory method to
 * create an instance of this fragment.
 */
open class ChallengeFragment1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentChallenge1Binding? = null
    private val binding get() = _binding!!
    private var position = 0
    private val challenge1Ing: Fragment = Challenge1IngFragment()
    private val challenge1End: Fragment = Challenge1EndFragment()

    var result: String = ""

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
        _binding = FragmentChallenge1Binding.inflate(inflater, container, false)

        binding.challengeTablayout.addTab(binding.challengeTablayout.newTab().setText("test"))
        binding.challengeTablayout.addTab(binding.challengeTablayout.newTab().setText("종료 0건"))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.setFragmentResultListener(
            "requestKey2",
            viewLifecycleOwner
        ) { key, bundle ->
            result = bundle.getString("bundleKey")!!
            Log.e("ChallengeFragment1", "result = $result")
            binding.challengeTablayout.getTabAt(0)?.setText("진행중 ${result}건")
        }
        navShow()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment challengeFragment_1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChallengeFragment1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun navShow() {
        Log.e("title", "navshow")
        childFragmentManager.beginTransaction()
            .replace(R.id.challengeIng_End, challenge1Ing)
            .setTransition(TRANSIT_FRAGMENT_OPEN)
            .commit()
        binding.challengeTablayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
                    position = tab.position
                    var select: Fragment = challenge1Ing
                    if (position == 0) {
                        select = challenge1Ing
                    } else if (position == 1) {
                        select = challenge1End
                    }
                    transaction.replace(R.id.challengeIng_End, select).commit()

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
    }
}