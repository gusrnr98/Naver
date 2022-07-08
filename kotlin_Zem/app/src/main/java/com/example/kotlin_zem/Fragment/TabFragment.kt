package com.example.kotlin_zem.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.kotlin_zem.Adapter.PagerAdapter
import com.example.kotlin_zem.HabitMainActivity
import com.example.kotlin_zem.MyTabLayout
import com.example.kotlin_zem.R
import com.example.kotlin_zem.databinding.FragmentTabBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TabFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding!!
    private var tabstate: Int = 2


    override fun onAttach(context: Context) {
        super.onAttach(context)
        tabstate = (activity as HabitMainActivity).gettabState()
    }
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
        val tabTextList1 = arrayListOf<Int>(0,0,0)
        _binding = FragmentTabBinding.inflate(inflater,container,false)


        var pagerAdapter = PagerAdapter(this)
        binding.habitstate.adapter = pagerAdapter
        pagerAdapter.setData(tabTextList1)

        childFragmentManager.setFragmentResultListener(
            "tab11",
            viewLifecycleOwner
        ) { key, bundle ->
            var tabTextList = bundle.getIntegerArrayList("bundleKey")
            Log.e("TAG",tabTextList.toString())
            tabTextList1[0] = tabTextList!![0]
            tabTextList1[1] = tabTextList!![1]
            tabTextList1[2] = tabTextList!![2]
            tabadd(tabTextList1)
        }
        return binding.root

    }

    fun tabadd(result: ArrayList<Int>){
        TabLayoutMediator(binding.habittabLayout,binding.habitstate){ tab, position ->
            tab.text = result[position].toString()
        }.attach()
        val result = arguments?.getString("TABSTATE")
        if(result != null){
            Log.e("result", result)
        }
        binding.habitstate.currentItem = tabstate
        binding.habitstate.isUserInputEnabled = false
        binding.habittabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    binding.habitstate.setCurrentItem(it,false)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TabFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TabFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}