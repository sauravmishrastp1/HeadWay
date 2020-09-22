package com.headwayagent.salesadviser_headwaygms.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.headwayagent.salesadviser_headwaygms.MainActivity;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.Registeration_Activity;
import com.headwayagent.salesadviser_headwaygms.SalesDashbBoard;
import com.headwayagent.salesadviser_headwaygms.activity.Activ_Inactiv;
import com.headwayagent.salesadviser_headwaygms.activity.MyOrdersActivity;
import com.headwayagent.salesadviser_headwaygms.models.CardItem_Hodel_List;

import java.util.ArrayList;
import java.util.List;

public class Virtical_Adapter extends RecyclerView.Adapter<Virtical_Adapter.ViewHolder> {

   private ArrayList<CardItem_Hodel_List>cartlist;
   private Context context;

    public Virtical_Adapter(ArrayList<CardItem_Hodel_List> cartlist, Context context) {
        this.cartlist = cartlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout,parent,false);
        return new Virtical_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageview.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animationdashboard));
        holder.textView1.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animationdashboard));
        holder.textView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animationdashboard));
        holder.view.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animationdashboard));
        final String name = cartlist.get(position).getName();
        String amoynt = cartlist.get(position).getAmount();
        int image = cartlist.get(position).getBackground();

        holder.textView.setText(name);
        holder.textView1.setText(amoynt);
        holder.imageview.setImageResource(image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




//                if (name.equals("Active Team")) {
//                    Intent intent = new Intent(context, Activ_Inactiv.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("type", "2");
//                    context.startActivity(intent);
//                }
//                if (name.equals("Inactive Team")) {
//                    Intent intent = new Intent(context, Activ_Inactiv.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("type", "1");
//                    context.startActivity(intent);
//                }
                if (name.equals("Team")) {
                    Intent intent = new Intent(context, Activ_Inactiv.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "2");
                    context.startActivity(intent);
                }









            }
        });

    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView1;
        View view;
                ImageView imageview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView =itemView.findViewById(R.id.textvieww);
            textView1 = itemView.findViewById(R.id.text1);
            view = itemView.findViewById(R.id.container);
            imageview = itemView.findViewById(R.id.acetimg);
        }
    }
}
