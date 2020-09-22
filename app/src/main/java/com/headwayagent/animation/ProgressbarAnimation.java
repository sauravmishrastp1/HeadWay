package com.headwayagent.animation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.headwayagent.salesadviser_headwaygms.SalesDashbBoard;
import com.headwayagent.salesadviser_headwaygms.activity.CheckInActivity;

public class ProgressbarAnimation extends Animation {

    private ProgressBar progressBar;
    private Context context;
    private TextView textView;
    private float from;
    private float to;


    public ProgressbarAnimation(ProgressBar progressBar, Context context, TextView textView, float from, float to) {
        this.progressBar = progressBar;
        this.context = context;
        this.textView = textView;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        float value=from+(to-from)*interpolatedTime;

        progressBar.setProgress((int)value);

        textView.setText((int)value+" %");

        if (value==to)
        {
            context.startActivity(new Intent(context, SalesDashbBoard.class));
            ((Activity)context).finish();

        }
    }
}
