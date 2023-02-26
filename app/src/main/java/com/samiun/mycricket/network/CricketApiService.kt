package com.samiun.mycricket.network

import com.samiun.mycricket.model.country.Country
import com.samiun.mycricket.model.fixture.Fixture
import com.samiun.mycricket.model.fixturewithdetails.FixtureWithDetails
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRun
import com.samiun.mycricket.model.league.League
import com.samiun.mycricket.model.liveScore.LiveScore
import com.samiun.mycricket.model.officials.Official
import com.samiun.mycricket.model.playerDetails.PlayerDetails
import com.samiun.mycricket.model.players.Players
import com.samiun.mycricket.model.ranking.Ranking
import com.samiun.mycricket.model.team.Teams
import com.samiun.mycricket.model.teamDetails.TeamDetails
import com.samiun.mycricket.model.teamsquad.TeamSquad
import com.samiun.mycricket.model.venue.Venue
import com.samiun.mycricket.utils.Constants
import com.samiun.mycricket.utils.Constants.Companion.BASE_URL
import com.samiun.mycricket.utils.Constants.Companion.COUNTRY_END_POINT
import com.samiun.mycricket.utils.Constants.Companion.LEAGUES_END_POINT
import com.samiun.mycricket.utils.Constants.Companion.TEAM_END_POINT
import com.samiun.mycricket.utils.Constants.Companion.UPCOMING_END_POINT
//import com.samiun.mycricket.utils.Constants.Companion.LEAGUES_END_POINT
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


val okHttpClient = OkHttpClient.Builder()
    .readTimeout(20, TimeUnit.SECONDS)
    .connectTimeout(20, TimeUnit.SECONDS)
    .build()
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface CricketApiService{
    @GET(COUNTRY_END_POINT)
    suspend fun getCountries(
        @Query("api_token") apikey: String
    ): Country

    @GET(LEAGUES_END_POINT)
    suspend fun getLeagues(): League

    @GET(TEAM_END_POINT)
    suspend fun getTeams(): Teams



    @GET("fixtures/{FIXTURE_ID}?include=batting,bowling,lineup,balls&${Constants.api_token1}")
    suspend fun getMatchDetails(
        @Path(value = "FIXTURE_ID", encoded = false) key: Int
    ): FixtureWithDetails

    @GET("fixtures/{FIXTURE_ID}?include=runs&${Constants.api_token1}")
    suspend fun getRuns(
        @Path(value = "FIXTURE_ID", encoded = false) key: Int
    ): FixtureWithRun

    @GET("livescores?include=batting,bowling,lineup,balls,runs&${Constants.api_token1}")
    suspend fun getLiveMatches(
    ): LiveScore


    @GET("fixtures?&${Constants.api_token1}")
    suspend fun getFixtures(
        @Query(value = "filter[starts_between]", encoded = false) p1: String
    ): Fixture

    @GET("fixtures")
    suspend fun getFixtureWithRun(
        @Query("filter[starts_between]") p1: String = "${Constants.getTime(-200)},${Constants.getTime(0)}",
        @Query("include") p2: String = "runs",
        @Query("api_token") p3: String = Constants.api_token
    ): FixtureWithRun



    @GET("players?fields[players]=id,fullname,image_path,dateofbirth&${Constants.api_token1}")
    suspend fun getPlayers(): Players

    @GET("team-rankings?${Constants.api_token1}")
    suspend fun getRanking(): Ranking

    @GET("venues?${Constants.api_token1}")
    suspend fun getVenus(): Venue

    @GET("officials?${Constants.api_token1}")
    suspend fun getOfficials(): Official

    @GET("teams/{TEAM_ID}?include=fixtures,results,squad,country&${Constants.api_token1}")
    suspend fun getTeamDetails(
        @Path(value = "TEAM_ID", encoded = false) key: Int
    ): TeamDetails


    @GET("teams/{TEAM_ID}/squad/23?&${Constants.api_token1}")
    suspend fun getTeamSquad(
        @Path(value = "TEAM_ID", encoded = false) key: Int
    ): TeamSquad



    @GET("players/{PLAYER_ID}?include=career,teams&${Constants.api_token1}")
    suspend fun getPlayerDetails(
       @Path(value = "PLAYER_ID", encoded = false) key: Int
    ): PlayerDetails
}

object CricketApi{
    val retrofitService: CricketApiService by lazy { retrofit.create(CricketApiService::class.java) }
}