package com.example.kotlin_zem.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlin_zem.Adapter.ZemAdapter
import com.example.kotlin_zem.Adapter.ZemCompletionAdapter
import com.example.kotlin_zem.HabitInfoActivity
import com.example.kotlin_zem.R
import com.example.kotlin_zem.database.*
import com.example.kotlin_zem.databinding.FragmentIngBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IngFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IngFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentIngBinding? = null
    private val binding get() = _binding!!
    var db: ZemDB? =null
    var dbc: ZemCompletionDB? =null
    var dbe: ZemEndDB? =null
    var count = 0
    var ccount = 0
    var ecount = 0
    var zemList = listOf<Zem>()
    var zemcList = listOf<ZemCompletion>()
    var zemeList = listOf<ZemEnd>()
    lateinit var mAdapter: ZemCompletionAdapter

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
        count = 0
        _binding = FragmentIngBinding.inflate(inflater,container,false)

        db = ZemDB.getInstance(requireContext())
        dbc = ZemCompletionDB.getInstance(requireContext())
        dbe = ZemEndDB.getInstance(requireContext())

        var id = arguments?.getLong("BTID")
        Log.e("ingid", zemcList.toString())

        run()

        showEmpty()

        return binding.root
    }

    fun showEmpty(){
        if(ccount == 0){
            binding.Ingempty.visibility = View.VISIBLE
            binding.inglayout.visibility =View.GONE
        }
        else {
            binding.Ingempty.visibility = View.GONE
            binding.inglayout.visibility = View.VISIBLE
        }
    }
    fun run(){
        var a = Thread(
            Runnable {
                zemList = db?.ZemDao()?.getALL()!!
                if(zemList != null){
                    for(i in zemList){
                        count++
                    }
                }
                zemcList = dbc?.ZemCompletionDao()?.getALL()!!
                if(zemcList != null){
                    for(i in zemcList){
                        ccount++
                    }
                }
                zemeList = dbe?.ZemEndDao()?.getALL()!!
                if(zemeList != null){
                    for(i in zemeList){
                        ecount++
                        Log.e("end",ecount.toString())
                    }
                }

                mAdapter = ZemCompletionAdapter(requireContext(),zemcList){
                    zemCompletion ->
                    val data = Intent(requireContext(), HabitInfoActivity::class.java)
                    data.putExtra("ID",zemCompletion.id)
                    data.putExtra("PUT","ING")
                    startActivity(data)
                    Log.e("aaa",zemCompletion.id.toString())
                }
                mAdapter.notifyDataSetChanged()

                binding.habitRecyclerView.adapter = mAdapter
                binding.habitRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.habitRecyclerView.setHasFixedSize(true)

                val list = arrayListOf<Int>(ccount,ecount,count)
                setFragmentResult("tab11", bundleOf("bundleKey" to list))
            }
        )
        a.start()
        a.join()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IngFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IngFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}