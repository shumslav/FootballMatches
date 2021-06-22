package com.example.a1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a1.Models.FootballMath
import com.example.a1.R
import com.example.a1.toFootballMath
import com.example.a1.toMap
import com.example.a1.ui.Adapters.MathesAdapter
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.*
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import com.mikepenz.materialdrawer.widget.MaterialDrawerSliderView
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var slider:MaterialDrawerSliderView
    private lateinit var recycler:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        slider = findViewById(R.id.slider)
        recycler = findViewById(R.id.math_recycler)
        recycler.layoutManager = LinearLayoutManager(this)


        val item1 = PrimaryDrawerItem().apply { nameText = "Mathes"; iconRes = R.drawable.small_ball; identifier = 1L
        isSelectable = false}
        val toolbar = Toolbar(this)

        val headerView = AccountHeaderView(this).apply {
            attachToSliderView(slider) // attach to the slider
            addProfiles(
                ProfileDrawerItem().apply { nameText = "Mike Penz";
                    descriptionText = "mikepenz@gmail.com";
                    iconRes = R.drawable.ic_launcher_background;}
            )
            onAccountHeaderListener = { view, profile, current ->
                // react to profile changes
                false
            }
            withSavedInstance(savedInstanceState)
        }

        slider.itemAdapter.add(
            item1
        )

        slider.onDrawerItemClickListener = {v: View?, item: IDrawerItem<*>, position: Int ->
            when(item.identifier){
                1L -> {true}
                else -> {false}
            }
        }
        val mathes = mutableListOf<FootballMath>()
        thread {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://free-football-soccer-videos.p.rapidapi.com/")
                .get()
                .addHeader("x-rapidapi-key", "0bf4174ac2msh27614236deeba5ep1a169ejsn12b0af132310")
                .addHeader("x-rapidapi-host", "free-football-soccer-videos.p.rapidapi.com")
                .build()

            val response = client.newCall(request).execute()
            val html = response.body!!.string()
            val b = JSONArray(html)
            for(i in 0..b.length()-1){
                mathes.add(b.getJSONObject(i).toFootballMath())
            }
        }.join()
        val fragmentManager = supportFragmentManager
        recycler.adapter = MathesAdapter(mathes, fragmentManager)
    }

}