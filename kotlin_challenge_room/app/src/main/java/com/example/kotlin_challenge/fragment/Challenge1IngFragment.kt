package com.example.kotlin_challenge.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_challenge.R
import com.example.kotlin_challenge.database.Challenge
import com.example.kotlin_challenge.database.ChallengeAdapter
import com.example.kotlin_challenge.database.ChallengeDB
import com.example.kotlin_challenge.databinding.FragmentChallenge1IngBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [challenge_1_ing.newInstance] factory method to
 * create an instance of this fragment.
 */
class Challenge1IngFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentChallenge1IngBinding? = null
    private val binding get() = _binding!!
    private var db: ChallengeDB? =null
    private var challengeList = listOf<Challenge>()
    private var challengeList1: Long = 0
    lateinit var mAdapter: ChallengeAdapter
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
        _binding= FragmentChallenge1IngBinding.inflate(inflater, container, false)
        Log.e("Ing","IngView")

        db = ChallengeDB.getInstance(requireContext())

        mAdapter = ChallengeAdapter(requireContext(),challengeList)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        run()

        mAdapter.setOnItemClickListener(object : ChallengeAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, position: Int) {
//                challengeList1 = challengeList.get(position).id!!
//                setFragmentResult("requestKey13", bundleOf("bundleKey" to challengeList1))
                Log.e("ING","HI")
                delete(position)
            }
        })
    }

    fun run(){
        var a = Thread (
            Runnable {
                challengeList = db?.ChallengeDao()?.getAll()!!
                if (challengeList != null) {
                    for (i in challengeList) {
                        count++
                    }
                    mAdapter = ChallengeAdapter(requireContext(),challengeList)
                    mAdapter.notifyDataSetChanged()

                    binding.mRecyclerView.adapter = mAdapter
                    binding.mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.mRecyclerView.setHasFixedSize(true)

                    val result = count.toString()
                    setFragmentResult("requestKey2", bundleOf("bundleKey" to result))
                }
            }
        )
        a.start()
        a.join()
    }

    fun delete(position: Int){
        AlertDialog.Builder(context)
            .setTitle("삭제")
            .setMessage("정말 삭제하실건가요?")
            .setPositiveButton("yes", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    var r = Thread(
                        Runnable {
                            db?.ChallengeDao()?.deleteUserById(challengeList.get(position).id!!)
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.challengeIng_End, Challenge1IngFragment())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()
                        }
                    )
                    r.start()
                    r.join()
                }
            })
            .setNeutralButton("no", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                }
            })
            .create()
            .show()
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Challenge1IngFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}