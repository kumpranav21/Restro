package com.example.restro;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface F_Dao {

    @Query("SELECT * FROM CART")
    List<CartEntities> getAll();

    @Query("SELECT * FROM CART WHERE F_name IN (:userIds)")
    List<CartEntities> loadAllByNAme(String userIds);

    @Query("SELECT * FROM CART WHERE F_name = :name")
    CartEntities load(String name);

    @Insert
    void insertAll(CartEntities... entities);

    @Query("DELETE FROM CART WHERE F_name = :name")
    void deleteByName(String name);
    @Query("DELETE FROM CART")
    void delete();
}
