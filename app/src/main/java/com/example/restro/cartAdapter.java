package com.example.restro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class cartAdapter extends BaseAdapter {
    Context context;
    List<CartEntities> res;


   public cartAdapter(Context context, List<CartEntities> res)
    {
        this.context = context;
        this.res = res;



    }


    @Override
    public int getCount() {
        return res.size();
    }

    @Override
    public Object getItem(int position) {
        return res.get(position);    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.listview_cart,null);





        TextView txt = view.findViewById(R.id.txtA);
        TextView txt1 = view.findViewById(R.id.txtFoodprice);
        txt.setText(res.get(position).F_name);
        txt1.setText(res.get(position).F_price);



        return view;
    }
}
