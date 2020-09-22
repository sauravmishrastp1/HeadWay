package com.headwayagent.salesadviser_headwaygms.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.adapter.DashBoardAdapter;
import com.headwayagent.salesadviser_headwaygms.models.DashBoardModel;

import java.util.ArrayList;
import java.util.List;

public class ServiceDashBoardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<DashBoardModel> dashBoardModelList;

    Toolbar toolbar;
    TextView totalservice_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_dash_board);

        recyclerView=findViewById(R.id.sales_recyclerview);
        toolbar=findViewById(R.id.toolbarservice);
        totalservice_tv=findViewById(R.id.servicesman_total_services_tv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DASHBOARD");

        startAnimation();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        dashBoardModelList=new ArrayList<>();
        dashBoardModelList.add(new DashBoardModel("NEW SERVICES",R.drawable.serviceimg,"",R.drawable.gradient2));
        dashBoardModelList.add(new DashBoardModel("MY  SERVICES",R.drawable.waranty,"",R.drawable.gradient4));
       // dashBoardModelList.add(new DashBoardModel("CHECK WARANTY",R.drawable.serviceimg));
        dashBoardModelList.add(new DashBoardModel("MY PROFILE",R.drawable.waranty,"",R.drawable.slider_banner_background));



        DashBoardAdapter dashBoardAdapter = new DashBoardAdapter(dashBoardModelList, getApplicationContext(),"service");
        recyclerView.setAdapter(dashBoardAdapter);
        dashBoardAdapter.notifyDataSetChanged();
    }

    private void startAnimation() {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.total_service_anim);
        a.reset();
        totalservice_tv.clearAnimation();
        totalservice_tv.startAnimation(a);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.profile:
                startActivity(new Intent(ServiceDashBoardActivity.this,MyProfileActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
