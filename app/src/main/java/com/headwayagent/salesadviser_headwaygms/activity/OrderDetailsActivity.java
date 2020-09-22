package com.headwayagent.salesadviser_headwaygms.activity;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.BaseUrl;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.SalesDashbBoard;
import com.headwayagent.salesadviser_headwaygms.adapter.SpinnerItemAdapter;
import com.headwayagent.salesadviser_headwaygms.models.SpinnerItemModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

   private Spinner spinner;
   private ProgressDialog progressDialog;
   private ArrayList<SpinnerItemModel>list;
   private Button reschedulebtn;
   public static TextView rescheduleDateText,rescheduleTimeText;
   private String rescheduleTime;
   private String rescheduleDate;
   private String orderID;
   private String schedulingType="2";
   private String servPerName;
   private String servPerPhone;
   private String servPerImage;
   private String helPerName;
   private String helPerPhone;
   private String helvPerImage;
   private String delDate;
   private String delTime;
   private String installDate;
   private String installTime;
   private String ReasonOfCancel;
   private String myrescheduledate;
   private String myrescheduletime;
   private View instalationscheduleView;
   private TextView servive_person_name;
   private ImageView servive_person_image;
   private TextView servive_person_phone;
   private TextView health_person_name;
   private ImageView health_person_image;
   private TextView health_person_phone;
   private TextView deliveryDate;
   private TextView deliveryTime;
   private TextView InstallationDate;
   private String serperPhone;
   private String helperPhone;
    TextView InstallationTime;
    TextView cancelReasonTextView;
    Button    cancel_Btn;
    View view;
    View helperLayoutView;
    View deliveryschlayout;
    String type,TYPE;
    String ORDERid;
    private TextView orderTitletv,confirmtitletv,delivertitletv,installedtitletv;

    private ImageView orderIndicator,deliverIndicator,installedindicator,confirmindicator;

    private ImageView orderIndi;
    private String ReasonOfCancelService;
    private ProgressBar packProgressbar,confirmedprogress,progressdelivery;
    private ImageView productImage;
    private TextView productTitle,ProductQty,ProductPrice,OrderStatus;
    private Button completeOrderBtn;
    private String backtype;
    private View servicemanlayout,cancelorderlayout,reschedulelayout;
    private ProgressBar progressBar;
    private String Phoneno;
    private String otp;
    private String Productname;
    private Button instaled;
    String productstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        spinner=findViewById(R.id.reschedule_spinner);
        productImage=findViewById(R.id.product_image);
        productTitle=findViewById(R.id.product_title);
        ProductPrice=findViewById(R.id.product_price);
        ProductQty=findViewById(R.id.product_quantity);
        OrderStatus=findViewById(R.id.orderd_title);
        completeOrderBtn=findViewById(R.id.complete_button);
        servicemanlayout=findViewById(R.id.servicemanlayoutt);
        cancelorderlayout=findViewById(R.id.canorderlayout);
        reschedulelayout=findViewById(R.id.reschedulelayout);
        deliverIndicator=findViewById(R.id.deliverindicator);
        installedindicator=findViewById(R.id.installedindicator);
        confirmindicator=findViewById(R.id.confirmindicator);
        confirmedprogress=findViewById(R.id.confirmprogress);
        rescheduleDateText=findViewById(R.id.service_reschedule_day);
        rescheduleTimeText=findViewById(R.id.service_reschedule_time);
        reschedulebtn=findViewById(R.id.reschedule_button);
        helperLayoutView=findViewById(R.id.helperlayout);
        health_person_name=findViewById(R.id.healthPersonName);
        health_person_phone=findViewById(R.id.healthPersonMobile);
        health_person_image=findViewById(R.id.healthPersonImageview);
        servive_person_name=findViewById(R.id.servManNameTextview);
        servive_person_phone=findViewById(R.id.servManTextview);
        servive_person_image=findViewById(R.id.servManImageview);
        deliveryDate=findViewById(R.id.deliverDateTextview);
        deliveryTime=findViewById(R.id.deliverTimeTextview);
        InstallationDate=findViewById(R.id.installDayTextView);
        InstallationTime=findViewById(R.id.installtimetextview);
        cancelReasonTextView=findViewById(R.id.cancel_order_reason);
        cancel_Btn=findViewById(R.id.cancel_order_button);
        view=findViewById(R.id.servmanlayout);
        deliveryschlayout=findViewById(R.id.deliveryscheduleayout);
        orderIndi=findViewById(R.id.orderdindicator);
        instalationscheduleView=findViewById(R.id.installationSchedule);
        packProgressbar=findViewById(R.id.order_oacked_progress);
        progressdelivery=findViewById(R.id.deliverprogress1);
        progressBar=findViewById(R.id.progressbar);
        orderTitletv=findViewById(R.id.orderd_title);
        confirmtitletv=findViewById(R.id.confirm_title);
        delivertitletv=findViewById(R.id.delivery_title);
        installedtitletv=findViewById(R.id.installed_title);
        instaled = findViewById(R.id.instlation);


        Bundle bundle;
        bundle=getIntent().getExtras();

        if (bundle!=null) {

            orderID = bundle.getString("Orderid");
            type = bundle.getString("intent_type");
            Productname = bundle.getString("name");
        }





        if (type.equals("d-boy"))
        {
           completeOrderBtn.setVisibility(View.VISIBLE);
           servicemanlayout.setVisibility(View.GONE);
           cancelorderlayout.setVisibility(View.GONE);
        }

        if (type.equals("sales"))
        {
            backtype=bundle.getString("backtype");
            reschedulelayout.setVisibility(View.GONE);
        }
        if(type.equals("direct")){


            backtype=bundle.getString("backtype");
            reschedulelayout.setVisibility(View.GONE);
        }
        SetOrder(orderID);
        //setOrderDetailsData(orderID);


        list=getList();

        SpinnerItemAdapter spinnerItemAdapter=new SpinnerItemAdapter(this,list);
        if (spinner!=null) {
            spinner.setAdapter(spinnerItemAdapter);
            spinner.setOnItemSelectedListener(this);
        }


        completeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderDetailsActivity.this, "pressed="+orderID, Toast.LENGTH_SHORT).show();
              verifyotp();
            }
        });

        instaled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                veryfyinstlation();
            }
        });



        reschedulebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equals("service"))
                {
                    rescheduleService(orderID);
                }

                else {

                    rescheduleOrderOrService(orderID);
                }
            }
        });


        cancel_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equals("service"))
                {
                    cancelService(orderID);
                }

                else {

                    cancelOrder(orderID);
                }
            }
        });

        rescheduleDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DatePickerFragment2();
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        rescheduleTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new TimePickerFragment2();
                newFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });


        servive_person_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",serperPhone , null));
                startActivity(intent);
            }
        });


        health_person_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",helPerPhone , null));
                startActivity(intent);
            }
        });

    }

    private void veryfyinstlation() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setIcon(R.drawable.headwaygmslogo);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String url ="https://www.headwaygms.com/api/cda-otp?order_id=143&type=instlation";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("msg");
                            String status = obj.getString("status");
                            String Otp = obj.getString("otp");

                            if (status.equals("200")) {
                                // Toast.makeText(PaymentDeatilsFormActivity.this, "otpsend"+message, LENGTH_SHORT).show();
                                Intent intent=new Intent(OrderDetailsActivity.this,OtpVerificationActivity.class);
                                intent.putExtra("order_id",orderID);
                                intent.putExtra("type","3");
                                intent.putExtra("otp",Otp);
                                startActivity(intent);
                                progressDialog.dismiss();

                            } else {
                                Toast.makeText(OrderDetailsActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OrderDetailsActivity.this, "exception"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderDetailsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("order_id", "143");
//                params.put("type","delivered");
//
//                return params;
//            }
        };

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(OrderDetailsActivity.this).addToRequestQueue(stringRequest);




    }


    public void onBackPressed() {

        if(backtype.equals("1"))
        {
            startActivity(new Intent(OrderDetailsActivity.this, SalesDashbBoard.class));

        }

        else {

            finish();
        }
    }

    private ArrayList<SpinnerItemModel> getList() {
        list=new ArrayList<>();

        if (type.equals("d-boy"))
        {
          //  list.add(new SpinnerItemModel("Delivery",R.drawable.android));
        }

        else {
            list.add(new SpinnerItemModel("Installation", R.drawable.android));
            list.add(new SpinnerItemModel("Delivery", R.drawable.ic_action_name));
        }
        return list;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SpinnerItemModel model= (SpinnerItemModel) parent.getSelectedItem();

        if (model.getSpinnerItemName().equals("Delivery"))
        {
            schedulingType="1";
        }

        else
        {
            schedulingType="2";
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void rescheduleOrderOrService(final String orderID) {

        rescheduleDate = rescheduleDateText.getText().toString();
        rescheduleTime = rescheduleTimeText.getText().toString();

      if (rescheduleDate.equals("DD/MM/YY")) {
          Toast.makeText(this, "Please Choose Date", Toast.LENGTH_SHORT).show();
      } else if (rescheduleTime.equals("HH:MM")) {
          Toast.makeText(this, "Please Choose Time", Toast.LENGTH_SHORT).show();
      } else {
          progressDialog = new ProgressDialog(this);
          progressDialog.setMessage("Please wait...");
          progressDialog.setTitle("Rescheduling");
          progressDialog.setIcon(R.drawable.headwaygmslogo);
          progressDialog.show();


          StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.URL_RESCHEDULE,
                  new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {

                          try {

                              JSONObject obj = new JSONObject(response);

                              //Toast.makeText(OrderDetailsActivity.this, "response="+response, Toast.LENGTH_SHORT).show();

                              String message = obj.getString("msg");

                              Toast.makeText(OrderDetailsActivity.this, "" + message, Toast.LENGTH_SHORT).show();


                              if (message.equals("Success")) {

                                  JSONObject jsonObject = obj.getJSONObject("orders");

                                    helPerName =  jsonObject.getString("health_ad_ain");
  //                                  helvPerImage = jsonObject.getString("health_ad_image");
//                                  helPerPhone = jsonObject.getString("h_ad_phone");
                                    servPerName = jsonObject.getString("ser_per_ain");
//                                  servPerImage = jsonObject.getString("s_p_image");
//                                  servPerPhone = jsonObject.getString("s_p_phone");
                                  delDate = jsonObject.getString("delivery_date");
                                  delTime = jsonObject.getString("delivery_time");
                                  installDate = jsonObject.getString("insta_date");
                                  installTime = jsonObject.getString("insta_time");


                                  if (schedulingType.equals("1")) {


                                      deliveryTime.setText(delTime);
                                      deliveryDate.setText(delDate);
                                      servive_person_name.setText(servPerName);
                                      servive_person_phone.setText(servPerPhone);
                                      Glide.with(getApplicationContext()).load(servPerImage).placeholder(R.drawable.headwaygmslogo).into(servive_person_image);

                                  } else {
                                      InstallationTime.setText(installTime);
                                      InstallationDate.setText(installDate);
                                  }

                                  health_person_name.setText(helPerName);
                                  health_person_phone.setText(helPerPhone);
                                  servive_person_name.setText(servPerName);
                                  servive_person_phone.setText(servPerPhone);
                                  Glide.with(getApplicationContext()).load(servPerImage).placeholder(R.drawable.headwaygmslogo).into(servive_person_image);
                                  Glide.with(getApplicationContext()).load(helvPerImage).placeholder(R.drawable.headwaygmslogo).into(health_person_image);

                                  progressDialog.dismiss();

                              } else {
                                  Toast.makeText(OrderDetailsActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                     progressDialog.dismiss();
                              }
                          } catch (JSONException e) {
                              e.printStackTrace();
                              progressDialog.dismiss();
                          }
                      }
                  },
                  new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          Toast.makeText(OrderDetailsActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                          progressDialog.dismiss();
                      }
                  }) {
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                  Map<String, String> params = new HashMap<>();
                  params.put("order_id", orderID);
                  params.put("reschedule_time", rescheduleTime);
                  params.put("reschedule_date", rescheduleDate);
                  params.put("type", schedulingType);
                  return params;
              }
          };

          VolleySingleton.getInstance(OrderDetailsActivity.this).addToRequestQueue(stringRequest);


      }
  }


    private  void cancelOrder(final String orderID) {
        ReasonOfCancel = cancelReasonTextView.getText().toString();

        if (TextUtils.isEmpty(ReasonOfCancel)) {
            Toast.makeText(this, "Please give some reason", Toast.LENGTH_SHORT).show();
        } else {

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setTitle("Canceling Order");
            progressDialog.setIcon(R.drawable.headwaygmslogo);
            progressDialog.show();

            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.URL_CANCEL_ORDER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONObject obj = new JSONObject(response);

                                //Toast.makeText(OrderDetailsActivity.this, "response="+response, Toast.LENGTH_SHORT).show();

                                String message = obj.getString("msg");


                                if (message.equals("Success")) {

                                    JSONObject jsonObject = obj.getJSONObject("orders");


                                    String orderStatus = jsonObject.getString("order_status");

                                    Toast.makeText(OrderDetailsActivity.this, "" + orderStatus, Toast.LENGTH_SHORT).show();
                                    OrderStatus.setText(orderStatus);
                                    progressDialog.dismiss();
                                } else {

                                    Toast.makeText(OrderDetailsActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(OrderDetailsActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("order_id", orderID);
                    params.put("reschedule_time", ReasonOfCancel);

                    return params;
                }
            };

            queue.getCache().clear();
            VolleySingleton.getInstance(OrderDetailsActivity.this).addToRequestQueue(stringRequest);


        }
    }


    private  void setOrderDetailsData(final String orderID)
    {

        progressBar.setVisibility(View.VISIBLE);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Placing..");
        progressDialog.setTitle("Please Wait...");
        progressDialog.setIcon(R.drawable.headwaygmslogo);

        String url="https://www.headwaygms.com/api/orders-details?order_id="+orderID;

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.getCache().clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,

                new Response.Listener<String>() {

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject=new JSONObject(response);


                            if (jsonObject.getString("msg").equals("Success")) {

                                JSONObject object = jsonObject.getJSONObject("orders");
                                JSONObject serviceobj=jsonObject.getJSONObject("service");
                                JSONObject distributorobj=jsonObject.getJSONObject("distributor");

                                String img = object.getString("product_image");
                                String title = object.getString("title");
                                String price = object.getString("price");
                                String qty = object.getString("quantity");
                                String order_status = object.getString("order_status");
                                String healthPerName = distributorobj.getString("full_name");
                                String helPerImg = distributorobj.getString("pic");
                                helperPhone = distributorobj.getString("phone");
                                String ser_per_name = serviceobj.getString("full_name");
                                String serperImg = serviceobj.getString("pic");
                                serperPhone = serviceobj.getString("phone");
                                String deliveryDateres = object.getString("delivery_date");
                                String DeliveyyTimee = object.getString("delivery_time");
                                String installDate = object.getString("insta_date");
                                String installTime = object.getString("insta_time");
                                productstatus = object.getString("product_status");

                                Glide.with(OrderDetailsActivity.this).load("http://headwaygms.com/" + img).placeholder(R.drawable.headwaygmslogo).into(productImage);
                                productTitle.setText(title);
                                ProductPrice.setText("\u20B9 " + price + "/-");
                                ProductQty.setText("Qty :" + qty);
                               // OrderStatus.setText(order_status);

                                Toast.makeText(OrderDetailsActivity.this, "is install="+productstatus, Toast.LENGTH_SHORT).show();

                                if (productstatus.equals("1")){

                                    completeOrderBtn.setVisibility(View.GONE);
                                    instaled.setVisibility(View.VISIBLE);

                                }else {
                                    instaled.setVisibility(View.GONE);
                                    completeOrderBtn.setVisibility(View.VISIBLE);
                                }

                                if (order_status.equals("1")) {
                                    orderIndi.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    //packProgressbar.setBackgroundColor(Color.parseColor("#4BB543"));
                                } else if (order_status.equals("2")) {
                                    orderIndi.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    packProgressbar.setBackgroundColor(Color.parseColor("#4BB543"));
                                    confirmindicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                } else if (order_status.equals("3")) {


                                    orderIndi.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    packProgressbar.setBackgroundColor(Color.parseColor("#4BB543"));
                                    confirmindicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    confirmedprogress.setBackgroundColor(Color.parseColor("#4BB543"));
                                    deliverIndicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));

                                } else if (order_status.equals("4")) {


                                    orderIndi.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    packProgressbar.setBackgroundColor(Color.parseColor("#4BB543"));
                                    confirmindicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    confirmedprogress.setBackgroundColor(Color.parseColor("#4BB543"));
                                    deliverIndicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    deliverIndicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    installedindicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    progressdelivery.setBackgroundColor(Color.parseColor("#4BB543"));
                                } else {
                                    orderIndi.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));

                                    packProgressbar.setVisibility(View.GONE);
                                    confirmindicator.setVisibility(View.GONE);
                                    confirmedprogress.setVisibility(View.GONE);
                                    deliverIndicator.setVisibility(View.GONE);
                                    installedindicator.setVisibility(View.GONE);
                                    progressdelivery.setVisibility(View.GONE);

                                    orderTitletv.setText("Cancelled");
                                    confirmtitletv.setVisibility(View.GONE);
                                    delivertitletv.setVisibility(View.GONE);
                                    installedtitletv.setVisibility(View.GONE);
                                }


                                health_person_name.setText(healthPerName);
                                health_person_phone.setText(helperPhone);
                                Picasso.get()
                                        .load("https://www.headwaygms.com/"+helPerImg)
                                        .placeholder(R.drawable.profileimageicon)
                                        .error(R.drawable.profileimageicon)
                                        .into(health_person_image);

                                servive_person_name.setText(ser_per_name);
                                servive_person_phone.setText(serperPhone);

                                Picasso.get()
                                        .load("https://www.headwaygms.com/"+serperImg)
                                        .placeholder(R.drawable.profileimageicon)
                                        .error(R.drawable.profileimageicon)
                                        .into(servive_person_image);
                                deliveryDate.setText(deliveryDateres);
                                deliveryTime.setText(DeliveyyTimee);
                                InstallationDate.setText(installDate);
                                InstallationTime.setText(installTime);


                                if (installDate.equals("null") || installTime.equals("null")) {
                                    instalationscheduleView.setVisibility(View.GONE);
                                    progressDialog.dismiss();
                                }


                                if (ser_per_name.equals("") || serperPhone.equals("")) {
                                    servicemanlayout.setVisibility(View.GONE);
                                    progressDialog.dismiss();
                                }

                                if (healthPerName.equals("") || helperPhone.equals("")) {
                                    helperLayoutView.setVisibility(View.GONE);
                                }


                                progressBar.setVisibility(View.GONE);
                            }

                            else {

                                Toast.makeText(OrderDetailsActivity.this, ""+jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                            progressBar.setVisibility(View.GONE);

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(OrderDetailsActivity.this, "Server Not Responding", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }) {

        };

        queue.getCache().clear();
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);



    }
    private  void SetOrder(String orderID)
    {

        progressBar.setVisibility(View.VISIBLE);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Placing..");
        progressDialog.setTitle("Please Wait...");
        progressDialog.setIcon(R.drawable.headwaygmslogo);

        String url="https://www.headwaygms.com/api/orders-details?order_id="+orderID;

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.getCache().clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,

                new Response.Listener<String>() {

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject=new JSONObject(response);


                            if (jsonObject.getString("msg").equals("Success")) {

                                productstatus = jsonObject.getString("product_status");


                                JSONObject object = jsonObject.getJSONObject("orders");
                                JSONObject serviceobj=jsonObject.getJSONObject("service");
                                JSONObject distributorobj=jsonObject.getJSONObject("distributor");

                                String img = object.getString("product_image");
                                String title = object.getString("title");
                                String price = object.getString("price");
                                String qty = object.getString("quantity");
                                String order_status = object.getString("order_status");
                                String healthPerName = distributorobj.getString("full_name");
                                String helPerImg = distributorobj.getString("pic");
                                helperPhone = distributorobj.getString("phone");
                                String ser_per_name = serviceobj.getString("full_name");
                                String serperImg = serviceobj.getString("pic");
                                serperPhone = serviceobj.getString("phone");
                                String deliveryDateres = object.getString("delivery_date");
                                String DeliveyyTimee = object.getString("delivery_time");
                                String installDate = object.getString("insta_date");
                                String installTime = object.getString("insta_time");
                                Phoneno = object.getString("phone");



                                Glide.with(OrderDetailsActivity.this).load("http://headwaygms.com/" + img).placeholder(R.drawable.headwaygmslogo).into(productImage);
                                productTitle.setText(title);
                                ProductPrice.setText("\u20B9 " + price + "/-");
                                ProductQty.setText("Qty :" + qty);
                                // OrderStatus.setText(order_status);



                                if (order_status.equals("3")){

                                    if (productstatus.equals("1"))
                                    {
                                        completeOrderBtn.setVisibility(View.GONE);
                                        instaled.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        completeOrderBtn.setVisibility(View.GONE);
                                        instaled.setVisibility(View.GONE);
                                    }

                                }else {
                                    instaled.setVisibility(View.GONE);
                                    completeOrderBtn.setVisibility(View.VISIBLE);
                                }

                                if (order_status.equals("1")) {
                                    orderIndi.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    orderIndi.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    packProgressbar.setBackgroundColor(Color.parseColor("#4BB543"));
                                    confirmindicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    //packProgressbar.setBackgroundColor(Color.parseColor("#4BB543"));
                                }

                                 else if (order_status.equals("3")) {


                                    orderIndi.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    packProgressbar.setBackgroundColor(Color.parseColor("#4BB543"));
                                    confirmindicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    confirmedprogress.setBackgroundColor(Color.parseColor("#4BB543"));
                                    deliverIndicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));

                                } else if (order_status.equals("4")) {


                                    orderIndi.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    packProgressbar.setBackgroundColor(Color.parseColor("#4BB543"));
                                    confirmindicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    confirmedprogress.setBackgroundColor(Color.parseColor("#4BB543"));
                                    deliverIndicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    deliverIndicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    installedindicator.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4BB543")));
                                    progressdelivery.setBackgroundColor(Color.parseColor("#4BB543"));
                                } else {
                                    orderIndi.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));

                                    packProgressbar.setVisibility(View.GONE);
                                    confirmindicator.setVisibility(View.GONE);
                                    confirmedprogress.setVisibility(View.GONE);
                                    deliverIndicator.setVisibility(View.GONE);
                                    installedindicator.setVisibility(View.GONE);
                                    progressdelivery.setVisibility(View.GONE);

                                    orderTitletv.setText("Cancelled");
                                    confirmtitletv.setVisibility(View.GONE);
                                    delivertitletv.setVisibility(View.GONE);
                                    installedtitletv.setVisibility(View.GONE);
                                }


                                health_person_name.setText(healthPerName);
                                health_person_phone.setText(helperPhone);
                                Picasso.get()
                                        .load("https://www.headwaygms.com/"+helPerImg)
                                        .placeholder(R.drawable.profileimageicon)
                                        .error(R.drawable.profileimageicon)
                                        .into(health_person_image);

                                servive_person_name.setText(ser_per_name);
                                servive_person_phone.setText(serperPhone);

                                Picasso.get()
                                        .load("https://www.headwaygms.com/"+serperImg)
                                        .placeholder(R.drawable.profileimageicon)
                                        .error(R.drawable.profileimageicon)
                                        .into(servive_person_image);
                                deliveryDate.setText(deliveryDateres);
                                deliveryTime.setText(DeliveyyTimee);
                                InstallationDate.setText(installDate);
                                InstallationTime.setText(installTime);


                                if (installDate.equals("null") || installTime.equals("null")) {
                                    instalationscheduleView.setVisibility(View.GONE);
                                    progressDialog.dismiss();
                                }


                                if (ser_per_name.equals("") || serperPhone.equals("")) {
                                    servicemanlayout.setVisibility(View.GONE);
                                    progressDialog.dismiss();
                                }

                                if (healthPerName.equals("") || helperPhone.equals("")) {
                                    helperLayoutView.setVisibility(View.GONE);
                                }


                                progressBar.setVisibility(View.GONE);
                            }

                            else {

                                Toast.makeText(OrderDetailsActivity.this, ""+jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                            progressBar.setVisibility(View.GONE);

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(OrderDetailsActivity.this, "Server Not Responding", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }) {

        };

        queue.getCache().clear();
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);



    }



