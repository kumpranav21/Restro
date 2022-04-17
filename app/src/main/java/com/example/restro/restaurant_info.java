package com.example.restro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class restaurant_info extends AppCompatActivity {

    recyAdapter_restInfo recyARest;
    List<FollowUpModel> followUpModel;
    boolean checkFav;
    public LinearLayoutManager linearLayoutManager;

    int[] arrImg = {R.drawable.biryani, R.drawable.biryani, R.drawable.roll, R.drawable.paneer_tikka, R.drawable.fried_rice, R.drawable.momos, R.drawable.manchurian};
    String[] arrFoodName = {"Paneer Biryani", "Paneer Biryani", "Chicken Roll", "Paneer Masala", "Fried Rice", "Veg Momos", "Veg Manchurian"};
    String[] arrFoodPrice = {"300", "300", "450", "250", "150", "350", "550"};
    String HotelName;
    String HotelDesc;
    int rest_img;
    String HotelPrice;
    Button btnCart;
    AppDatabase db;

    private recyAdapter_homePage.RecyclerViewClickListner listner;
    private recyAdapter_homePage.RecyclerViewClickListner listner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restuarant_info_recy);


//        if (checkFav) {
//            btnSetText("REMOVE FROM FAV");
//
//        }else{
//            btnSetText("ADD TO FAV");
//
//        }

        RecyclerView recy = findViewById(R.id.recyView);
        btnCart = findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(restaurant_info.this, cart_activity.class);

                startActivity(i);
            }
        });

        linearLayoutManager = new LinearLayoutManager(restaurant_info.this);

        HotelName = getIntent().getStringExtra("Name");
        HotelPrice = getIntent().getStringExtra("Price");
        HotelDesc = getIntent().getStringExtra("Desc");
        rest_img = getIntent().getIntExtra("img", 0);


        recy.setLayoutManager(linearLayoutManager);

        followUpModel = new ArrayList<>();
        for (int i = 0; i < arrFoodName.length; i++) {

            FollowUpModel data = new FollowUpModel(arrFoodName[i], arrFoodPrice[i], arrImg[i], HotelName, HotelPrice, HotelDesc);
            followUpModel.add(data);


        }


        setOnClickListener();
        recyARest = new recyAdapter_restInfo(restaurant_info.this, followUpModel, listner,listner1);
        recy.setAdapter(recyARest);
        recy.setHasFixedSize(true);

    }


    private void setOnClickListener() {

        listner = new recyAdapter_homePage.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) throws ExecutionException, InterruptedException {
                Entities entities = new Entities();
                entities.R_name = HotelName;
                entities.R_desc = HotelDesc;
                entities.R_price = HotelPrice;
                entities.R_img = rest_img;


                 checkFav = new DBAsyntask(getApplicationContext(), entities, 1).execute().get();
                AsyncTask<Void, Void, Boolean> check = new DBAsyntask(getApplicationContext(), entities, 1).execute();


                if (checkFav) {

                    Toast.makeText(getApplicationContext(), "Restaurant already favourite ", Toast.LENGTH_SHORT).show();


                } else {
                    AsyncTask<Void, Void, Boolean> asyn = new DBAsyntask(getApplicationContext(), entities, 2).execute();
                    boolean result = asyn.get();

                    if (result) {
                        Toast.makeText(restaurant_info.this, "Restaurant added", Toast.LENGTH_SHORT).show();



                    } else {
                        Toast.makeText(restaurant_info.this, "Some error occurred", Toast.LENGTH_LONG).show();

                    }

                }

            }
        };

        listner1 = new recyAdapter_homePage.RecyclerViewClickListner() {

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "Rest-db").fallbackToDestructiveMigration().allowMainThreadQueries().build();

            @Override
            public void onClick(View v, int position) throws ExecutionException, InterruptedException {

                CartEntities entities = new CartEntities();
                entities.F_name = arrFoodName[position];
                entities.F_price = arrFoodPrice[position];
                db.F_Dao().insertAll(entities);

                Toast.makeText(restaurant_info.this, "Added", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    class DBAsyntask extends AsyncTask<Void, Void, Boolean> {

        Context context;
        Entities entity;
        int mode;

        DBAsyntask(Context context, Entities entity, int mode) {

            this.context = context;
            this.entity = entity;
            this.mode = mode;

        }

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Rest-db").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        @Override
        protected Boolean doInBackground(Void... voids) {


            switch (mode) {

                case 1: {
//DB if rest is fav or not
                    Entities rest = db.R_Dao().load(entity.R_name);
                    db.close();
                    return rest != null;


                }
                case 2: {
//SAve rest in DB as fav

                    db.R_Dao().insertAll(entity);
                    db.close();
                    return true;

                }
                case 3: {
//Remove rest from DB

                    db.R_Dao().deleteByName(entity.R_name);
                    db.close();
                    return true;

                }


            }

            return false;

        }
    }

    public static AppDatabase buildDB(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, "Rest-db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }
}
