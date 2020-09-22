package com.headwayagent.salesadviser_headwaygms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.activity.SalesDashBoardActivity;
import com.headwayagent.salesadviser_headwaygms.adapter.GridProductAdapter;
import com.headwayagent.salesadviser_headwaygms.models.GridProductDetailsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerview;

    Toolbar toolbar;

    RequestQueue requestQueue;
    public static ProgressBar progressBar;
    private String type;
    public static String applicantid;
    public static String pid,quantity,name,email,phone,address1,address2,city,state,pin,alternatephone,user_id;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.mainprogressbar);

        recyclerview=findViewById(R.id.products_recyclerview);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showProducts();
        user_id= SharedPrefManager.getInstance(this).getUser().getAin_number();

         Bundle extras=getIntent().getExtras();
        if (extras!=null) {

            type = extras.getString("type");
            Registeration_Activity.ain  = extras.getString("ain");
        }

//        if (type.equals("dsr"))
//        {
//            name=extras.getString("name");
//            email=extras.getString("email");
//            phone=extras.getString("phone");
//            address1=extras.getString("address1");
//            address2=extras.getString("address2");
//            city=extras.getString("city");
//            state=extras.getString("state");
//            pin=extras.getString("pin");
//            getSupportActionBar().setTitle("Products");
//            alternatephone=extras.getString("alternate_phone");
//
//        }
//
//        else if (type.equals("notplace")){
//
//            getSupportActionBar().setTitle("Choose Interested Product");
//            name=extras.getString("name");
//            email=extras.getString("email");
//            phone=extras.getString("phone");
//            address1=extras.getString("address1");
//            address2=extras.getString("address2");
//            city=extras.getString("city");
//            state=extras.getString("state");
//            pin=extras.getString("pin");
//            alternatephone=extras.getString("alternate_phone");
//        }
//
//        else {
//            getSupportActionBar().setTitle("Products");
//        }

   }


    private void showProducts()
    {

        progressBar.setVisibility(View.VISIBLE);

        String url="https://www.headwaygms.com/api/get-s-product?department=sales";

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(JSONObject response) {

                        try{

                            String msg=response.getString("msg");


                            if (msg.equals("Success")) {


                                JSONArray productsArray = response.getJSONArray("products");


////////////////////////////////////////////////////////////////////////////Products //////////////////////////////////////////////

                                List<GridProductDetailsModel>gridProductDetailsModels=new ArrayList<>();

                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerview.setLayoutManager(gridLayoutManager);

                                for (int y = 0; y < productsArray.length(); y++) {

                                    JSONObject jsonObject = productsArray.getJSONObject(y);

                                    String productimage = jsonObject.getString("image");

                                    gridProductDetailsModels.add(new GridProductDetailsModel(jsonObject.getString("id"),jsonObject.getString("product_name"),jsonObject.getString("description"),jsonObject.getString("price"),"http://headwaygms.com/"+productimage,jsonObject.getString("model_no")));

                                }

                                GridProductAdapter gridProductAdapter = new GridProductAdapter(gridProductDetailsModels, getApplicationContext(),type);
                                recyclerview.setAdapter(gridProductAdapter);
                                gridProductAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }

                            else {
                                Toast.makeText(getApplicationContext(), ""+response.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
//                                    output.setText(data);
                        }catch(JSONException e)
                        {e.printStackTrace();
                            progressBar.setVisibility(View.GONE);}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley","Error");
                        Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                    }
                }
        );
        requestQueue.add(jor);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent =  new Intent(MainActivity.this, SalesDashbBoard.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent =  new Intent(MainActivity.this, SalesDashbBoard.class);
        startActivity(intent);
        finish();
    }
}
