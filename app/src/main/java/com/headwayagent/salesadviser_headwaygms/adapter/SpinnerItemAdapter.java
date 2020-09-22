package com.headwayagent.salesadviser_headwaygms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.models.SpinnerItemModel;
import com.headwayagent.salesadviser_headwaygms.models.Spinner_ItemModel;

import java.util.ArrayList;

public class SpinnerItemAdapter extends ArrayAdapter {


    public SpinnerItemAdapter(Context context, ArrayList<SpinnerItemModel>customList) {

        super(context,  0, customList);
    }


    @Override
    public View getView(int position,View convertView,  ViewGroup parent) {

        if (convertView==null)
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner_layout,null);
        }

        SpinnerItemModel spinnerItemModel= (SpinnerItemModel) getItem(position);

        TextView   textView=convertView.findViewById(R.id.title1);

        if (spinnerItemModel!=null) {
            textView.setText(spinnerItemModel.getSpinnerItemName());
        }

        return convertView;


    }

    @Override
    public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
        if (convertView==null)
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dropdown_layout,null);
        }

        SpinnerItemModel spinnerItemModel= (SpinnerItemModel) getItem(position);

        TextView   textView=convertView.findViewById(R.id.title);

        if (spinnerItemModel!=null) {
            textView.setText(spinnerItemModel.getSpinnerItemName());
        }

        return convertView;
    }
}
