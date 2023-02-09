package com.samiun.mycricket.network

import com.samiun.mycricket.model.country.Country
import com.samiun.mycricket.model.fixture.Fixture
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.league.League
import com.samiun.mycricket.model.team.Teams
import com.samiun.mycricket.utils.Constants.Companion.BASE_URL
import com.samiun.mycricket.utils.Constants.Companion.COUNTRY_END_POINT
import com.samiun.mycricket.utils.Constants.Companion.FIXTURE_END_POINT
import com.samiun.mycricket.utils.Constants.Companion.LEAGUES_END_POINT
import com.samiun.mycricket.utils.Constants.Companion.TEAM_END_POINT
//import com.samiun.mycricket.utils.Constants.Companion.LEAGUES_END_POINT
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CricketApiService{
    @GET(COUNTRY_END_POINT)
    suspend fun getCountries(
        @Query("api_token") apikey: String
    ): Country

    @GET(LEAGUES_END_POINT)
    suspend fun getLeagues(): League

    @GET(FIXTURE_END_POINT)
    suspend fun getFixtures(): Fixture
    @GET(TEAM_END_POINT)
    suspend fun getTeams(): Teams

}

object CricketApi{
    val retrofitService: CricketApiService by lazy { retrofit.create(CricketApiService::class.java) }
}