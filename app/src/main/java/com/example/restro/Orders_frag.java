package com.example.restro;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.List;

public class Orders_frag extends Fragment {
    View view;
    ListView listView;
    List<CartEntities> res;
    String price = "0";
    AppDatabase db;
    Button btnOrder;
    int Final = 0;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


         view = inflater.inflate(R.layout.frag_orders, container, false);
String price = "0";
int Final = 0;
        getActivity().setTitle("My Orders");
        btnOrder = view.findViewById(R.id.btnOrder);
        list_sync(1, 0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
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
        return view;


    }
    public void list_sync(int mode, int f) {
        db = restaurant_info.buildDB(getContext());

        res = db.F_Dao().getAll();

        listView = view.findViewById(R.id.lv1);
        TextView txtPriceFinal = view.findViewById(R.id.txtPriceFinal);
        if (mode == 1) {
            for (int i = 0; i < res.size(); i++) {
                price = res.get(i).F_price;

                Final += Integer.parseInt(price);

            }
        } else {
            Final -= f;
        }

        txtPriceFinal.setText(String.valueOf(Final));
        cartAdapter cartAdapter = new cartAdapter(getContext(), res);
        listView.setAdapter(cartAdapter);
    }
}
