package com.example.kotlin_challenge.database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_challenge.R

class ChallengeAdapter(val context: Context, val challenge: List<Challenge>):
RecyclerView.Adapter<ChallengeAdapter.Holder>() {
    var count = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.challenge_item, parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: ChallengeAdapter.Holder, position: Int) {
        holder?.bind(challenge[position])
    }

    override fun getItemCount() = challenge.size

        interface OnItemClickListener {
            fun onItemClick(v: View?, position: Int) //뷰와 포지션값
        }

        private var mListener: OnItemClickListener? = null

        fun setOnItemClickListener(listener: OnItemClickListener?) {
            mListener = listener
        }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleC = itemView?.findViewById<TextView>(R.id.challengeTittle)
        val dateC = itemView?.findViewById<TextView>(R.id.date)
        val menuC = itemView?.findViewById<TextView>(R.id.challengeMenu)
        fun bind(challenge: Challenge) {
            titleC?.text = "["+challenge.challengeTittle+"]"
            dateC?.text = challenge.date
            menuC?.text = challenge.challengeMenu
            count++
            itemView.setOnClickListener {
                var position = position
                    if(position != RecyclerView.NO_POSITION){
                        mListener?.onItemClick(itemView,position)
                    }
                }
            }
        }
    }