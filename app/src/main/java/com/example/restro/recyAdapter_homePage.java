package com.example.restro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class recyAdapter_homePage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    int[] image = {R.drawable.discount_img1, R.drawable.discount_img2, R.drawable.discount_img3, R.drawable.discount_img4, R.drawable.discount_img5, R.drawable.discount_img6,};
    private RecyclerViewClickListner listner;
    private RecyclerViewClickListner listner1;
    List<FollowUpModel> followUpModel;
public static String quer;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 2;

    public recyAdapter_homePage(Context recyclerView, List followUpModel, RecyclerViewClickListner listner, RecyclerViewClickListner listner1) {
        this.followUpModel = followUpModel;
        this.context = recyclerView;
        this.listner = listner;
        this.listner1 = listner1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddesign_home_recy, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            //Inflating header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_header, parent, false);
            return new HeaderViewHolder(itemView);
        } else return null;


    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof HeaderViewHolder) {

            SliderAdapter sliderAdapter = new SliderAdapter(context.getApplicationContext(), image);
            ((HeaderViewHolder) holder).sliderView.setSliderAdapter(sliderAdapter);
            ((HeaderViewHolder) holder).sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
            ((HeaderViewHolder) holder).sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
            ((HeaderViewHolder) holder).sliderView.startAutoCycle();


        }
        if (holder instanceof ItemViewHolder) {

            ((ItemViewHolder) holder).txtName.setText(followUpModel.get(position).getName());
            ((ItemViewHolder) holder).txtDesc.setText(followUpModel.get(position).getDesc());
            ((ItemViewHolder) holder).txtPrice.setText(followUpModel.get(position).getPrice());
            Drawable drawable = context.getResources().getDrawable(followUpModel.get(position).getImg());
            ((ItemViewHolder) holder).img.setImageDrawable(drawable);


        }


    }

    @Override
    public int getItemCount() {
        return followUpModel.size();
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName;
        TextView txtDesc;
        TextView txtPrice;
        ImageView img;


        public ItemViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtPrice = itemView.findViewById(R.id.txtPrice);

            img = itemView.findViewById(R.id.Img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            try {
                listner.onClick(itemView, getAdapterPosition());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView txtHappy;
        TextView nearResto;
        SearchView searchveiw;
        SliderView sliderView;

        @SuppressLint("ClickableViewAccessibility")
        public HeaderViewHolder(View view) {
            super(view);
            txtHappy = view.findViewById(R.id.txtHappy);
            nearResto = view.findViewById(R.id.nearResto);
            searchveiw = view.findViewById(R.id.searchveiw);
            sliderView = view.findViewById(R.id.slider);
            searchveiw.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    quer = query;
                    try {
                        listner1.onClick(itemView, getAdapterPosition());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {


                    return false;
                }
            });


        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public interface RecyclerViewClickListner {

        void onClick(View v, int position) throws ExecutionException, InterruptedException;


    }


}
