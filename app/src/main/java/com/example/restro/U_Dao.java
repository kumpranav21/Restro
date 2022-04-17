package com.example.restro;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface U_Dao {

    @Query("SELECT * FROM USER")
    List<UserEntities> getAll();

    @Query("SELECT * FROM USER WHERE U_Email IN (:userIds)")
    List<UserEntities> loadAllByNAme(String userIds);

    @Query("SELECT * FROM USER WHERE U_name = :name")
    UserEntities load(String name);

    @Insert
    void insertAll(UserEntities... entities);

    @Query("DELETE FROM USER WHERE U_name = :name")
    void deleteByName(String name);
    @Query("DELETE FROM USER")
    void delete();
}