//    private void setServiceDetailsData(String orderID) {
//
//        progressDialog=new ProgressDialog(this);
//        progressDialog.setMessage("Placing..");
//        progressDialog.setTitle("Please Wait...");
//        progressDialog.setIcon(R.drawable.headwaygmslogo);
//
//        String url="http://www.headwaygms.com/api/services-order-details?service_id="+orderID;
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        queue.getCache().clear();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
//
//                new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//
//                            JSONObject jsonObject=new JSONObject(response);
//
//                            Toast.makeText(OrderDetailsActivity.this, ""+response, Toast.LENGTH_SHORT).show();
//
//                            JSONObject object= jsonObject.getJSONObject("services");
//
//                            String img=object.getString("image");
//                            String  title=object.getString("title");
//                            String price=object.getString("price");
//                            String service_status=object.getString("service_status");
//                            String healthPerName=object.getString("health_ad_name");
//                            String helPerImg=object.getString("h_ad_image");
//                            String helperPhone=object.getString("h_ad_phone");
//                            String ser_per_name=object.getString("ser_per_name");
//                            String serperImg=object.getString("s_p_image");
//                            String serperPhone=object.getString("s_p_phone");
//                            String serviceDateres=object.getString("service_date");
//                            String serviceTimee =object.getString("service_time");
//
//
//                            Glide.with(OrderDetailsActivity.this).load("http://headwaygms.com/"+img).placeholder(R.drawable.headwaygmslogo).into(productImage);
//                            productTitle.setText(title);
//                            ProductPrice.setText("Rs "+price+"/-");
//
//                            OrderStatus.setText(service_status);
//                            health_person_name.setText(healthPerName);
//                            health_person_phone.setText(helperPhone);
//                            Glide.with(OrderDetailsActivity.this).load(helPerImg).placeholder(R.drawable.headwaygmslogo).into(health_person_image);
//
//                            servive_person_name.setText(ser_per_name);
//                            servive_person_phone.setText(serperPhone);
//                            Glide.with(OrderDetailsActivity.this).load(serperImg).placeholder(R.drawable.headwaygmslogo).into(servive_person_image);
//
//                            ProductQty.setVisibility(View.GONE);
//
//                            InstallationDate.setText(serviceDateres);
//                            InstallationTime.setText(serviceTimee);
//
//                            deliveryschlayout.setVisibility(View.GONE);
//
//                            if (ser_per_name.equals("null") && serperPhone.equals("null"))
//                            {
//
//                                view.setVisibility(View.GONE);
//                            }
//
//                            if (healthPerName.equals("null") && helperPhone.equals("null"))
//                            {
//                                helperLayoutView.setVisibility(View.GONE);
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            progressDialog.dismiss();
//
//                        }
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(OrderDetailsActivity.this, "Response error", Toast.LENGTH_SHORT).show();
//
//
//                    }
//                }) {
//
//        };
//
//        queue.getCache().clear();
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
//        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//    }


    private  void cancelService(final String orderID) {

        ReasonOfCancelService = cancelReasonTextView.getText().toString();

        if (ReasonOfCancelService.equals("")) {
            Toast.makeText(this, "Please Give some reason", Toast.LENGTH_SHORT).show();
        } else {

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Rescheduling");
            progressDialog.setTitle("Please wait...");
            progressDialog.setIcon(R.drawable.headwaygmslogo);
            progressDialog.show();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.URL_CANCEL_SERVICE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONObject obj = new JSONObject(response);

                                //Toast.makeText(OrderDetailsActivity.this, "response="+response, Toast.LENGTH_SHORT).show();

                                String message = obj.getString("msg");

                                if (message.equals("Success")) {

                                    JSONObject jsonObject = obj.getJSONObject("services");

                                    String ServiceStatus = jsonObject.getString("service_status");
                                    OrderStatus.setText(ServiceStatus);
                                    Toast.makeText(OrderDetailsActivity.this, "Status" + ServiceStatus, Toast.LENGTH_SHORT).show();

                                    progressDialog.dismiss();

                                } else {
                                    Toast.makeText(OrderDetailsActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(OrderDetailsActivity.this, "exception", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(OrderDetailsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("service_order_id", orderID);
                    params.put("cancelation_reason", ReasonOfCancelService);

                    return params;
                }
            };

            VolleySingleton.getInstance(OrderDetailsActivity.this).addToRequestQueue(stringRequest);
        }

    }

    private void rescheduleService(final String orderID) {


        myrescheduledate = rescheduleDateText.getText().toString();
        myrescheduletime = rescheduleTimeText.getText().toString();


        if (myrescheduledate.equals("DD/MM/YY")) {
            Toast.makeText(this, "Please Enter date", Toast.LENGTH_SHORT).show();

        }

      else  if (myrescheduletime.equals("HH:MM")) {
            Toast.makeText(this, "Please Enter time", Toast.LENGTH_SHORT).show();

        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Rescheduling");
            progressDialog.setTitle("Please wait...");
            progressDialog.setIcon(R.drawable.headwaygmslogo);
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.URL_SERVICERESCHEDULE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONObject obj = new JSONObject(response);

                                //Toast.makeText(OrderDetailsActivity.this, "response="+response, Toast.LENGTH_SHORT).show();

                                String message = obj.getString("msg");

                                Toast.makeText(OrderDetailsActivity.this, "" + message, Toast.LENGTH_SHORT).show();


                                if (message.equals("Success")) {

                                    JSONObject jsonObject = obj.getJSONObject("services");

                                    String serviceDatee = jsonObject.getString("service_date");
                                    String serviceTimee = jsonObject.getString("service_time");

                                    InstallationDate.setText(serviceDatee);
                                    InstallationTime.setText(serviceTimee);

                                    progressDialog.dismiss();

                                } else {
                                    Toast.makeText(OrderDetailsActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(OrderDetailsActivity.this, "exception", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(OrderDetailsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("service_order_id", orderID);
                    params.put("reschedule_time", myrescheduletime);
                    params.put("reschedule_date", myrescheduledate);

                    return params;
                }
            };

            VolleySingleton.getInstance(OrderDetailsActivity.this).addToRequestQueue(stringRequest);


        }
    }
    private void verifyotp(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setIcon(R.drawable.headwaygmslogo);
        progressDialog.setTitle("Deleverd");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String url ="https://www.headwaygms.com/api/cda-otp?order_id="+orderID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);

                           // Toast.makeText(OrderDetailsActivity.this, "response="+response, Toast.LENGTH_SHORT).show();

                            String message = obj.getString("msg");
                            String status = obj.getString("status");
                            String Otp = obj.getString("otp");

                            if (status.equals("200")) {
                                 Toast.makeText(OrderDetailsActivity.this, "otp="+Otp,Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OrderDetailsActivity.this,OtpVerificationActivity.class);
                                intent.putExtra("order_id",orderID);
                                intent.putExtra("type","2");
                                intent.putExtra("otp",Otp);
                                startActivity(intent);
                                progressDialog.dismiss();

                            } else {
                                Toast.makeText(OrderDetailsActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OrderDetailsActivity.this, "exception"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderDetailsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {

        };

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(OrderDetailsActivity.this).addToRequestQueue(stringRequest);




    }


    private void  completeOrder()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String url ="https://www.headwaygms.com/api/mark-as-delivered?order_id="+orderID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("msg");
                            Toast.makeText(OrderDetailsActivity.this, "" + message, Toast.LENGTH_SHORT).show();


                            if (message.equals("Success")) {
                                Intent intent=new Intent(OrderDetailsActivity.this,OtpVerificationActivity.class);
                                intent.putExtra("order-id",orderID);
                                intent.putExtra("type","2");
                                intent.putExtra("phone",Phoneno);
                                startActivity(intent);
                                progressDialog.dismiss();

                            } else {
                                Toast.makeText(OrderDetailsActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OrderDetailsActivity.this, "exception", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderDetailsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", orderID);


                return params;
            }
        };

        VolleySingleton.getInstance(OrderDetailsActivity.this).addToRequestQueue(stringRequest);


    }

    }





@SuppressLint("ValidFragment")
class TimePickerFragment2 extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String am_pm;


        if (hourOfDay<12)
        {
            am_pm="AM";
        }

        else
        {
            am_pm="PM";
        }

        OrderDetailsActivity.rescheduleTimeText.setText(hourOfDay+":"+minute+" "+am_pm);

    }


}


@SuppressLint("ValidFragment")
class DatePickerFragment2 extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        OrderDetailsActivity.rescheduleDateText.setText(day+"/"+month+"/"+year);
    }
}






