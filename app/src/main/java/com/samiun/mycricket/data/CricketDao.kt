package com.samiun.mycricket.data
import androidx.lifecycle.LiveData
import androidx.room.*
import com.samiun.mycricket.model.country.Data
import com.samiun.mycricket.model.fixture.Fixture
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRun
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRunEntity
import com.samiun.mycricket.model.league.Leagues
import com.samiun.mycricket.model.officials.OfficialEntity
import com.samiun.mycricket.model.players.PlayerData
import com.samiun.mycricket.model.ranking.RankingData
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.model.venue.VenueEntity

@Dao
interface CricketDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRanking(data : List<RankingData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVenues(data : List<VenueEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOfficials(data : List<OfficialEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlayer(data : List<PlayerData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCountry(data : List<Data>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFixtureWithRun(fixture: List<FixtureWithRunEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addleague(data : List<Leagues>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFixture(fixture: List<FixtureEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTeam(teamEntity: List<TeamEntity>)

    @Query("select * from Fixtures order by starting_at limit 5")
    fun readAllFixtures(): LiveData<List<FixtureEntity>>


    @Query("select * from Fixturerun order by starting_at  desc limit 5")
    fun readFixtureWithRun(): LiveData<List<FixtureWithRunEntity>>

    @Query("select * from teams order by name")
    fun readTeamEntity(): LiveData<List<TeamEntity>>


    @Query("select * from players order by image_path desc")
    fun readPlayerData(): LiveData<List<PlayerData>>


    @Query("SELECT * FROM ranking WHERE gender = :gender AND type = :type")
    fun getRanking(gender: String, type : String): RankingData

    @Query("select * from teams where id = :id")
    fun readTeamId(id: Int): TeamEntity

    @Query("select * from venues where id = :id")
    fun readVenueId(id: Int): VenueEntity

    @Query("select * from officials where id = :id")
    fun readOfficialId(id: Int): OfficialEntity

    @Query("select * from leagues where id = :id")
    fun readLeagues(id: Int): Leagues






}