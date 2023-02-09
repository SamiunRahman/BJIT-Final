package com.samiun.mycricket.utils

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

        //https://cricket.sportmonks.com/api/v2.0/countries?api_token=Wy9K8UlUMHGRkfslTawlhRtVk3v47DIhh2VCgfPhfww0ox42CiJ5aECYEe7h

    }
}