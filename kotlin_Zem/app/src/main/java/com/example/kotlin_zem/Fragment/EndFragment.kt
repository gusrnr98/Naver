package com.example.kotlin_zem.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlin_zem.Adapter.ZemAdapter
import com.example.kotlin_zem.Adapter.ZemEndAdapter
import com.example.kotlin_zem.HabitInfoActivity
import com.example.kotlin_zem.R
import com.example.kotlin_zem.database.Zem
import com.example.kotlin_zem.database.ZemDB
import com.example.kotlin_zem.database.ZemEnd
import com.example.kotlin_zem.database.ZemEndDB
import com.example.kotlin_zem.databinding.FragmentEndBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EndFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EndFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentEndBinding? = null
    private val binding get() = _binding!!
    var dbe: ZemEndDB? =null
    var zemEndList = listOf<ZemEnd>()
    lateinit var mAdapter: ZemEndAdapter

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
        // Inflate the layout for this fragment
        _binding = FragmentEndBinding.inflate(inflater,container,false)

        showEmpty()

        dbe = ZemEndDB.getInstance(requireContext())

        run()

        return binding.root
    }

    fun showEmpty(){
        val result = arguments?.getString("END")
        Log.e("END",result.toString())

        if(result == "0"){
            binding.endempty.visibility = View.VISIBLE
            binding.endlayout.visibility =View.GONE
        }
        else {
            binding.endempty.visibility = View.GONE
            binding.endlayout.visibility = View.VISIBLE
        }
    }

    fun run(){
        var a = Thread(
            Runnable {
                zemEndList = dbe?.ZemEndDao()?.getALL()!!

                if(zemEndList != null){
                    Log.e("END", zemEndList.toString())
                    mAdapter = ZemEndAdapter(requireContext(),zemEndList){
                        zemEnd ->
                        val data = Intent(requireContext(), HabitInfoActivity::class.java)
                        data.putExtra("ID",zemEnd.id)
                        data.putExtra("PUT","END")
                        startActivity(data)
                    }

                    mAdapter.notifyDataSetChanged()

                    binding.habitRecyclerView.adapter = mAdapter
                    binding.habitRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.habitRecyclerView.setHasFixedSize(true)
                }
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
         * @return A new instance of fragment EndFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EndFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}