package com.example.restro;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Entities.class,CartEntities.class,UserEntities.class}, version = 5)
abstract class AppDatabase extends RoomDatabase {
    public abstract R_Dao R_Dao();
    public abstract F_Dao F_Dao();
    public abstract U_Dao U_Dao();
}