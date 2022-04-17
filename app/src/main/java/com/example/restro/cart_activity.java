package com.example.restro;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class cart_activity extends AppCompatActivity {
    ListView listView;
    List<CartEntities> res;
    String price = "0";
    AppDatabase db;
    int Final = 0;
Button btnOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_orders);

        getSupportActionBar().setTitle("My Cart");
        btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cart_activity.this, order_screen.class);
                startActivity(i);
            }
        });
        list_sync(1, 0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(cart_activity.this);
                builder1.setTitle("Alert");
                builder1.setMessage("Delete " + res.get(position).F_name + "?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.F_Dao().deleteByName(res.get(position).F_name);
                                list_sync(2, Integer.parseInt(res.get(position).F_price));
                            }
                        });

                builder1.setNegativeButton(
                        "NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

    public void list_sync(int mode, int f) {
        db = restaurant_info.buildDB(this);

        res = db.F_Dao().getAll();

        listView = findViewById(R.id.lv1);
        TextView txtPriceFinal = findViewById(R.id.txtPriceFinal);
        if (mode == 1) {
            for (int i = 0; i < res.size(); i++) {
                price = res.get(i).F_price;

                Final += Integer.parseInt(price);

            }
        } else {
            Final -= f;
        }

        txtPriceFinal.setText(String.valueOf(Final));
        cartAdapter cartAdapter = new cartAdapter(this, res);
        listView.setAdapter(cartAdapter);
    }
}
