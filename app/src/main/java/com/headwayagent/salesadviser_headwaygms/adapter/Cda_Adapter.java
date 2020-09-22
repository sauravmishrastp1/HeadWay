package com.headwayagent.salesadviser_headwaygms.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.headwayagent.salesadviser_headwaygms.MainActivity;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.Registeration_Activity;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.activity.Activ_Inactiv;
import com.headwayagent.salesadviser_headwaygms.activity.CustomerDetailsFormActivity;
import com.headwayagent.salesadviser_headwaygms.activity.MyOrdersActivity;
import com.headwayagent.salesadviser_headwaygms.activity.MyProfileActivity;
import com.headwayagent.salesadviser_headwaygms.activity.OrderDetailsActivity;
import com.headwayagent.salesadviser_headwaygms.activity.ServiceManActivity;
import com.headwayagent.salesadviser_headwaygms.activity.TotalDirectCommisionActivity;
import com.headwayagent.salesadviser_headwaygms.activity.TotalIndirectCommissionActivity;
import com.headwayagent.salesadviser_headwaygms.activity.ViewDSRActivity;
import com.headwayagent.salesadviser_headwaygms.models.CardItem_Hodel_List;
import com.headwayagent.salesadviser_headwaygms.models.Cda_model;

import java.util.ArrayList;

public class Cda_Adapter extends RecyclerView.Adapter<Cda_Adapter.ViewHolder> {
    private ArrayList<Cda_model>cda_models;
    private Context context;

    public Cda_Adapter(ArrayList<Cda_model> cda_models, Context context) {
        this.cda_models = cda_models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cda_layout,parent,false);
        return new Cda_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animationdashboard));
        final String value = cda_models.get(position).getName();
        holder.textView.setText(value);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (value.equals("CDA Registration")) {
                    Intent intent = new Intent(context, Registeration_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                if (value.equals("My Order")) {
                    Intent intent = new Intent(context, MyOrdersActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "1");
                    context.startActivity(intent);
                }
//                if (value.equals("New Order")) {
//                    Intent intent = new Intent(context, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("type", "1");
//                    context.startActivity(intent);
 //               }
//                if(value.equals("Downline"))
//                {  Intent intent = new Intent(context, Activ_Inactiv.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("type", "2");
//                    context.startActivity(intent);
//
//                }









            }
        });


    }

    @Override
    public int getItemCount() {
        return cda_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView,textView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView =itemView.findViewById(R.id.textvieww1);



        }
    }
}
