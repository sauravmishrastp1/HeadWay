package com.headwayagent.salesadviser_headwaygms.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.adapter.MyAdapter;

public class Activ_Inactiv extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    private String type,tYPE,ain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activ__inactiv);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        toolbar=findViewById(R.id.toolbar);

        Bundle extras=getIntent().getExtras();


            type = extras.getString("type");
            tYPE = extras.getString("type");
            ain = extras.getString("ain");

            if( tYPE.equals("teamzise")){
                tabLayout.addTab(tabLayout.newTab().setText("Active"));
                tabLayout.addTab(tabLayout.newTab().setText("Inactive"));

            }



        setHasOptionsMenu(true);
        toolbar.setTitle("Downline");
        if(type.equals("2")){
            tabLayout.addTab(tabLayout.newTab().setText("Active"));
            tabLayout.addTab(tabLayout.newTab().setText("Inactive"));

        }if(type.equals("1")){

            tabLayout.addTab(tabLayout.newTab().setText("Inactive"));
            tabLayout.addTab(tabLayout.newTab().setText("Active"));
        }



        final MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), getApplicationContext(), tabLayout.getTabCount(),type);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               finish();
            }
        });

      
    }





    private void setHasOptionsMenu(boolean b) {
    }
}

