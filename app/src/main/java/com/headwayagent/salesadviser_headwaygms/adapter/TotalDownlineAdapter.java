package com.headwayagent.salesadviser_headwaygms.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.activity.MyProfileActivity;
import com.headwayagent.salesadviser_headwaygms.models.TotalDownlineModel;

import java.util.List;

public class TotalDownlineAdapter extends RecyclerView.Adapter<TotalDownlineAdapter.ViewHolder> {

     List<TotalDownlineModel>totalDownlineModelList;
     Context context;

    public TotalDownlineAdapter(List<TotalDownlineModel> totalDownlineModelList, Context context) {
        this.totalDownlineModelList = totalDownlineModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.total_downline_item_layout,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.aintv.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        viewHolder.numberoforderstv.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        viewHolder.ain.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        viewHolder.number.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        String ainnumber=totalDownlineModelList.get(position).getAinnumber();
        String numberoforder=totalDownlineModelList.get(position).getNumberoforders();

        viewHolder.aintv.setText(ainnumber);
        viewHolder.numberoforderstv.setText(numberoforder);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, MyProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type","Active");
                intent.putExtra("ain",totalDownlineModelList.get(position).getNumberoforders());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  totalDownlineModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView aintv,numberoforderstv;
        TextView ain,number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            aintv=itemView.findViewById(R.id.aintv);
            numberoforderstv=itemView.findViewById(R.id.numberoforderstv);
            ain = itemView.findViewById(R.id.name1);
            number =itemView.findViewById(R.id.name2);
        }
    }
}
