package com.example.restro;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder>{

    int[] images;
Context context;
    public SliderAdapter(Context context,int[] images){

        this.images = images;
        this.context =context;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.slider_layout,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {

        viewHolder.imageView.setImageResource(images[position]);

    }

    @Override
    public int getCount() {
        return images.length;
    }

     static class Holder extends  SliderViewAdapter.ViewHolder{

        ImageView imageView;
         View itemView;

        public Holder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.myimage);
            this.itemView=itemView;

        }
    }

}

