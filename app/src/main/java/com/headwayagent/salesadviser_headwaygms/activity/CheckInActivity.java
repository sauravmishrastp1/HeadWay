package com.headwayagent.salesadviser_headwaygms.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.SalesDashbBoard;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.models.UserCheckin;
import com.headwayagent.salesadviser_headwaygms.models.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckInActivity extends AppCompatActivity {

    Button checkInBTn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        checkInBTn = findViewById(R.id.checkinbutton);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(CheckInActivity.this, LoginActivity.class));
            finish();
        }

        UserDetails user = SharedPrefManager.getInstance(this).getUser();
        final String department = user.getDepartment();
        final String ain_number = user.getAin_number();


        checkInBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkIn(department, ain_number);


            }
        });

    }
    private void checkIn(final String department, final String ain_number) {

        String url = "https://www.headwaygms.com/api/check-in?ain=" + ain_number;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);

                            String message = obj.getString("msg");

                            if (message.equals("Success")) {

                                if (department.equals("service")) {


                                    UserCheckin userCheckin = new UserCheckin(ain_number);
                                    SharedPrefManager.getInstance(getApplicationContext()).userCheckin(userCheckin);
                                    Intent intent = new Intent(CheckInActivity.this, MyOrdersActivity.class);
                                    intent.putExtra("type", "2");
                                    startActivity(intent);
                                    progressDialog.dismiss();
                                    finish();
                                    return;


                                } else if (department.equals("sales")) {

                                    UserCheckin userCheckin = new UserCheckin(ain_number);
                                    SharedPrefManager.getInstance(getApplicationContext()).userCheckin(userCheckin);
                                    Intent intent = new Intent(CheckInActivity.this, SalesDashbBoard.class);
                                    startActivity(intent);
                                    finish();
                                    progressDialog.dismiss();


                                }


                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "Server Not Responding  check your network connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {

        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }




}