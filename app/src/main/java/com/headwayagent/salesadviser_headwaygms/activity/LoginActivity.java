package com.headwayagent.salesadviser_headwaygms.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.BaseUrl;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.Registeration_Activity;
import com.headwayagent.salesadviser_headwaygms.SalesDashbBoard;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.models.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button login_btn;

    private EditText aintextview;
    private EditText passwordtextview;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    public static String ain,department,sponsor_name,sponsor_id,title,module_name,status,full_name,sdw,aadhaar_number,designation,address,city,state,
    pin,email,userid,pan,phone,nominee_name,nominee_dob,relationship,account_holder,account_number,bank,branch,account_type,ifsc,pic,aadhaar_doc;
    private TextView textViewRestePss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_btn=findViewById(R.id.loginbtn);
        aintextview=findViewById(R.id.AIN_Number);
        passwordtextview=findViewById(R.id.password);
        textViewRestePss = findViewById(R.id.restelink);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               login();
            }
        });
      aintextview.setText("HGMS-CDA-");
      textViewRestePss.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this,ResetPassword.class);
            startActivity(intent);
          }
      });

    }




    private void login() {

        final String ain_number=aintextview.getText().toString();
        final String password=passwordtextview.getText().toString();


        if (TextUtils.isEmpty(ain_number)) {
            aintextview.setError("Please enter your AIN Number");
            aintextview.requestFocus();
            return;
        } else if (TextUtils.isEmpty(password)) {
            passwordtextview.setError("Please enter password");

        }  else{


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setIcon(R.drawable.headwaygmslogo);
            progressDialog.setTitle("Login.....");
            progressDialog.setMessage("Please wait......");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.URL_EMP_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                //converting response to json object

                                JSONObject obj = new JSONObject(response);

                                String message = obj.getString("msg");
                               // String otp = obj.getString("otp");


                                if (message.equals("Success")) {


                                      //  String offer_letter = obj.getString("offer");
                                        JSONObject jsonObject = obj.getJSONObject("user");

                                      //  String downline = obj.getString("downline");
                                       // String order = obj.getString("order");
                                         department = jsonObject.getString("department");
                                        sponsor_name = jsonObject.getString("sponsor_name");
                                         sponsor_id = jsonObject.getString("sponsor_id");
                                         module_name = jsonObject.getString("module_name");
                                         title = jsonObject.getString("title");
                                         ain = jsonObject.getString("ain");
                                         status = jsonObject.getString("status");
                                         full_name = jsonObject.getString("full_name");
                                         sdw = jsonObject.getString("sdw");
                                         aadhaar_number = jsonObject.getString("aadhaar_number");
                                         designation = jsonObject.getString("designation");
                                         address = jsonObject.getString("address");
                                         city = jsonObject.getString("city");
                                         state = jsonObject.getString("state");
                                         pin = jsonObject.getString("pin");
                                         email = jsonObject.getString("email");
                                         userid = jsonObject.getString("id");
                                         pan = jsonObject.getString("pan");
                                         phone = jsonObject.getString("phone");
                                         nominee_name = jsonObject.getString("nominee_name");
                                         relationship = jsonObject.getString("relationship");
                                         nominee_dob = jsonObject.getString("nominee_dob");
                                         account_holder = jsonObject.getString("account_holder");
                                         bank = jsonObject.getString("bank");
                                         branch = jsonObject.getString("branch");
                                         ifsc = jsonObject.getString("ifsc");
                                         account_number = jsonObject.getString("account_number");
                                         account_type = jsonObject.getString("account_type");
                                         pic = jsonObject.getString("pic");
                                         aadhaar_doc = jsonObject.getString("aadhaar_doc");
                                      //  String name =jsonObject.getString("name");



                                       UserDetails user = new UserDetails(ain, password, phone, department, title, full_name, pic, module_name, city, state, email, address, pin, sponsor_name, sponsor_id, sdw, designation, aadhaar_number, nominee_name, nominee_dob, relationship, pan, account_holder, bank, account_number, ifsc, "", status, userid, "", "order");

                                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                        Intent intent = new Intent(getApplicationContext(), SalesDashbBoard.class);
                                        //intent.putExtra("otp",otp);
                                        //intent.putExtra("ain",ain);
                                        startActivity(intent);
                                        LoginActivity.this.finish();
                                        progressDialog.dismiss();
                                        return;

                                } else {
                                    Toast.makeText(getApplicationContext(), ""+obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(LoginActivity.this, "Something went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Server Not Respondin Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("ain", ain_number);
                    params.put("password", password);
                    return params;
                }
            };
            VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        requestStoragePermission();
    }

    protected void requestStoragePermission(){
        if(ContextCompat.checkSelfPermission(
                this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                LoginActivity.this,
                                new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        }else {
            // Do something, when permissions are already granted
            Toast.makeText(this,"Permissions already granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CODE:{
                // When request is cancelled, the results array are empty
                if(
                        (grantResults.length >0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED
                                )
                ){
                    // Permissions are granted
                    Toast.makeText(this,"Permissions granted.",Toast.LENGTH_SHORT).show();
                }else {
                    // Permissions are denied
                    Toast.makeText(this,"Permissions denied.",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}



