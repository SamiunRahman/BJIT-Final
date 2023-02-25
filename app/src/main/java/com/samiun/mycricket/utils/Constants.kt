package com.samiun.mycricket.utils

import android.util.Log
import com.samiun.mycricket.model.fixturewithrun.Run
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

class Constants {
    companion object{
        const val databaseName = "cricket_database"
        const val BASE_URL = "https://cricket.sportmonks.com/api/v2.0/"
        const val apikey = "Wy9K8UlUMHGRkfslTawlhRtVk3v47DIhh2VCgfPhfww0ox42CiJ5aECYEe7h"

        //API's

      //  hNf2oXFWaRWVINxXWzIZczPrbbH1db5WMoQ6osus2XhsK3Z5wI4D3Nsf8vTY

        const val api_token3 = "api_token=qEx13JbWRD8rtNU6dGUIpQIx6aHxawElzDxMs4EkGNpOMTrvqyjqZSHQdZIK"

        //const val api_token1 ="api_token=hNf2oXFWaRWVINxXWzIZczPrbbH1db5WMoQ6osus2XhsK3Z5wI4D3Nsf8vTY"
        const val api_token1 ="api_token=Wy9K8UlUMHGRkfslTawlhRtVk3v47DIhh2VCgfPhfww0ox42CiJ5aECYEe7h"

        const val api_token2 ="qEx13JbWRD8rtNU6dGUIpQIx6aHxawElzDxMs4EkGNpOMTrvqyjqZSHQdZIK"
        const val api_token ="0ElKvYYdKqBDgRJc367869n3iPEljCurdPpqnjIMFcj3HqqHvAL35XJGXios"
        //const val api_token ="hNf2oXFWaRWVINxXWzIZczPrbbH1db5WMoQ6osus2XhsK3Z5wI4D3Nsf8vTY"
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
            outputFormat.timeZone = TimeZone.getDefault()
            val date = inputFormat.parse(inputDate)
            val outputDate = outputFormat.format(date)

            return outputDate
        }
        fun timeFormat(inputDate: String): String{

            val outputFormat = DateTimeFormatter.ofPattern("hh:mm a")

            val instant = Instant.parse(inputDate)
            val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            val time = outputFormat.format(date)

            return time
        }
        fun upcomingtimeFormat(inputDate: String): String{

            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
            val outputFormat = DateTimeFormatter.ofPattern("hh:mm a")

            val instant = Instant.parse(inputDate)
            val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            val time = outputFormat.format(date)

            return time
        }
        fun convertDateTime(dateTimeString: String): String {
            val formatter = DateTimeFormatter.ofPattern("dd MMMM h a")
            val instant = Instant.parse(dateTimeString)
            val date = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
            return formatter.format(date)
        }

        fun calculateAge(dateString: String?): String {
            try {
                val dob = LocalDate.parse(dateString)
                val today = LocalDate.now()
                return Period.between(dob, today).years.toString()+" Years"
            }
            catch (e:Exception){
                Log.e("Constant Calculate Age Exception", "calculateAge: $e", )
                return "not available"
            }
        }

        fun homedata(results: List<com.samiun.mycricket.model.teamDetails.Result>?, id: Int): List<Any> {
            val info = mutableListOf<Any>()
            var total = 0
            var home = 0
            var away = 0
            var percentage = 0.0

            if(results!=null){
                for(result in results){
                    if(result in results){
                        if(result.localteam_id ==id){
                            total++
                            if(result.winner_team_id ==id){
                                home++
                            }
                            else{
                                away++
                            }
                        }
                    }
                }
            }
            percentage =( home.toDouble()/total.toDouble())*100
            info.add(total)
            info.add(home)
            info.add(away)
            info.add(percentage)
            return info
        }



