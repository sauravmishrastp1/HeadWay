package com.headwayagent.salesadviser_headwaygms.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.BaseUrl;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.SalesDashbBoard;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.adapter.TotalDirectCommissionAdapter;
import com.headwayagent.salesadviser_headwaygms.models.TotalDirectCommisionModel;
import com.headwayagent.salesadviser_headwaygms.models.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VerifyOtp extends AppCompatActivity {

    private String otp;
    private EditText Happycode;
    private Button Verify;
    private String happycode;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        Happycode = findViewById(R.id.happycode);
        Verify = findViewById(R.id.submitotp);
        toolbar=findViewById(R.id.toolbarservice);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Happy Code Verfication");

        Bundle bundle;
        bundle=getIntent().getExtras();

        otp=bundle.getString("otp");



        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                happycode = Happycode.getText().toString();
                // Toast.makeText(VerifyOtp.this, "otp"+otp, Toast.LENGTH_SHORT).show();


                if (happycode.equals("")) {
                    Toast.makeText(VerifyOtp.this, "Please Enter Happy Code", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(VerifyOtp.this);
                    progressDialog.setIcon(R.drawable.headwaygmslogo);
                    progressDialog.setTitle("Login.....");
                    progressDialog.setMessage("Please wait......");
                    progressDialog.show();

                if (otp.equals(happycode)) {
                    // Toast.makeText(VerifyOtp.this, "ok", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                   // UserDetails user = new UserDetails(LoginActivity.ain, "", LoginActivity.phone, LoginActivity.department, LoginActivity.title,LoginActivity. full_name, LoginActivity.pic, LoginActivity.module_name, LoginActivity.city, LoginActivity.state, LoginActivity.email, LoginActivity.address, LoginActivity.pin, LoginActivity.sponsor_name, LoginActivity.sponsor_id, LoginActivity.sdw, LoginActivity.designation,LoginActivity. aadhaar_number,
                           // LoginActivity.nominee_name,LoginActivity. nominee_dob, LoginActivity.relationship, LoginActivity.pan, LoginActivity.account_holder, LoginActivity.bank, LoginActivity.account_number,LoginActivity. ifsc, "", LoginActivity.status, LoginActivity.userid, "", "order");

                   // SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                    //Intent intent = new Intent(VerifyOtp.this, SalesDashbBoard.class);
                   // intent.putExtra("ain",ain);

                   // startActivity(intent);
                } else {

                    Toast.makeText(VerifyOtp.this, "otp doesn't match", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            }
        });

    }

    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.profile:
                startActivity(new Intent(VerifyOtp.this,LoginActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
