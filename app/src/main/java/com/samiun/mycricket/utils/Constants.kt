package com.samiun.mycricket.utils

import java.text.SimpleDateFormat
import java.util.*

class Constants {
    companion object{
        const val databaseName = "cricket_database"
        const val BASE_URL = "https://cricket.sportmonks.com/api/v2.0/"
        const val apikey = "Wy9K8UlUMHGRkfslTawlhRtVk3v47DIhh2VCgfPhfww0ox42CiJ5aECYEe7h"

        //API's
        const val api_token ="api_token=Wy9K8UlUMHGRkfslTawlhRtVk3v47DIhh2VCgfPhfww0ox42CiJ5aECYEe7h"
        const val COUNTRY_END_POINT = "countries"
        const val LEAGUES_END_POINT ="leagues?$api_token"
        const val FIXTURE_END_POINT ="fixtures?$api_token"
        const val TEAM_END_POINT ="teams?$api_token"
       // const val FIXTURE_END_POINT ="fixtures?$api_token"

        //https://cricket.sportmonks.com/api/v2.0/fixtures?filter[starts_between]=2023-01-02T00:00:00.000000Z,2023-02-10T00:00:00.000000Z
    }
    fun dateFormat(date: String): String{

        val dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        val inputTime = sdf.parse(date).time
        val currentTime = System.currentTimeMillis()
        val difference = currentTime - inputTime
        val hoursAgo = (difference / (1000 * 60 * 60)).toInt()
        val daysAgo = (difference / (1000 * 60 * 60*24)).toInt()

        if (hoursAgo<25){
            return ("$hoursAgo hours ago")
        }
        else{
            return ("$daysAgo days ago")
        }
    }
}