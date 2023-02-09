package com.samiun.mycricket.data

import androidx.lifecycle.LiveData
import androidx.room.Database
import com.samiun.mycricket.model.country.Data
import com.samiun.mycricket.model.fixture.Fixture
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.league.Leagues
import com.samiun.mycricket.model.team.TeamEntity

class CricketRepository(private val cricketDao: CricketDao) {
    val readFixtureEntity:LiveData<List<FixtureEntity>> = cricketDao.readAllFixtures()


    suspend fun addLeague(leagueList: List<Leagues>){
        cricketDao.addleague(leagueList)
    }
    suspend fun addCountry(countryList: List<Data>){
        cricketDao.addCountry(countryList)
    }

    suspend fun addFixtures(fixture: List<FixtureEntity>){
        cricketDao.addFixture(fixture)
    }
    suspend fun addTeams(teamEntity: List<TeamEntity>){
        cricketDao.addTeam(teamEntity)
    }

    suspend fun readTeam(id: Int): TeamEntity{
        return cricketDao.readTeamId(id)
    }


}