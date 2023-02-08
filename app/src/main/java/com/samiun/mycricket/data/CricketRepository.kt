package com.samiun.mycricket.data

import androidx.room.Database
import com.samiun.mycricket.model.country.Data
import com.samiun.mycricket.model.league.Leagues

class CricketRepository(private val cricketDao: CricketDao) {
    suspend fun addLeague(leagueList: List<Leagues>){
        cricketDao.addleague(leagueList)
    }
    suspend fun addCountry(countryList: List<Data>){
        cricketDao.addCountry(countryList)
    }
}