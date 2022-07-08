package com.example.kotlin_zem.Fragment

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
import com.example.kotlin_zem.AddHabitActivity
import com.example.kotlin_zem.HabitInfoActivity
import com.example.kotlin_zem.database.Zem
import com.example.kotlin_zem.database.ZemDB
import com.example.kotlin_zem.databinding.FragmentWaitBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WaitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WaitFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentWaitBinding? = null
    private val binding get() = _binding!!
    var db: ZemDB? =null
    lateinit var mAdapter: ZemAdapter
    var zemList = listOf<Zem>()
    var count = 0

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
        // Inflate the layout for this fragment
        _binding = FragmentWaitBinding.inflate(inflater,container,false)

        showEmpty()

        db = ZemDB.getInstance(requireContext())

        run()

        return binding.root
    }

    fun showEmpty(){
        val result = arguments?.getString("WAIT")
        Log.e("WAIT",result.toString())

        if(result == "0"){
            binding.waitempty.visibility = View.VISIBLE
            binding.waitelayout.visibility =View.GONE
        }
        else {
            binding.waitempty.visibility = View.GONE
            binding.waitelayout.visibility = View.VISIBLE
        }
    }

    fun run(){
        var a = Thread(
            Runnable {
                zemList = db?.ZemDao()?.getALL()!!
                if(zemList != null){
                    mAdapter = ZemAdapter(requireContext(),zemList){
                        zem ->
                        val data = Intent(requireContext(), HabitInfoActivity::class.java)
                        data.putExtra("ID",zem.id)
                        data.putExtra("PUT","WAIT")
                        Log.e("zemid",zem.id.toString())
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
         * @return A new instance of fragment WaitFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WaitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}