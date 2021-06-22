package com.example.a1.ui.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.a1.Models.FootballMath
import com.example.a1.R
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [MathFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MathFragment : Fragment() {

    lateinit var mathFragmentScore:ImageView
    lateinit var mathFragmentCard:CardView
    //lateinit var mathFragmentTeams:TextView
    //lateinit var mathFragmentDate:TextView
    //lateinit var mathFragmentTime:TextView
    lateinit var mathFragmentWeb:WebView

    companion object{
        fun newInstance(math:FootballMath):MathFragment{
            val mathFragment = MathFragment()
            val args = Bundle()
            args.putString("mathTitle", math.title)
            args.putString("mathDate",math.date)
            args.putString("mathEmbed",math.embed)
            args.putString("mathUrl",math.url)
            args.putString("mathThumbnail",math.thumbnail)
            args.putString("mathSide1",math.side1)
            args.putString("mathSide2",math.side2)
            mathFragment.arguments = args
            return mathFragment
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_math,container, false)
        mathFragmentCard = view.findViewById(R.id.math_fragment_card)
        //mathFragmentDate = view.findViewById(R.id.math_fragment_date)
        mathFragmentScore = view.findViewById(R.id.math_fragment_score)
        //mathFragmentTeams = view.findViewById(R.id.math_fragment_teams)
        //mathFragmentTime = view.findViewById(R.id.math_fragment_time)
        mathFragmentWeb = view.findViewById(R.id.math_fragment_web_video)
        if (arguments!=null){
            val url = requireArguments().getString("mathEmbed","")
                .split("src='")[1]
                .split(" ")[0]
                .replace("'","")
            Log.i("url",url)
            Picasso.get().load(requireArguments().getString("mathThumbnail","")).into(mathFragmentScore)
            val date = requireArguments().getString("mathDate")!!.split("T")
            //mathFragmentDate.text = date[0]
            //mathFragmentTime.text = date[1].split("+")[0]
            //mathFragmentTeams.text = requireArguments().getString("mathTitle")
            mathFragmentWeb.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            mathFragmentWeb.settings.apply {
                this.javaScriptEnabled = true
                this.domStorageEnabled = true
                this.databaseEnabled = true
                this.allowFileAccess = true
            }
            mathFragmentWeb.loadUrl(requireArguments().getString("mathUrl",""))
        }
        return view
    }

}