package com.example.a1.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a1.Models.FootballMath
import com.example.a1.R
import com.example.a1.ui.Fragments.MathFragment
import com.squareup.picasso.Picasso

class MathesAdapter(val mathes:MutableList<FootballMath>, val fragmentManager: FragmentManager):RecyclerView.Adapter<MathesAdapter.MathesHolder>() {
    class MathesHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var scoreMath:ImageView
        var teamsMath:TextView
        var dateMath:TextView
        var timeMath:TextView
        var mathCard:CardView
        init {
            scoreMath = itemView.findViewById(R.id.math_score)
            teamsMath = itemView.findViewById(R.id.math_teams)
            dateMath = itemView.findViewById(R.id.math_date)
            timeMath = itemView.findViewById(R.id.math_time)
            mathCard = itemView.findViewById(R.id.math_card)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MathesHolder {
        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.math_card,parent,false)
        return MathesHolder(itemView)
    }

    override fun onBindViewHolder(holder: MathesHolder, position: Int) {

        Picasso.get().load(mathes[position].thumbnail).into(holder.scoreMath)
        holder.dateMath.text = mathes[position].date.split("T")[0]
        holder.timeMath.text = mathes[position].date.split("T")[1].split("+")[0]
        holder.teamsMath.text = mathes[position].title
        holder.mathCard.setOnClickListener {
            val math = MathFragment.newInstance(mathes[position])
            fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.nav_default_enter_anim,R.anim.nav_default_exit_anim)
                .add(R.id.fragment_math_container,math,"math_fragment").addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int {
        return mathes.size
    }
}