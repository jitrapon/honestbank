package com.assingment.android.view.score

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assingment.android.R
import com.assingment.android.view.ScoreRank

class ScoreAdapter(private val scores: List<ScoreRank>) :
    RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {

    override fun getItemCount(): Int = scores.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_score, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(scores[position])
    }

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val rank: TextView = view.findViewById(R.id.rank)
        private val name: TextView = view.findViewById(R.id.name)
        private val score: TextView = view.findViewById(R.id.score)

        fun bind(item: ScoreRank) {
            rank.text = item.rank.toString()
            name.text = item.name
            score.text = item.score.toString()
        }
    }
}
