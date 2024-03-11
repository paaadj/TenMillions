package com.example.tenmillions.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Question::class, User::class], version = 1, exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun questionDao(): QuestionDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "db")
                        .createFromAsset("database/TenMillions.db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}