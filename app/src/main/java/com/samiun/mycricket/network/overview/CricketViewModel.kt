package com.samiun.mycricket.network.overview
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.samiun.mycricket.data.CricketDatabase
import com.samiun.mycricket.data.CricketRepository
import com.samiun.mycricket.model.country.Data
import com.samiun.mycricket.model.fixture.Fixture
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.league.Leagues
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.network.CricketApi
import com.samiun.mycricket.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class CricketViewModel(application: Application): AndroidViewModel(application){

    private val _countries = MutableLiveData<List<Data>>()
    private val countries: LiveData<List<Data>> = _countries

    private val _leagues = MutableLiveData<List<Leagues>>()
    private val leagues: LiveData<List<Leagues>> = _leagues

    private val _fixture = MutableLiveData<List<FixtureEntity>>()
    private val fixture: LiveData<List<FixtureEntity>> = _fixture

    private val _team = MutableLiveData<List<TeamEntity>>()
    private val team: LiveData<List<TeamEntity>> = _team


    private val repository: CricketRepository

    val readFixtureEntity :LiveData<List<FixtureEntity>>

    init{
        val cricketDao = CricketDatabase.getDatabase(application).cricketDao()
        repository = CricketRepository(cricketDao)
        readFixtureEntity = repository.readFixtureEntity
        //readTeam = repository.readTeam(id)
    }

    fun getCountries(){
        viewModelScope.launch {
            try {
               // Log.d("Overview Fragment", "getCountries: ")
                _countries.value = CricketApi.retrofitService.getCountries(Constants.apikey).data
                countries.value?.let { Log.d("Api", "getCountries: ${it.get(0).name}") }

                countries.value?.let { addCountryList(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.d("Over View Model","$e")
            }
        }
    }

    suspend fun findTeamById(id: Int): TeamEntity{
        viewModelScope.launch(Dispatchers.IO) {  }
            return repository.readTeam(id)
    }

    private fun addCountryList(countryList: List<Data>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCountry(countryList)
        }
    }

    fun getLeagues(){
        viewModelScope.launch {
            try {
                // Log.d("Overview Fragment", "getCountries: ")
                _leagues.value = CricketApi.retrofitService.getLeagues().data
                leagues.value?.let { Log.d("Api", "getCountries: ${it.get(0).name}") }

                leagues.value?.let { addLeagueList(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.d("Over View Model","$e")
            }
        }
    }

    private fun addLeagueList(leagues: List<Leagues>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLeague(leagues)
        }
    }

    fun getFixtures(){
        viewModelScope.launch {
            try {
                Log.d("Overview Fragment", "Fixtue: ")
                _fixture.value = CricketApi.retrofitService.getFixtures().data
                fixture.value?.let { Log.d("Api", "Fixture: ${it.get(0).note}") }

                fixture.value?.let { addFixtureList(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.d("Overview Fragment excaption","$e")
            }
        }
    }

    private fun addFixtureList(fixture: List<FixtureEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFixtures(fixture)
            Log.d("FIXTURE", "FIXTURE: ${fixture.get(0).elected}")
        }
    }

    fun getTeams(){
        viewModelScope.launch {
            try {
                Log.d("Overview Fragment", "Teams: ")
                _team.value = CricketApi.retrofitService.getTeams().data
                team.value?.let { Log.d("Api", "Team: ${it.get(0).name}") }

                team.value?.let { addTeam(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.d("Overview Fragment excaption","$e")
            }
        }
    }

    private fun addTeam(teams: List<TeamEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTeams(teams)
        }
    }
}