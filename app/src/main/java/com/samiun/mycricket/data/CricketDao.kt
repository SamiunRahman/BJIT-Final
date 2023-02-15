package com.samiun.mycricket.data
import androidx.lifecycle.LiveData
import androidx.room.*
import com.samiun.mycricket.model.country.Data
import com.samiun.mycricket.model.fixture.Fixture
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRun
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRunEntity
import com.samiun.mycricket.model.league.Leagues
import com.samiun.mycricket.model.ranking.RankingData
import com.samiun.mycricket.model.team.TeamEntity

@Dao
interface CricketDao {

    @Query("select * from Fixtures order by starting_at desc limit 5")
    fun readAllFixtures(): LiveData<List<FixtureEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRanking(data : List<RankingData>)


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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFixtureWithRun(fixture: List<FixtureWithRunEntity>)

    @Query("select * from Fixturerun order by starting_at desc limit 5")
    fun readFixtureWithRun(): LiveData<List<FixtureWithRunEntity>>

    @Query("select * from teams order by name")
    fun readTeamEntity(): LiveData<List<TeamEntity>>

    @Query("SELECT * FROM ranking WHERE gender = :gender AND type = :type")
    fun getRanking(gender: String, type : String): RankingData

}