package com.headwayagent.salesadviser_headwaygms.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Config;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.BaseUrl;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.Registeration_Activity;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.adapter.Department_Adapter;
import com.headwayagent.salesadviser_headwaygms.adapter.Distrubeter_Adapter;
import com.headwayagent.salesadviser_headwaygms.adapter.Iactive_adapter;
import com.headwayagent.salesadviser_headwaygms.adapter.Spinner_ItemAdapter;
import com.headwayagent.salesadviser_headwaygms.adapter.State_Adapter;
import com.headwayagent.salesadviser_headwaygms.dialogues.DSRDialogFragment;
import com.headwayagent.salesadviser_headwaygms.models.Department_model;
import com.headwayagent.salesadviser_headwaygms.models.Distributer_model;
import com.headwayagent.salesadviser_headwaygms.models.Spinner_ItemModel;
import com.headwayagent.salesadviser_headwaygms.models.State_Model;
import com.headwayagent.salesadviser_headwaygms.models.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fragment.Inactive_Fragement;

import static android.widget.Toast.LENGTH_SHORT;

public class CustomerDetailsFormActivity extends AppCompatActivity {

    private Button nextbtn;
    private CheckBox checkBox;
    Spinner statesp,distrubutersp;
    private Toolbar toolbar;
    private String pid;
    private String quantity;
    private String type;
    private String p_price,p_img,p_title,p_discription;
    private ProgressBar progressBar;
    private String user_id;
    private JSONArray result;

     private ArrayList<State_Model>state_modelList = new ArrayList<>();


    private EditText nameEdittext,emailEdittext,phoneEditText,address1EditText,address2Edittext,cityEditText,stateEditText,pinEditText,alternateEditext;
    private  String name,email,phone,address1,address2="NotGiven",city,pin,alternatePhone="N/A";

