package com.headwayagent.salesadviser_headwaygms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.models.Spinner_ItemModel;
import com.headwayagent.salesadviser_headwaygms.models.State_Model;

import java.util.ArrayList;

public class State_Adapter extends ArrayAdapter {
    public State_Adapter(Context context, ArrayList<State_Model> customList) {

        super(context,  0, customList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner_layout,null);
        }

        State_Model spinnerItemModel= (State_Model) getItem(position);


        TextView textView=convertView.findViewById(R.id.title1);

        if (spinnerItemModel!=null) {

            textView.setText(spinnerItemModel.getStatename());

        }

        return convertView;


    }

    @Override
    public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
        if (convertView==null)
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.customdropdown_layout,null);
        }

        State_Model spinnerItemModel= (State_Model) getItem(position);

        TextView   textView=convertView.findViewById(R.id.title);

        if (spinnerItemModel!=null) {
            textView.setText(spinnerItemModel.getStatename());

        }

        return convertView;
    }
}
