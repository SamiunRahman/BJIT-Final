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
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.fixturewithdetails.FixtureWithDetailsData
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRunEntity
import com.samiun.mycricket.model.league.Leagues
import com.samiun.mycricket.model.ranking.RankingData
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.model.teamDetails.TeamDetails
import com.samiun.mycricket.model.teamDetails.TeamDetailsData
import com.samiun.mycricket.network.CricketApi
import com.samiun.mycricket.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CricketViewModel(application: Application): AndroidViewModel(application){

    //For API
    private val _countries = MutableLiveData<List<Data>>()
    private val countries: LiveData<List<Data>> = _countries
    private val _leagues = MutableLiveData<List<Leagues>>()
    private val leagues: LiveData<List<Leagues>> get() = _leagues
    private val _ranking = MutableLiveData<List<RankingData>>()
    private val ranking: LiveData<List<RankingData>> get() = _ranking
    private val _fixture = MutableLiveData<List<FixtureEntity>>()
    private val fixture: LiveData<List<FixtureEntity>> get() = _fixture
    private val _fixturewithrun = MutableLiveData<List<FixtureWithRunEntity>>()
    private val fixturewithrun: LiveData<List<FixtureWithRunEntity>> get()  = _fixturewithrun
    private val _fixturewithDetails = MutableLiveData<FixtureWithDetailsData?>()
    private val fixturewithDetails: LiveData<FixtureWithDetailsData?> get()  = _fixturewithDetails

    private val _teamDetails = MutableLiveData<TeamDetailsData?>()
    private val teamDetails: LiveData<TeamDetailsData?> get()  = _teamDetails



    private val _team = MutableLiveData<List<TeamEntity>>()
    private val team: LiveData<List<TeamEntity>> = _team



    private val repository: CricketRepository

    val readFixtureEntity :LiveData<List<FixtureEntity>>
    val readFixtureWithRunEntity: LiveData<List<FixtureWithRunEntity>>
    val readTeamEntity: LiveData<List<TeamEntity>>


    init{
        val cricketDao = CricketDatabase.getDatabase(application).cricketDao()
        repository = CricketRepository(cricketDao)
        readFixtureEntity = repository.readFixtureEntity
        readFixtureWithRunEntity = repository.readFixtureWithRunEntity
        readTeamEntity = repository.readTeamEntity
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

    fun getRanking(){
        viewModelScope.launch {
            try {
                // Log.d("Overview Fragment", "getCountries: ")
                _ranking.value = CricketApi.retrofitService.getRanking().data
                ranking.value?.let { Log.d("Api", "getCountries: ${it.get(0).team}") }

                ranking.value?.let { addRanking(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.d("Over View Model","$e")
            }
        }
    }

    private fun addRanking(ranking: List<RankingData>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRanking(ranking)
        }
    }

    fun getFixtures(){
        val startDate = Constants.getTime(0)//"2023-02-26T00:00:00.000000Z"
        val endDate = Constants.getTime(30)//"2023-04-10T00:00:00.000000Z"
        viewModelScope.launch {
            try {
                Log.d("Overview Fragment", "Fixtue: ")
                _fixture.value = CricketApi.retrofitService.getFixtures("$startDate,$endDate").data

                //_fixture.value = CricketApi.retrofitService.getFixture(startDate,endDate).data
                //fixture.value?.let { Log.d("Api", "Fixture: ${it.get(0).note}") }

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

    private fun addFixtureWithRun(fixtureWithRunEntity: List<FixtureWithRunEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFixturesWithRun(fixtureWithRunEntity)
            Log.d("FIXTURE With Run", "FIXTURE With Run: ${fixtureWithRunEntity.get(0)}")
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

//    fun getFixtureWithRun(fixtureID: Int): LiveData<List<FixtureWithRunEntity>> {
//
//        viewModelScope.launch {
//            try {
//                Log.d("Fixture with Run", "Fixtuer: $fixtureID")
//                _fixturewithrun.value = CricketApi.retrofitService.getFixturewithRun(fixtureID, Constants.api_token, "runs").data
//                fixturewithrun.value?.let {
//                   // Log.d("Api Fixture with run", "Fixture: ${it[0].runs?.get(0)?.score}")
//                }
//                //fixturewithrun.value?.let { addFixtureWithRun(it) }
//
//            } catch (e: java.lang.Exception) {
//                _fixturewithrun.value = listOf()
//                Log.d("Overview Fragment exception", "$e")
//            }
//        }
//        return fixturewithrun
//
//    }


    fun getDetailsByMatch(fixtureID: Int): LiveData<FixtureWithDetailsData?> {

        viewModelScope.launch {
            try {
                Log.d("Fixture with Run", "Fixtuer: $fixtureID")
                _fixturewithDetails.value = CricketApi.retrofitService.getMatchDetails(fixtureID).data
                fixturewithDetails.value?.let {
                }
                Log.e("get details Api", "${fixturewithrun.value?.get(0)?.runs?.get(0)?.score}")

            } catch (e: java.lang.Exception) {
                _fixturewithrun.value = listOf()
                Log.d("Get Details api", "$e")
            }
        }

        return fixturewithDetails

    }


    fun getTeamDetails(id: Int): LiveData<TeamDetailsData?> {

        viewModelScope.launch {
            try {
               // _fixturewithDetails.value = CricketApi.retrofitService.getMatchDetails(fixtureID).data
                Log.e("Team API", "getTeamDetails: $id" )
                _teamDetails.value = CricketApi.retrofitService.getTeamDetails(id).data

                _teamDetails.value?.let {
                }
                delay(1000)
                Log.e("get Team Api", "${teamDetails.value!!.name}")

            } catch (e: java.lang.Exception) {
                _fixturewithrun.value = listOf()
                Log.d("Get Match Details API", "$e")
            }
        }

        return teamDetails

    }


    fun getFixturesWithRun(){
        val startDate = Constants.getTime(0)//"2023-02-26T00:00:00.000000Z"
        val endDate = Constants.getTime(-100)//"2023-04-10T00:00:00.000000Z"
        viewModelScope.launch {
            try {
                Log.d("Overview Fragment with runs", "Fixtue: ")
                _fixturewithrun.value = CricketApi.retrofitService.getFixtureWithRun().data
                fixturewithrun.value?.let { Log.d("Api", "Fixture: ${it.get(0).note}") }

                fixturewithrun.value?.let { addFixtureWithRun(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.e("Overview Fragment excaption","$e")
            }
        }
    }


    fun getRanking(gender:String, format:String): LiveData<RankingData> {
        val result = MutableLiveData<RankingData>()
        viewModelScope.launch(Dispatchers.IO) {
           val ranking = repository.getRanking(gender, format)
            result.postValue(ranking)
        }
        return result
    }

}