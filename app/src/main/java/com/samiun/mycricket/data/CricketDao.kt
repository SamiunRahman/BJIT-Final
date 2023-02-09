package com.samiun.mycricket.data
import androidx.lifecycle.LiveData
import androidx.room.*
import com.samiun.mycricket.model.country.Data
import com.samiun.mycricket.model.fixture.Fixture
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.league.Leagues
import com.samiun.mycricket.model.team.TeamEntity

@Dao
interface CricketDao {

    @Query("select * from Fixtures order by starting_at limit 5")
    fun readAllFixtures(): LiveData<List<FixtureEntity>>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCountry(data : List<Data>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addleague(data : List<Leagues>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFixture(fixture: List<FixtureEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTeam(teamEntity: List<TeamEntity>)

    @Query("select * from teams where id = :id")
    fun readTeamId(id: Int): TeamEntity

}