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
import com.example.kotlin_zem.R
import com.example.kotlin_zem.databinding.FragmentAddHabitStep2Binding
import com.example.kotlin_zem.databinding.FragmentAddHabitStep3Binding
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddHabitStep3Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddHabitStep3Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAddHabitStep3Binding? = null
    private val binding get() = _binding!!


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
        _binding = FragmentAddHabitStep3Binding.inflate(inflater,container,false)

        setFragmentResultListener("habittype"){ habittype, bundle ->
            var str = bundle.getStringArrayList("bundleKey")!!

            show(str)

            binding.back.setOnClickListener {
                back(str)
            }
        }
        addtab()

        return binding.root
    }

    fun back(str: ArrayList<String>){
        setFragmentResult("fragment2", bundleOf("bundleKey" to str))
        parentFragmentManager.beginTransaction()
            .replace(R.id.addhabitstepview, AddHabitStep2Fragment())
            .commit()
    }

    fun show(list: ArrayList<String>){
        Log.e("FRAGMENT", list.toString())
        val imageId = context?.resources?.getIdentifier(list[2],"drawable",context?.packageName)
        binding.habitimage.setImageResource(imageId!!)
        binding.habittitle.text =  list[0] +" "+ list[1]
        binding.habitedit.setText(list[3])
    }

    fun addtab(){
        binding.step3tab.addTab(binding.step3tab.newTab().setText("7일"))
        binding.step3tab.addTab(binding.step3tab.newTab().setText("14일"))
        binding.step3tab.addTab(binding.step3tab.newTab().setText("30일"))
        binding.step3tab.addTab(binding.step3tab.newTab().setText("100일"))
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddHabitStep3Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}