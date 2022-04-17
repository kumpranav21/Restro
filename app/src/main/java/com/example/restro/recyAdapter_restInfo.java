package com.example.restro;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class recyAdapter_restInfo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<FollowUpModel> followUpModel;
    private recyAdapter_homePage.RecyclerViewClickListner listner;
    private recyAdapter_homePage.RecyclerViewClickListner listner1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 2;

    Context context;
    TextView Recomd;
    public static Button btnfav;

    public recyAdapter_restInfo(Context recyclerView, List followUpModel, recyAdapter_homePage.RecyclerViewClickListner listner,recyAdapter_homePage.RecyclerViewClickListner listner1) {
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
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddesign_restuarant_info, parent, false);
            return new recyAdapter_restInfo.ItemViewHolder(itemView, parent);


        } else if (viewType == TYPE_HEADER) {

            //Inflating header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resturant_info_header, parent, false);
            Recomd = itemView.findViewById(R.id.txtrecomded);
            btnfav = itemView.findViewById(R.id.btnfav);

            Login_page_praent.setUnderLineText(Recomd, "Recommended(6)");
            return new recyAdapter_restInfo.HeaderViewHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof recyAdapter_restInfo.HeaderViewHolder) {

            ((recyAdapter_restInfo.HeaderViewHolder) holder).txthotelName.setText(followUpModel.get(position).getHotelame());
            ((recyAdapter_restInfo.HeaderViewHolder) holder).txtdesc.setText(followUpModel.get(position).getHotelDesc());
            ((recyAdapter_restInfo.HeaderViewHolder) holder).eachPrice.setText(followUpModel.get(position).getHotelPrice());

        }
        if (holder instanceof recyAdapter_restInfo.ItemViewHolder) {

            ((recyAdapter_restInfo.ItemViewHolder) holder).txtFoodName.setText(followUpModel.get(position).getFoodName());
            ((recyAdapter_restInfo.ItemViewHolder) holder).txtFoodprice.setText(followUpModel.get(position).getFoodPrice());
            Drawable drawable = context.getResources().getDrawable(followUpModel.get(position).getFoodImg());
            ((recyAdapter_restInfo.ItemViewHolder) holder).img.setImageDrawable(drawable);

        }


    }

    @Override
    public int getItemCount() {
        return followUpModel.size();
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView txtFoodName;
        TextView txtFoodprice;
        ImageView img;
        Button btnAdd;


        public ItemViewHolder(View itemView, ViewGroup parent) {
            super(itemView);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtFoodprice = itemView.findViewById(R.id.txtFoodprice);
            img = itemView.findViewById(R.id.FoodImg);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnAdd.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            try {
                listner1.onClick(itemView, getAdapterPosition());

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txthotelName;
        TextView txtdesc;
        TextView rating;
        TextView totalRate;
        TextView eachPrice;

        TextView price;


        public HeaderViewHolder(View view) {
            super(view);
            txthotelName = view.findViewById(R.id.txthotelName);
            txtdesc = view.findViewById(R.id.txtHoteldesc);
            rating = view.findViewById(R.id.rating);
            totalRate = view.findViewById(R.id.totalRate);
            eachPrice = view.findViewById(R.id.eachPrice);
            btnfav = view.findViewById(R.id.btnfav);

            price = view.findViewById(R.id.price);
            btnfav.setOnClickListener(this);

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

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }


}