   private  String stateName;
   public static String statepin;
   private String fullName ,Pin,Ain;
   TextView  staenametext,getcdaa;
   private String Inactiveain="";
   private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details_form);
        nextbtn=findViewById(R.id.customerdetailsbtn);
        toolbar=findViewById(R.id.toolbar);
        progressBar=findViewById(R.id.progressbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Customer Details");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameEdittext=findViewById(R.id.name);
        emailEdittext=findViewById(R.id.email);
        phoneEditText=findViewById(R.id.phone);
        address1EditText=findViewById(R.id.address1);
        address2Edittext=findViewById(R.id.address2);
        cityEditText=findViewById(R.id.city);
        statesp=findViewById(R.id.state);
        pinEditText=findViewById(R.id.pin);
        alternateEditext=findViewById(R.id.alternate_phone);
        staenametext = findViewById(R.id.statename);
        checkBox = findViewById(R.id.getcda);
        view = findViewById(R.id.viewchosecda);
        UserDetails user = SharedPrefManager.getInstance(this).getUser();

        user_id=user.getAin_number();

        Bundle extras = getIntent().getExtras();

        if (extras!=null) {
            type = extras.getString("type");
        }

        if (MyProfileActivity.type.equals("InActive"))
        {
            view.setVisibility(View.GONE);
        }

        getstate();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    nameEdittext.setText(Registeration_Activity.fullname);
                    emailEdittext.setText(Registeration_Activity.email);
                    phoneEditText.setText(Registeration_Activity.mobileno);
                    address1EditText.setText(Registeration_Activity.address);
                    cityEditText.setText(Registeration_Activity.city);
                }
                else {
                    nameEdittext.getText().clear();
                    emailEdittext.getText().clear();
                    phoneEditText.getText().clear();
                    address1EditText.getText().clear();
                }
            }
        });



         state_modelList.add(new State_Model("Select State",""));
        State_Adapter state_adapter = new State_Adapter(this, (ArrayList<State_Model>) state_modelList);

        if (statesp != null) {
            statesp.setAdapter(state_adapter);

            statesp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    State_Model model = (State_Model) parent.getSelectedItem();

                     statepin=model.getSatepin();
                     stateName = model.getStatename();
                    // Toast.makeText(CustomerDetailsFormActivity.this, ""+stateName+statepin, LENGTH_SHORT).show();




                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }



         if (type.equals("sales")) {

             quantity = extras.getString("qty");
             pid=extras.getString("pid");
             type=extras.getString("type");
             p_price=extras.getString("price");
             p_img=extras.getString("image");
             p_title=extras.getString("p_title");



         }

         else {
             pid=extras.getString("pid");
             p_img=extras.getString("image");
             p_price=extras.getString("service_price");
             p_title=extras.getString("title");
             p_discription=extras.getString("service_discription");
         }


       // Toast.makeText(this, "qty="+quantity, Toast.LENGTH_SHORT).show();


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyFields();
            }
        });
    }
    private void getstate(){
        //Creating a string request
        String url="https://www.headwaygms.com/api/get-state";

         StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(CustomerDetailsFormActivity.this, "value="+response, LENGTH_SHORT).show();

                        try {
                          //  Toast.makeText(CustomerDetailsFormActivity.this, "hello", LENGTH_SHORT).show();

                            JSONObject object=new JSONObject(response);
                            String msg=object.getString("msg");
                           // Toast.makeText(CustomerDetailsFormActivity.this, "msg"+msg, LENGTH_SHORT).show();

                            if (msg.equals("Success")) {



                                JSONArray ja = object.getJSONArray("state");
                               // Toast.makeText(CustomerDetailsFormActivity.this, "value"+ja, LENGTH_SHORT).show();


                                for (int i = 0; i < ja.length(); i++) {
                                   // Toast.makeText(CustomerDetailsFormActivity.this, "hii=" + ja, LENGTH_SHORT).show();

                                    JSONObject jsonObject = ja.getJSONObject(i);

                                     String stateName = jsonObject.getString("StateName");
                                      String statepin = jsonObject.getString("state_code");



                                      state_modelList.add(new State_Model(stateName,statepin));

                                }


                            }

                            else {
                                Toast.makeText(CustomerDetailsFormActivity.this, "somthing wrong", LENGTH_SHORT).show();
                            }

                        } catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(CustomerDetailsFormActivity.this, "something went wrong"+e, LENGTH_SHORT).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Server Not responding"+error.toString(),Toast.LENGTH_LONG).show();

                    }
                }){

        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }



    private void verifyFields()
    {
         name=nameEdittext.getText().toString();
         email=emailEdittext.getText().toString();
         phone=phoneEditText.getText().toString();
         address1=address1EditText.getText().toString();
         address2=address2Edittext.getText().toString();
         city=cityEditText.getText().toString();
        // state=state.getText().toString();
         pin=pinEditText.getText().toString();
         alternatePhone=alternateEditext.getText().toString();


    if (alternatePhone.equals(""))
    {
        alternatePhone="00000";
    }

    if (address2.equals(""))
    {
        address2="NotGiven";
    }

        if (TextUtils.isEmpty(name))
        {
            nameEdittext.setError("Required");
            nameEdittext.requestFocus();
        }



        else if (TextUtils.isEmpty(phone))
        {
            phoneEditText.setError("Required");
            phoneEditText.requestFocus();
        }

        else if (phone.length()>10 || phone.length()<10)
        {
            Toast.makeText(this, "Phone Must Be only 10 digit", Toast.LENGTH_SHORT).show();
        }


        else if (TextUtils.isEmpty(address1))
        {
            address1EditText.setError("Required");
            address1EditText.requestFocus();
        }



        else if (TextUtils.isEmpty(city))
        {
            cityEditText.setError("Required");
            cityEditText.requestFocus();
        }else if(stateName.equals("Select State") || stateName.equals("")){
            Toast.makeText(this, "please select State", LENGTH_SHORT).show();

        }


        else if (TextUtils.isEmpty(pin))
        {
            pinEditText.setError("Required");
            pinEditText.requestFocus();
        }



        else {


            if (type.equals("dsr"))
            {
                 // addCustomer();
//                Intent intent=new Intent(this,SalesDashBoardActivity.class);
//                startActivity(intent);
//                Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();

                Bundle  bundle=new Bundle();

                DSRDialogFragment f = new DSRDialogFragment();
                FragmentManager fm = getSupportFragmentManager();
                bundle.putString("name", name);
                bundle.putString("email", email);
                bundle.putString("phone", phone);
                bundle.putString("address1", address1);
                bundle.putString("address2", address2);
                bundle.putString("city", city);
                bundle.putString("state", statepin);
                bundle.putString("pin", pin);
                bundle.putString("alternate_phone", alternatePhone);
                bundle.putString("pid", pid);
                bundle.putString("qty", quantity);
                bundle.putString("type", type);
                bundle.putString("p_img", p_img);
                bundle.putString("p_price", p_price);
                bundle.putString("p_title", p_title);
                bundle.putString("p_discription", p_discription);
                bundle.putString("ain",Ain);

                f.setArguments(bundle);
                f.show(fm, "Select  Category");

            }

            else {
               // Toast.makeText(this, "ptitle="+p_title, LENGTH_SHORT).show();
                Intent intent = new Intent(CustomerDetailsFormActivity.this, AddressProductDetailsActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("address1", address1);
                intent.putExtra("address2", address2);
                intent.putExtra("city", city);
                intent.putExtra("state", statepin);
                intent.putExtra("pin", pin);
                intent.putExtra("alternate_phone", alternatePhone);
                intent.putExtra("pid", pid);
                intent.putExtra("qty", quantity);
                intent.putExtra("type", type);
                intent.putExtra("p_img", p_img);
                intent.putExtra("p_price", p_price);
                intent.putExtra("ain",Ain);
                intent.putExtra("p_title",p_title);
                startActivity(intent);
            }

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void addCustomer()
    {

         progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.URL_DAILY_REPORT,

                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("msg");

                            if (message.equals("Success"))
                            {
                                Intent intent=new Intent(CustomerDetailsFormActivity.this,SalesDashBoardActivity.class);
                                startActivity(intent);
                                Toast.makeText(CustomerDetailsFormActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                            else {
                                Toast.makeText(CustomerDetailsFormActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(CustomerDetailsFormActivity.this, "hello="+e.toString(), Toast.LENGTH_SHORT).show();
                           progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("address1", address1);
                params.put("address2", address2);
                params.put("city", city);
                params.put("state", statepin);
                params.put("pin", pin);
                params.put("alternate_phone",alternatePhone);
                params.put("user_id",user_id);

                return params;
            }
        };

        queue.getCache().clear();

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}
