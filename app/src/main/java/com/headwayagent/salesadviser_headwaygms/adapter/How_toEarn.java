package com.headwayagent.salesadviser_headwaygms.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.Webview;
import com.headwayagent.salesadviser_headwaygms.models.CardItem_Hodel_List;
import com.headwayagent.salesadviser_headwaygms.models.Howto_earn_model;

import java.util.ArrayList;

public class How_toEarn extends RecyclerView.Adapter<How_toEarn.ViewHolder> {

    public How_toEarn(ArrayList<Howto_earn_model> cardItem_hodel_lists, Context context) {
        this.cardItem_hodel_lists = cardItem_hodel_lists;
        this.context = context;
    }

    private ArrayList<Howto_earn_model>cardItem_hodel_lists;
    private Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.howtpearn_layout,parent,false);
        return new How_toEarn.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        holder.imageView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animationdashboard));
//        holder.textView2.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animationdashboard));
        holder.textView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animationdashboard));
        String name = cardItem_hodel_lists.get(position).getName();
        String link =cardItem_hodel_lists.get(position).getLink();
        Spanned finallink= Html.fromHtml("<a href='"+link+"'>Click Here</a>");
        holder.textView.setMovementMethod(LinkMovementMethod.getInstance());
        holder.textView.setText(finallink);
        //holder.webView.(price);
        holder.webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Webview.class);
                intent.putExtra("link",cardItem_hodel_lists.get(position).getLink());
                context.startActivity(intent);

            }
        });
       // holder.imageView.setBackgroundResource(dimage);
    }

    @Override
    public int getItemCount() {
        return cardItem_hodel_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        WebView webView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.wevview);
            textView = itemView.findViewById(R.id.tetvie2);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.getSettings().setSupportMultipleWindows(true);
        }


    }
}
