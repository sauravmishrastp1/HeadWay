package com.headwayagent.salesadviser_headwaygms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.models.CardItem_Hodel_List;

import java.util.ArrayList;
import java.util.List;

public class DashBoard_Adapter extends RecyclerView.Adapter<DashBoard_Adapter.ViewHolder> {

   private ArrayList<CardItem_Hodel_List>cardItem_hodel_lists;
   private Context context;

    public DashBoard_Adapter(ArrayList<CardItem_Hodel_List> cardItem_hodel_lists, Context context) {
        this.cardItem_hodel_lists = cardItem_hodel_lists;
        this.context = context;
    }

    @NonNull
    @Override
    public DashBoard_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_layout,parent,false);
        return new DashBoard_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashBoard_Adapter.ViewHolder holder, int position) {
     holder.imageView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animationdashboard));
        holder.textView2.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animationdashboard));
        holder.textView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animationdashboard));
        String name = cardItem_hodel_lists.get(position).getName();
        String price =cardItem_hodel_lists.get(position).getAmount();
       int dimage = cardItem_hodel_lists.get(position).getBackground();
        holder.textView.setText(name);
        holder.textView2.setText(price);
        holder.imageView.setBackgroundResource(dimage);


    }

    @Override
    public int getItemCount() {
        return cardItem_hodel_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView2;
        View view;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.coursename);
            textView2=itemView.findViewById(R.id.coursepricee);
            view = itemView.findViewById(R.id.dashboard_layout);
            imageView =itemView.findViewById(R.id.assetimg);

        }
    }
}
