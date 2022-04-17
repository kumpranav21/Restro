package com.example.restro;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface R_Dao {

    @Query("SELECT * FROM restaurants")
    List<Entities> getAll();

    @Query("SELECT * FROM restaurants WHERE R_name IN (:userIds)")
    List<Entities> loadAllByNAme(String userIds);

    @Query("SELECT * FROM restaurants WHERE R_name = :name")
    Entities load(String name);

    @Insert
    void insertAll(Entities... entities);

    @Query("DELETE FROM restaurants WHERE R_name = :name")
    void deleteByName(String name);
}
