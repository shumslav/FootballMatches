package com.example.a1

import com.example.a1.Models.FootballMath
import org.json.JSONArray
import org.json.JSONObject

fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
    when (val value = this[it]) {
        is JSONArray -> {
            val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
            JSONObject(map).toMap().values.toList()
        }
        is JSONObject -> value.toMap()
        JSONObject.NULL -> null
        else -> value
    }
}

fun JSONObject.toFootballMath(): FootballMath {
    val json = this
    return FootballMath(json.getString("title"),
        json.getString("embed"),
        json.getString("url"),
        json.getString("thumbnail"),
        json.getString("date"),
        json.getString("title").split('-')[0].trim(),
        json.getString("title").split('-')[1].trim())
}