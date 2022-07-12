package com.example.kotlin_challenge.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_challenge.MainActivity
import com.example.kotlin_challenge.PagerAdapter
import com.example.kotlin_challenge.databinding.FragmentChallengeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [challengeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChallengeFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentChallengeBinding? = null
    private val binding get() = _binding!!
    private val challengefragment1:Fragment = ChallengeFragment1()
    private val challengefragment2:Fragment = ChallengeFragment2()
    private val challengefragment3:Fragment = ChallengeFragment3()
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
        _binding = FragmentChallengeBinding.inflate(inflater, container, false)
        setUpViewPager()

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        binding.addBt.setOnClickListener {
            (activity as MainActivity).addActivity()
            requireActivity().finish()
        }
        return binding.root
    }

    private fun setUpViewPager() {
      var adapter = PagerAdapter(childFragmentManager!!)
        adapter.addFragment(challengefragment1,"전체 목표")
        adapter.addFragment(challengefragment2,"오늘 실천")
        adapter.addFragment(challengefragment3,"챌린지")
        binding.allchallenge.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.allchallenge)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment challengeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChallengeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
