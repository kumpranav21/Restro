package com.example.restro;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Fav_frag extends Fragment {
    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_fav, container, false);
        getActivity().setTitle("Favourites");
        AppDatabase db = restaurant_info.buildDB(getContext());
        List<Entities> res = db.R_Dao().getAll();

        listView = view.findViewById(R.id.listVw);
        ImgAdapter imgAdapter = new ImgAdapter(getContext(), res, view);
        listView.setAdapter(imgAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(),restaurant_info.class);
                i.putExtra("Name",res.get(position).R_name);
                i.putExtra("Price",res.get(position).R_price);
                i.putExtra("Desc",res.get(position).R_desc);
                i.putExtra("img",res.get(position).R_img);
                startActivity(i);
            }
        });
        return view;


    }
}
