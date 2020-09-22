package com.headwayagent.salesadviser_headwaygms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.activity.LoginActivity;
import com.headwayagent.salesadviser_headwaygms.activity.MyProfileActivity;
import com.headwayagent.salesadviser_headwaygms.activity.SalesDashBoardActivity;
import com.headwayagent.salesadviser_headwaygms.adapter.Cda_Adapter;
import com.headwayagent.salesadviser_headwaygms.adapter.DashBoardAdapter;
import com.headwayagent.salesadviser_headwaygms.adapter.DashBoard_Adapter;
import com.headwayagent.salesadviser_headwaygms.adapter.How_toEarn;
import com.headwayagent.salesadviser_headwaygms.adapter.Virtical_Adapter;
import com.headwayagent.salesadviser_headwaygms.models.CardItem_Hodel_List;
import com.headwayagent.salesadviser_headwaygms.models.Cda_model;
import com.headwayagent.salesadviser_headwaygms.models.DashBoardModel;
import com.headwayagent.salesadviser_headwaygms.models.Howto_earn_model;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fragment.SideMenuFragment;

public class SalesDashbBoard extends AppCompatActivity {

   private RecyclerView recyclerView;
   private RecyclerView recyclerView2,recyclerView3,recyclerView4;
   private ArrayList<CardItem_Hodel_List>cartlist= new ArrayList<>();
   private ArrayList<CardItem_Hodel_List>cardItem_hodel_lists = new ArrayList<>();
   private ArrayList<Cda_model>cda_models = new ArrayList<>();
   private ArrayList<Howto_earn_model>howto_earn_models = new ArrayList<>();
   private ImageView textview,menu;
    private ImageView sidemenu;
    private String name;
    private String ain,ainn,namee;
    private TextView profilename,ainnum;
    private String Total_income,Last_withdrawal,Available_balance,Roll_up_no,Roll_up_balance,Tds,
                   Directs,Active_team,Inactive_team,Team_size,Direct_commission,Leadership_commission,Promotional_commission,Rank_commission;
    private RatingBar ratingBar;
    private String Stars;
    private ProgressBar progressBar;
    private View parentview;
    String html = "<a href=\"http://yourdomain.com\">Your Domain Name</a>";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_dashb_board);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView2 = findViewById(R.id.grid);
        recyclerView3 = findViewById(R.id.recyclewview3);
        recyclerView4 = findViewById(R.id.howtoearn);
        textview = findViewById(R.id.view);
        profilename = findViewById(R.id.profilename);
        ainnum = findViewById(R.id.ainno);
       menu = findViewById(R.id.view6);
       ratingBar = findViewById(R.id.rating3);
       progressBar = findViewById(R.id.progressbar1);
       parentview=findViewById(R.id.parentview);



        if (!SharedPrefManager.getInstance(this).isCheckIn())
        {
            startActivity(new Intent(SalesDashbBoard.this, LoginActivity.class));
            finish();
        }
        parentview.setVisibility(View.GONE);
        ain=SharedPrefManager.getInstance(this).getUser().getAin_number();
        namee = SharedPrefManager.getInstance(this).getUser().getFull_name();

        getDashboardData();

        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalesDashbBoard.this,MyProfileActivity.class);
                intent.putExtra("type","profile");
                intent.putExtra("ain",ain);
                //intent.putExtra("ain",ain);

                startActivity(intent);
            }
        });
      //  Toast.makeText(this, "statrs="+Stars, Toast.LENGTH_SHORT).show();

             //ratingBar.setRating(Float.parseFloat(Stars));
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SalesDashbBoard.this);
                View mView = getLayoutInflater().inflate(R.layout.menu_box, null);
                RelativeLayout button = mView.findViewById(R.id.bannercard);
                RelativeLayout button1 = mView.findViewById(R.id.bannercard22);
                mView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animationdashboard));


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(SalesDashbBoard.this, SalesDashbBoard.class));


                    }

                });
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(SalesDashbBoard.this, MyProfileActivity.class));




                    }

                });

            }
        });

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SalesDashbBoard.this);
        View mView = getLayoutInflater().inflate(R.layout.alertbox_for_testing, null);
        Button button = mView.findViewById(R.id.canclebtn);
        mView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animationdashboard));
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               dialog.dismiss();

            }

        });




        cda_models = new ArrayList<>();
        cda_models.add(new Cda_model("CDA Registration"));
        cda_models.add(new Cda_model("My Order"));
       // cda_models.add(new Cda_model("New Order"));
        //cda_models.add(new Cda_model("Downline"));
        @SuppressLint("WrongConstant") LinearLayoutManager horizontalLayoutManager2
                = new LinearLayoutManager(SalesDashbBoard.this, LinearLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(horizontalLayoutManager2);
        Cda_Adapter cda_adapter = new Cda_Adapter(cda_models,SalesDashbBoard.this);
        recyclerView3.setAdapter(cda_adapter);

        howto_earn_models = new ArrayList<>();
        howto_earn_models.add(new Howto_earn_model ("https://youtu.be/WWiqIwp9BpE","Affiliates"));
       // howto_earn_models.add(new Howto_earn_model("https://www.youtube.com/watch?v=mMmGV-yapwE","Did YouTube"));
        // cda_models.add(new Cda_model("New Order"));
        //cda_models.add(new Cda_model("Downline"));
        @SuppressLint("WrongConstant") LinearLayoutManager horizontalLayoutManager3
                = new LinearLayoutManager(SalesDashbBoard.this, LinearLayoutManager.VERTICAL, false);
        recyclerView4.setLayoutManager(horizontalLayoutManager3);
        How_toEarn cda_adapter1 = new How_toEarn(howto_earn_models,SalesDashbBoard.this);
        recyclerView4.setAdapter(cda_adapter1);
         }



    private void getDashboardData()
    {
        progressBar.setVisibility(View.VISIBLE);

        String url="https://www.headwaygms.com/api/home?ain="+ain;
       // Toast.makeText(this, "ain"+ain, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);

                            String message = obj.getString("msg");
                            //Toast.makeText(SalesDashbBoard.this, ""+response, Toast.LENGTH_SHORT).show();

                            if (message.equals("Success")) {

                               JSONObject user = obj.getJSONObject("user");
                                JSONObject userstates = obj.getJSONObject("user_stats");
                                String active = obj.getString("active");
                                String iactive = obj.getString("inactive");

                                //Toast.makeText(SalesDashbBoard.this, ""+response, Toast.LENGTH_SHORT).show();
                                String Namee = user.getString("name");
                                String  Ainn = user.getString("ain");

                               String email = user.getString("email");
                                Total_income = userstates.getString("total_income");
                                Last_withdrawal = userstates.getString("last_withdrawal");
                                Available_balance = userstates.getString("available_balance");
                                Roll_up_no= userstates.getString("roll_up_no");
                                Roll_up_balance = userstates.getString("roll_up_balance");
                                Tds = userstates.getString("tds");
                                Directs = userstates.getString("directs");
                                Stars = userstates.getString("stars");
                                Active_team = userstates.getString("active_team");
                               Inactive_team = userstates.getString("inactive_team");
                               Team_size = userstates.getString("team_size");
                                Direct_commission = userstates.getString("direct_commission");
                                Leadership_commission = userstates.getString("leadership_commission");
                                Promotional_commission = userstates.getString("promotional_commission");
                                Rank_commission = userstates.getString("rank_commission");

                                profilename.setText(Namee);
                                ainnum.setText(Ainn);

                                ratingBar.setRating(Float.parseFloat(Stars));

                                cartlist=new ArrayList<>();
                                cartlist.add(new CardItem_Hodel_List("Direct",Directs,R.drawable.directindirect));
                                cartlist.add(new CardItem_Hodel_List("Team",Team_size,R.drawable.ic_people_black_24dp));
                                cartlist.add(new CardItem_Hodel_List("Active Team",active,R.drawable.ic_people_black_24dp));
                                cartlist.add(new CardItem_Hodel_List("Inactive Team",iactive,R.drawable.ic_people_black_24dp));

                                // cartlist.add(new CardItem_Hodel_List("Stars","0",R.drawable.ic_star_border_black_24dp));
                                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(SalesDashbBoard.this, 2);

                                recyclerView2.setLayoutManager(gridLayoutManager1);
                                Virtical_Adapter gridProductAdapter = new Virtical_Adapter(cartlist, SalesDashbBoard.this);
                                recyclerView2.setAdapter(gridProductAdapter);
                                gridProductAdapter.notifyDataSetChanged();
                                cardItem_hodel_lists=new ArrayList<>();
                                cardItem_hodel_lists.add(new CardItem_Hodel_List("Total Income","\u20B9"+Total_income,R.drawable.wallet));
                                cardItem_hodel_lists.add(new CardItem_Hodel_List("Avl Blance","\u20B9"+Available_balance,R.drawable.wallet));
                                cardItem_hodel_lists.add(new CardItem_Hodel_List("Direct commission","\u20B9"+Direct_commission,R.drawable.wallet));
                                cardItem_hodel_lists.add(new CardItem_Hodel_List("Promotional commission","\u20B9"+Promotional_commission,R.drawable.wallet));
                                cardItem_hodel_lists.add(new CardItem_Hodel_List("Leadership commission","\u20B9"+Leadership_commission,R.drawable.wallet));
                                cardItem_hodel_lists.add(new CardItem_Hodel_List("Rank commission","\u20B9"+Rank_commission,R.drawable.wallet));
                                cardItem_hodel_lists.add(new CardItem_Hodel_List("Roll up Blance","\u20B9"+Roll_up_balance,R.drawable.wallet));
                                cardItem_hodel_lists.add(new CardItem_Hodel_List("No of Rollups",Roll_up_no,R.drawable.wallet));
                                cardItem_hodel_lists.add(new CardItem_Hodel_List("Last Withdrawal",Last_withdrawal,R.drawable.wallet));
                                cardItem_hodel_lists.add(new CardItem_Hodel_List("  TDS","\u20B9"+Tds,R.drawable.wallet));










                                LinearLayoutManager horizontalLayoutManager
                                        = new LinearLayoutManager(SalesDashbBoard.this, LinearLayoutManager.HORIZONTAL, false);
                                recyclerView.setLayoutManager(horizontalLayoutManager);
                                DashBoard_Adapter dashBoard_adapter = new DashBoard_Adapter(cardItem_hodel_lists,SalesDashbBoard.this);
                                recyclerView.setAdapter(dashBoard_adapter);

                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);

                                gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                                recyclerView.setLayoutManager(gridLayoutManager);
                                gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                                parentview.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                             // Toast.makeText(SalesDashbBoard.this, ""+Stars, Toast.LENGTH_SHORT).show();



                            } else {
                                Toast.makeText(getApplicationContext(), "Dashoard Data Not Found "+obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SalesDashbBoard.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Server Not Responding  check your network connection", Toast.LENGTH_SHORT).show();
                      //  progressBar.setVisibility(View.GONE);
                    }
                }) {

        };



        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}

