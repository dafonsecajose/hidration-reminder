package br.com.jose.hidratereminder.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.jose.hidratereminder.data.database.dao.HistoryDrinkDao
import br.com.jose.hidratereminder.data.model.HistoryDrink

@Database(entities = [HistoryDrink::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract  fun historyDrinkDao(): HistoryDrinkDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "drinks_db",
            ).fallbackToDestructiveMigration().build()
        }
    }
}