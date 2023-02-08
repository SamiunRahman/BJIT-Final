package com.samiun.mycricket.data
import androidx.room.*
import com.samiun.mycricket.model.country.Data
import com.samiun.mycricket.model.league.Leagues

@Dao
interface CricketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCountry(data : List<Data>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addleague(data : List<Leagues>)


}