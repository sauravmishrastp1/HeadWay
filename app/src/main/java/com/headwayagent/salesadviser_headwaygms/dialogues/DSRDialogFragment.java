package com.headwayagent.salesadviser_headwaygms.dialogues;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.headwayagent.salesadviser_headwaygms.MainActivity;
import com.headwayagent.salesadviser_headwaygms.R;


public class DSRDialogFragment extends DialogFragment {

    private View rootView;

    private CardView placeorder;
    private CardView notplaceorder;
    private String pid,quantity,name,email,phone,address1,address2,city,state,pin,alternatephone,user_id;

    public DSRDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dsr_option_dialogue, container);
        placeorder=rootView.findViewById(R.id.school_attendence);
        notplaceorder=rootView.findViewById(R.id.coaching_attendence);


        setDialog();

        Bundle  extras=getArguments();
        name=extras.getString("name");
        email=extras.getString("email");
        phone=extras.getString("phone");
        address1=extras.getString("address1");
        address2=extras.getString("address2");
        city=extras.getString("city");
        state=extras.getString("state");
        pin=extras.getString("pin");
        alternatephone=extras.getString("alternate_phone");

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type","dsr");
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("address1", address1);
                intent.putExtra("address2", address2);
                intent.putExtra("city", city);
                intent.putExtra("state", state);
                intent.putExtra("pin", pin);
                intent.putExtra("alternate_phone", alternatephone);

                startActivity(intent);

            }
        });


        notplaceorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type","notplace");
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("address1", address1);
                intent.putExtra("address2", address2);
                intent.putExtra("city", city);
                intent.putExtra("state", state);
                intent.putExtra("pin", pin);
                intent.putExtra("alternate_phone", alternatephone);

                startActivity(intent);

            }
        });

        return rootView;
    }

    private void setDialog() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().setCanceledOnTouchOutside(true);
    }





    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
