package com.samiun.mycricket.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.samiun.mycricket.model.country.Data
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRunEntity
import com.samiun.mycricket.model.league.Leagues
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.model.team.Teams
import com.samiun.mycricket.utils.Constants
import com.samiun.mycricket.utils.RunsConverter

@Database(entities = [Data::class, Leagues::class,TeamEntity::class, FixtureEntity::class, FixtureWithRunEntity::class], version =12, exportSchema = false)
@TypeConverters(RunsConverter::class)
abstract class CricketDatabase: RoomDatabase() {
    abstract fun cricketDao(): CricketDao

    companion object{
        @Volatile
        private var INSTANCE: CricketDatabase? = null

        fun getDatabase(context: Context): CricketDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CricketDatabase::class.java,
                    Constants.databaseName
                )
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}