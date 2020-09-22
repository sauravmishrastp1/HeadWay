package fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.headwayagent.salesadviser_headwaygms.MainActivity;
import com.headwayagent.salesadviser_headwaygms.R;

import static androidx.core.content.ContextCompat.startActivity;


public class SideMenuFragment extends BaseFragment  {


    private View rootView;
    private ImageView profilePic;
    private View logout;
    private View myorder;
    private View home;
    private View profile;
    private View deluxetiffin;
    private TextView usernametv,phonetv;

    private String name="N/A",phone="N/A";

    @Override
    protected int getFragmentLayout() {
        return R.layout.ly_sidebar;
    }

    public SideMenuFragment() {

    }

    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ly_sidebar, container, false);
        logout=rootView.findViewById(R.id.logout);
        profile=rootView.findViewById(R.id.profile);
        home=rootView.findViewById(R.id.home);
        deluxetiffin=rootView.findViewById(R.id.deluxetiffin);
        usernametv=rootView.findViewById(R.id.userName);
        phonetv=rootView.findViewById(R.id.userphone);
        myorder = rootView.findViewById(R.id.myorders);



        myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), MainActivity.class));

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // startActivity(new Intent(getActivity(), ProfileActivity.class));

            }
        });


        deluxetiffin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(), UpdateMealsActivity.class));
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Preference.getPreference(getActivity()).edit().clear().apply();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
      //  ApiManager.getProfile(getActivity(), String.valueOf(UserInfo.getUserId(getActivity())),this);
    }






}





