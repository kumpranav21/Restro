package com.example.restro;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurants")
public class Entities {

    @PrimaryKey(autoGenerate = true)
    public int R_id;

    @ColumnInfo(name = "R_name")
    public String R_name;

    @ColumnInfo(name = "R_price")
    public String R_price;

    @ColumnInfo(name = "R_desc")
    public String R_desc;

    @ColumnInfo(name = "R_img")
    public int R_img;



}
