package com.example.restro;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CART")
public class CartEntities {

    @PrimaryKey(autoGenerate = true)
    public int F_id;


    @ColumnInfo(name = "F_name")
    public String F_name;

    @ColumnInfo(name = "F_price")
    public String F_price;




}
