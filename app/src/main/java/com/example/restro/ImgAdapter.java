package com.example.restro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import java.util.List;

public class ImgAdapter extends BaseAdapter {
    Context context;
    List<Entities> res;
View listV;
    ListView listView;
    public ImgAdapter(Context context, List<Entities> res,View listV) {
        this.context = context;
        this.res = res;
        this.listV = listV;



    }


    @Override
    public int getCount() {
        return res.size();
    }

    @Override
    public Object getItem(int position) {
        return res.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.activity_imgadapter, null);

        ImageView img = view.findViewById(R.id.img);
        Drawable drawable = context.getResources().getDrawable(res.get(position).R_img);
        img.setImageDrawable(drawable);

        TextView txt = view.findViewById(R.id.txtA);
        TextView txt1 = view.findViewById(R.id.txtFoodprice);
        txt.setText(res.get(position).R_name);
        txt1.setText(res.get(position).R_price);
        Button btnRemove = view.findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            AppDatabase db = Room.databaseBuilder(view.getContext(),
                    AppDatabase.class, "Rest-db").fallbackToDestructiveMigration().allowMainThreadQueries().build();

            @Override
            public void onClick(View v) {


                db.R_Dao().deleteByName(res.get(position).R_name);

                List<Entities> res = db.R_Dao().getAll();
                if (res == null)
                    Toast.makeText(view.getContext(), "Empty", Toast.LENGTH_SHORT).show();
                ImgAdapter imgAdapter = new ImgAdapter(view.getContext(), res, listV);

                 listView = listV.findViewById(R.id.listVw);

                listView.setAdapter(imgAdapter);


                Toast.makeText(view.getContext(), "Removed", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