        fun awaydata(results: List<com.samiun.mycricket.model.teamDetails.Result>?, id: Int): List<Any> {
            val info = mutableListOf<Any>()
            var total = 0
            var home = 0
            var away = 0
            var percentage = 0.0

            if(results!=null){
                for(result in results){
                    if(result in results){
                        if(result.localteam_id !=id){
                            total++
                            if(result.winner_team_id ==id){
                                home++
                            }
                            else{
                                away++
                            }
                        }
                    }
                }
            }
            percentage =( home.toDouble()/total.toDouble())*100
            info.add(total)
            info.add(home)
            info.add(away)
            info.add(percentage)
            return info
        }
        fun totalTeamWon(results: List<com.samiun.mycricket.model.teamDetails.Result>?, id: Int?): Int {
            var won = 0
            if (results != null) {
                for (result in results) {
                    if (result.winner_team_id == id) {
                        won++
                    }
                }
            }
            return won
        }

        fun totalOtherWon(results: List<com.samiun.mycricket.model.teamDetails.Result>?, id: Int?): Int {
            var won = 0
            if (results != null) {
                for (result in results) {
                    if (result.winner_team_id != id && result.winner_team_id!=null) {
                        won++
                    }
                }
            }
            return won
        }

        fun countBalls(over:Double): Int{
            val balls:Int = ((over*10)/10).toInt()* 6
            val remainder: Int = ((over*10)%10).toInt()
            return (balls + remainder) ?: 0
        }


        fun WinningPercentage(list :List<Run>): Double {
            val homeRating = 179
            val awayRating =239
            var winningPercentage =50.0
            val homeBattingScore = 179


            val run1 = Run(2239,121,1,20.0,"44/2",null,null,null,180,10,null,2)
            val run2 = Run(2239,122,2,4.0,"40/2",null,null,null,40,37,null,4)


            if(list.size==1){
                var wicketPoints1 =0.0
                var runrate = 0.0
                val ball1 = list.get(0).overs?.let { countBalls(it) }
                Log.d("WInning percentage balls", "calculateBangladeshWinningPercentage: $ball1")
                if(list.get(0).wickets!=null && list.get(0).wickets!=null){
                     wicketPoints1 = list.get(0).wickets!!.toDouble()/list.get(0).overs!!
                }


                if(list.get(0).score!=null){
                    runrate = (list.get(0).score!!.toDouble()/ball1!!.toDouble())*6
                }

                val points1 =runrate-wicketPoints1

                winningPercentage = (points1/(points1+8))*100
                Log.d("Winning Percentage", "team 1:$runrate $wicketPoints1 $points1   : $winningPercentage")

            }
            else if(list.size==2){
                var wicketPoints1 =0.0
                var runrate1 = 0.0
                val ball1 = list.get(0).overs?.let { countBalls(it) }
                if(list.get(0).wickets!=null && list.get(0).wickets!=null){
                    wicketPoints1 = list.get(0).wickets!!.toDouble()/list.get(0).overs!!
                }


                if(list.get(0).score!=null){
                    runrate1 = (list.get(0).score!!.toDouble()/ball1!!.toDouble())*6
                }

                val points1 =runrate1-wicketPoints1
                var requireRate =0.0
                var wicketPoints2 =0.0
                var runrate2 = 0.0
                val ball2 = list.get(1).overs?.let { countBalls(it) }
                if(list.get(1).wickets!=null && list.get(1).wickets!=null){
                    wicketPoints2 = list.get(1).wickets!!.toDouble()/list.get(1).overs!!
                }


                if(list.get(1).score!=null){
                    runrate2 = (list.get(1).score!!.toDouble()/ball2!!.toDouble())*6
                    requireRate = ((list.get(0).score!! - list.get(1).score!!)/(120-ball2).toDouble())*6
                }

                val points2 = requireRate-(wicketPoints2)

                winningPercentage = (points1/(points1+points2))*100
                Log.d("Winning Percentage", "$runrate1 and $runrate2 $wicketPoints1 $points1 and:$points2   : $winningPercentage")

            }

           // Log.d("Winning Percentage", "calculateBangladeshWinningPercentage:$winningPercentage")


            return winningPercentage
        }
    }

}