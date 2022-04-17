package com.example.restro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class home_frag extends Fragment {
    recyAdapter_homePage recyA;
    List<FollowUpModel> followUpModel;
    ProgressBar progressBar;

    private recyAdapter_homePage.RecyclerViewClickListner listner;
    private recyAdapter_homePage.RecyclerViewClickListner listner1;

    public LinearLayoutManager linearLayoutManager;


    int[] arrImg = {R.drawable.resto_img1, R.drawable.resto_img4, R.drawable.resto_img5, R.drawable.resto_img2, R.drawable.resto_img3, R.drawable.resto_img6,R.drawable.resto_img1};
    String[] arrName = {"Kothrud Bowl Company", "TGC The Gargi's Cafe", "Vaishali", "Exotica", "Kaka Da Hotel", "Sukh Sagar","Kothrud Bowl Company"};
    String[] arrDesc = {"Street Fast Food, North Indian", "Street Food,Maharashtrian", "Mithai, North Indian", "Italian, European, North Inadian", "North Indian, Street Food", "Italian, Maharashtrian","Street Fast Food, North Indian"};
    String[] arrPrice = {"₹450 for one", "₹310 for one", "₹160 for one", "₹550 for one", "₹350 for one", "₹200 for one","₹450 for one"};


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.home_page_recy, container, false);
        getActivity().setTitle("Home");
        progressBar = view.findViewById(R.id.spin_kit);
        progressBar.setVisibility(View.INVISIBLE);

        RecyclerView recy = view.findViewById(R.id.recyView);
        linearLayoutManager = new LinearLayoutManager(getContext());


        recy.setLayoutManager(linearLayoutManager);

        followUpModel = new ArrayList<>();
        for (int i = 0; i < arrName.length; i++) {
            FollowUpModel data = new FollowUpModel(arrName[i], arrImg[i], arrDesc[i], arrPrice[i]);
            followUpModel.add(data);

        }

        setOnClickListener();
        recyA = new recyAdapter_homePage(getContext(), followUpModel, listner,listner1);
        recy.setAdapter(recyA);
        recy.setHasFixedSize(true);
        recyA.notifyDataSetChanged();


        return view;

    }

    private void setOnClickListener() {



        listner = new recyAdapter_homePage.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {

                progressBar.setVisibility(View.VISIBLE);

                Intent i = new Intent(getActivity(),restaurant_info.class);
                i.putExtra("Name",followUpModel.get(position).getName());
                i.putExtra("Price",followUpModel.get(position).getPrice());
                i.putExtra("Desc",followUpModel.get(position).getDesc());
                i.putExtra("img",followUpModel.get(position).getImg());
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(i);

            }
        };
        listner1 = new recyAdapter_homePage.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) throws ExecutionException, InterruptedException {
                Intent i = new Intent(getActivity(),Search.class);
                startActivity(i);
            }
        };
    }


}
