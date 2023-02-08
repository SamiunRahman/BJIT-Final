package com.samiun.mycricket.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.samiun.mycricket.model.country.Data
import com.samiun.mycricket.model.league.Leagues
import com.samiun.mycricket.utils.Constants

@Database(entities = [Data::class, Leagues::class], version = 9, exportSchema = false)
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