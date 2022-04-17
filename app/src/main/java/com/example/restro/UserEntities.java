package com.example.restro;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "USER")
public class UserEntities {

    @PrimaryKey(autoGenerate = true)
    public int U_id;


    @ColumnInfo(name = "U_name")
    public String U_name;

    @ColumnInfo(name = "U_Ph")
    public String U_Ph;
    @ColumnInfo(name = "U_Email")
    public String U_Email;
    @ColumnInfo(name = "U_Pass")
    public String U_Pass;

    @ColumnInfo(name = "U_ADD")
    public String U_ADD;


}
