package com.samiun.mycricket.utils

import java.lang.annotation.RetentionPolicy
import java.text.SimpleDateFormat
import java.util.*

class Constants {
    companion object{
        const val databaseName = "cricket_database"
        const val BASE_URL = "https://cricket.sportmonks.com/api/v2.0/"
        const val apikey = "Wy9K8UlUMHGRkfslTawlhRtVk3v47DIhh2VCgfPhfww0ox42CiJ5aECYEe7h"

        //API's
        const val api_token1 ="api_token=Wy9K8UlUMHGRkfslTawlhRtVk3v47DIhh2VCgfPhfww0ox42CiJ5aECYEe7h"

        const val api_token ="0ElKvYYdKqBDgRJc367869n3iPEljCurdPpqnjIMFcj3HqqHvAL35XJGXios"
        const val COUNTRY_END_POINT = "countries"
        const val LEAGUES_END_POINT ="leagues?$api_token1"

/*        val currentTime = getTime(0)
        val upcomingTime = getTime(5)*/
        const val FIXTURE_END_POINT ="fixtures?filter[starts_between]=2023-02-26T00:00:00.000000Z,2023-04-10T00:00:00.000000Z&$api_token1"
        const val FIXTURE_WITH_RUN_END_POINT ="fixtures?filter[starts_between]=2023-01-02T00:00:00.000000Z,2023-02-10T00:00:00.000000Z&include=runs&$api_token1"
        const val TEAM_END_POINT ="teams?$api_token1"
        const val UPCOMING_END_POINT ="fixtures?filter[starts_between]=2023-02-26T00:00:00.000000Z,2023-03-10T00:00:00.000000Z&$api_token1"
        const val FIXTUREWITHRUN_END_POINT ="fixtures?&include=runs"
       // https://cricket.sportmonks.com/api/v2.0/fixtures/:FIXTURE_ID?include=runs&api_token=FMRNjV3cC2q6xE31ya2oXBTizo4H1AMoYDXtPWszp62FBn6FMz7UAWXvaWWd
        fun getTime(days:Int):String{
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE,days)
            val time = calendar.time
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            return dateFormat.format(time)
        }

        fun dateFormat(inputDate: String): String{
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
            val outputFormat = SimpleDateFormat("EEE, MMM d")

            val date = inputFormat.parse(inputDate)
            val outputDate = outputFormat.format(date)

            return outputDate
        }
        fun timeFormat(inputDate: String): String{
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
            val outputFormat = SimpleDateFormat("hh:mm a")

            val date = inputFormat.parse(inputDate)
            val time = outputFormat.format(date)

            return time
        }
    }

}