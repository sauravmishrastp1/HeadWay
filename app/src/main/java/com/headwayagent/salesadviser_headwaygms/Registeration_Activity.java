package com.headwayagent.salesadviser_headwaygms;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.BaseUrl;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.activity.CheckInActivity;
import com.headwayagent.salesadviser_headwaygms.activity.LoginActivity;
import com.headwayagent.salesadviser_headwaygms.activity.SalesDashBoardActivity;
import com.headwayagent.salesadviser_headwaygms.adapter.Department_Adapter;
import com.headwayagent.salesadviser_headwaygms.adapter.Registration_phase_two;
import com.headwayagent.salesadviser_headwaygms.adapter.Spinner_ItemAdapter;
import com.headwayagent.salesadviser_headwaygms.models.Department_model;
import com.headwayagent.salesadviser_headwaygms.models.Spinner_ItemModel;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.FileUtils;
import util.VolleyMultipartRequest;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Registeration_Activity extends AppCompatActivity {
    EditText nameEt, adharEt, sdwoEt, addressEt, cityEt, pincodeEt, emailEt, dobEt, pancardEt, mobilenoEt, nomineenameEt,
            relationshpEt, nomineedobEt, bankholdernameEt, banknameEt, banacountnoEt, branchEt, ifscEt, stateEt;
    Spinner dapartmentSp, genderSp;
    RadioButton cdachannalradiobtn;
    Calendar calendar;
    int year, date, month;
    Button registerbutton, chooseephotograph, chooseadhhar, choosepancard, choosecancle, choosesignature, other;
    private List<Spinner_ItemModel> genderlist = new ArrayList<>();
    private List<Department_model> department_models = new ArrayList<>();
    String gender;
    String department;
    private String type, extension;
    private int progressStatus = 0;
    private static final int GALLERY_IMAGE = 1;
    private static final int GALLERY_SIGN = 2;
    private static final int GALLERY_ADHAR = 3;
    private static final int GALLERY_OTHER = 4;
    private static final int GALLERY_PAN = 5;
    private static final int GALLERY_CHEQUE = 6;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    ProgressDialog progressDialog;
    String mUri = "";
    String CDAChannel;
    String account_type = "";
    String filePathpic = "";
    String filePatadhar = "";
    String filePathpan = "";
    String filePathcheque = "";
    String filePathsign = "";
    String filePathother = "";
    byte Pic[];
    byte Pancard[];
    byte Adhardata[];
    byte Cheque[];
    byte Other[];
    byte Sign[];
    TextView textView1;
    private String ainNumber;
    private String spounsername;
    BitmapFactory.Options options;
    RadioGroup radioGroup;
    public static String ain="null";
    public static String  fullname, adharno, sodo, address, city, pincode, email, dateofbi, pancardn, mobileno,sate="null";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_);
        dapartmentSp = findViewById(R.id.department);
        genderSp = findViewById(R.id.gender);
        stateEt = findViewById(R.id.Statee);

        cdachannalradiobtn = findViewById(R.id.radiobutoon);
        radioGroup = findViewById(R.id.groupradio);

        nameEt = findViewById(R.id.fullname);
        adharEt = findViewById(R.id.adharno);
        sdwoEt = findViewById(R.id.sofdof);
        addressEt = findViewById(R.id.address);
        cityEt = findViewById(R.id.city);
        pincodeEt = findViewById(R.id.pincode);
        emailEt = findViewById(R.id.email);
        dobEt = findViewById(R.id.dob);
        pancardEt = findViewById(R.id.pancardno);
        mobilenoEt = findViewById(R.id.mobileno);
        //  nomineenameEt = findViewById(R.id.nomeniname);
        relationshpEt = findViewById(R.id.relationship);
        // nomineedobEt = findViewById(R.id.nomeneedob);
        //  bankholdernameEt = findViewById(R.id.AccountHolderName);
        // banknameEt = findViewById(R.id.BankName);
        // banacountnoEt = findViewById(R.id.BankAccountNumber);
        // branchEt = findViewById(R.id.Branch);
        //ifscEt = findViewById(R.id.IFSC);
        calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        registerbutton = findViewById(R.id.submit_datafile);
        // chooseephotograph = findViewById(R.id.photograph);
        // chooseadhhar = findViewById(R.id.adharcard);
        // choosecancle = findViewById(R.id.canclechequ);
        // choosesignature = findViewById(R.id.signature);
        // choosepancard = findViewById(R.id.pancard);
        // other = findViewById(R.id.other);
        // textView1 = findViewById(R.id.filename);

        ainNumber = SharedPrefManager.getInstance(getApplicationContext()).getUser().getAin_number();
        spounsername = SharedPrefManager.getInstance(getApplicationContext()).getUser().getFull_name();
        month = month + 1;
       // Toast.makeText(this, ""+ain, Toast.LENGTH_SHORT).show();

        dobEt.setText(date + "-" + month + "-" + year);

        dobEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Registeration_Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }


        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
              //  Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date =+ day + "/"+ month + "/"  + year;
                dobEt.setText(date);
            }
        };
//        nomineedobEt.setText(date + "-" + month + "-" + year);

//       // nomineedobEt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(Registeration_Activity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthofyear, int dayOfMonth) {
//                        monthofyear = monthofyear + 1;
//                        nomineedobEt.setText(dayOfMonth + "-" + monthofyear + "-" + year);
//                    }
//                }, year, month, date);
//                datePickerDialog.show();
//            }


        //  });

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();

            }
        });
//        radioGroup.setOnCheckedChangeListener(
//                new RadioGroup
//                        .OnCheckedChangeListener() {
//                    @Override
//
//                    // The flow will come here when
//                    // any of the radio buttons in the radioGroup
//                    // has been clicked
//
//                    // Check which radio button has been clicked
//                    public void onCheckedChanged(RadioGroup group,
//                                                 int checkedId)
//                    {
//                        RadioButton
//                                radioButton
//                                = (RadioButton)group
//                                .findViewById(checkedId);
//                        account_type=  radioButton.getText().toString();
//                        Toast.makeText(Registeration_Activity.this, ""+account_type, Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//
//


        genderlist = getGenderList();

        Spinner_ItemAdapter genderadapter = new Spinner_ItemAdapter(this, (ArrayList<Spinner_ItemModel>) genderlist);

        if (genderSp != null) {
            genderSp.setAdapter(genderadapter);

            genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Spinner_ItemModel model = (Spinner_ItemModel) parent.getSelectedItem();

                    gender = model.getSpinnerItemName();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        department_models = getdepartment_models();

        Department_Adapter adapter = new Department_Adapter(this, (ArrayList<Department_model>) department_models);

        if (dapartmentSp != null) {
            dapartmentSp.setAdapter(adapter);

            dapartmentSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Department_model model = (Department_model) parent.getSelectedItem();

                    department = model.getSpinnerItemName();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
//        chooseephotograph.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                choosepic();
//            }
//        });
//        choosepancard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                choosepanr();
//            }
//        });
//        choosesignature.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                choosesign();
//            }
//        });
//        choosecancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                chossecheuqe();
//            }
//        });
//        other.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                chooseother();
//            }
//        });

//        chooseadhhar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                chooseaadhar();
//            }
//        });

        cdachannalradiobtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    CDAChannel = "CDAChannel";
                } else {
                    CDAChannel = "No";
                }
            }
        });

//        savingacountradiobtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                savingacountradiobtn.setChecked(true);
//                if (b) {
//
//                    Saving_Account = "saving";
//                } else {
//
//                    Saving_Account = "current";
//                }
//            }
//        });
//        cruntaccountradiobtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                cruntaccountradiobtn.setChecked(true);
//                if (b) {
//
//                    Saving_Account = "current";
//                } else {
//
//                    Saving_Account = "saving";
//                }
//            }
//        });


    }


    private void register() {

         fullname = nameEt.getText().toString();
         adharno  = adharEt.getText().toString();
         sodo     = sdwoEt.getText().toString();
         address  = addressEt.getText().toString();
         city     = cityEt.getText().toString();
         pincode  = pincodeEt.getText().toString();
         email    = emailEt.getText().toString();
         dateofbi = dobEt.getText().toString();
         pancardn = pancardEt.getText().toString();
         mobileno = mobilenoEt.getText().toString();
        // final String nominee_name = nomineenameEt.getText().toString();
        // final String relationship = relationshpEt.getText().toString();
        // final String namineedob = nomineedobEt.getText().toString();
        //final String acountholdername = bankholdernameEt.getText().toString();
        //final String bankename = banknameEt.getText().toString();
        // final String branch = branchEt.getText().toString();
        // final String ifsc = ifscEt.getText().toString();
        // final String acountno = banacountnoEt.getText().toString();
         sate = stateEt.getText().toString();
        // int selectedId = radioGroup.getCheckedRadioButtonId();
       // Toast.makeText(this, ""+dateofbi, Toast.LENGTH_SHORT).show();

        // Toast.makeText(this, ""+account_type, Toast.LENGTH_SHORT).show();
         if(TextUtils.isEmpty(fullname)){
             nameEt.setError("Required");
             nameEt.requestFocus();
         }
         else if (mobileno.length() < 10 || mobileno.length() > 10) {
            mobilenoEt.setError("mobile no must be 10 digit");
            mobilenoEt.requestFocus();
        } else {
            Toast.makeText(this, "" + account_type, Toast.LENGTH_SHORT).show();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setIcon(R.drawable.headwaygmslogo);
            progressDialog.setTitle("Please wait.....");
            // progressDialog.setMessage("Please wait......");
            progressDialog.show();

            String url = "https://www.headwaygms.com/api/register-cda-step-one";


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                //converting response to json object

                                JSONObject obj = new JSONObject(response);

                                String message = obj.getString("msg");
                                // ain = obj.getString("ain");


                                if (message.equals("success")) {

                                    JSONObject jsonObject = obj.getJSONObject("user");
                                    ain = jsonObject.getString("ain");


                                    Toast.makeText(Registeration_Activity.this, "" + message, Toast.LENGTH_SHORT).show();
                                    // Toast.makeText(Registeration_Activity.this, "msg"+obj, Toast.LENGTH_SHORT).show();
                                    Intent inten = new Intent(Registeration_Activity.this, MainActivity.class);

                                    startActivity(inten);
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(getApplicationContext(), "" + obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(Registeration_Activity.this, "Something went wrong" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
//                @Override
//                protected Map<String, DataPart> getByteData() {
//                    Map<String, DataPart> params = new HashMap<>();
//                    params.put("pic",new DataPart("pan_doc"+extension ,Pic,type));
//                    params.put("pan_doc",new DataPart("pan_doc"+extension ,Pancard,type));
//                    params.put("aadhaar_doc",new DataPart("pan_doc"+extension, Adhardata,type));
//                    params.put("cheque_doc",new DataPart("pan_doc"+extension, Cheque,type));
//                    params.put("sign_doc",new DataPart("pan_doc"+extension, Sign,type));
//                    params.put("other_doc",new DataPart("pan_doc"+extension, Other,type));
//                    return params;


                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", fullname);
                    params.put("phone", mobileno);
                    params.put("aadhaar_number", adharno);
                    params.put("sdw", sodo);
                    params.put("address", address);
                    params.put("city", city);
                    params.put("pin", pincode);
                    params.put("dob", dateofbi);
                    params.put("pan", pancardn);
                    // params.put("nominee_name",nominee_name);
                    //params.put("relationship", relationship);
                    // params.put("nominee_dob", namineedob);
                    // params.put("account_holder", acountholdername);
                    // params.put("bank", bankename);
                    // params.put("account_number", acountno);
                    // params.put("branch", branch);
                    // params.put("ifsc", ifsc);
                    // params.put("account_type",account_type);
                    params.put("state", sate);
                    params.put("title", gender);
                    params.put("sponsor_name", spounsername);
                    params.put("sponsor_id", ainNumber);
                    params.put("designation", "Health Advisor");
                    params.put("email", email);
                    return params;
                }

            };


            VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);

        }
    }






            private ArrayList<Spinner_ItemModel> getGenderList () {


                genderlist.add(new Spinner_ItemModel(getResources().getString(R.string.genderlist)));
                genderlist.add(new Spinner_ItemModel(getResources().getString(R.string.male)));
                genderlist.add(new Spinner_ItemModel(getResources().getString(R.string.female)));
                genderlist.add(new Spinner_ItemModel(getResources().getString(R.string.ms)));

                return (ArrayList<Spinner_ItemModel>) genderlist;
            }

            private ArrayList<Department_model> getdepartment_models () {



                department_models.add(new Department_model(getResources().getString(R.string.employee)));


                return (ArrayList<Department_model>) department_models;
            }


            public void choosepic () {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_IMAGE);


            }
            public void chooseaadhar () {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_ADHAR);


            }
            public void choosepanr () {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_PAN);


            }
            public void choosesign () {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_SIGN);


            }
            public void chooseother () {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_OTHER);


            }
            public void chossecheuqe () {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_CHEQUE);


            }


            @Override
            protected void onActivityResult ( int requestCode, int resultCode, Intent data){
                super.onActivityResult(requestCode, resultCode, data);

                if (requestCode == GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri uri = data.getData();
                    //Toast.makeText(this, "you selected="+filePathpic+uri, Toast.LENGTH_SHORT).show();

                    try {
                        //getting image from gallery
                        //getting image from gallery
                        filePathpic = FileUtils.getPath(this,uri);
                        chooseephotograph.setText(filePathpic);
                        type=FileUtils.getMimeType(this,uri);
                        extension= (String) FileUtils.getExtension(String.valueOf(uri));

                            try {
                                ContentResolver cr = getBaseContext().getContentResolver();
                                InputStream inputStream = cr.openInputStream(uri);
                                Bitmap bitmap = BitmapFactory.decodeFile(filePathpic);
                                // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                Pic = baos.toByteArray();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();

                            }
                    } catch (Exception e) {
                        e.printStackTrace();
                       //  Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();

                    }
                } else if (requestCode == GALLERY_PAN && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri uri = data.getData();
                   // Toast.makeText(this, "you selected="+filePathpan+uri, Toast.LENGTH_SHORT).show();

                    try {
                        //getting image from gallery

                        filePathpan = FileUtils.getPath(this,uri);
                        type=FileUtils.getMimeType(this,uri);
                        choosepancard.setText(filePathpan);
                        extension= (String) FileUtils.getExtension(String.valueOf(uri));
                        try {
                            ContentResolver cr = getBaseContext().getContentResolver();
                            InputStream inputStream = cr.openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeFile(filePathpan);
                           // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            Pancard = baos.toByteArray();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                      //  Toast.makeText(this, "somthin" Toast.LENGTH_SHORT).show();
                    }
                } else if (requestCode == GALLERY_ADHAR && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri uri = data.getData();

                   // Toast.makeText(this, "you selected="+filePatadhar+uri, Toast.LENGTH_SHORT).show();
                    try {
                        //getting image from gallery
                        filePatadhar = FileUtils.getPath(this,uri);
                        chooseadhhar.setText(filePatadhar);
                        type=FileUtils.getMimeType(this,uri);
                        extension= (String) FileUtils.getExtension(String.valueOf(uri));
                        try {
                            ContentResolver cr = getBaseContext().getContentResolver();
                            InputStream inputStream = cr.openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeFile(filePatadhar);
                           // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            Adhardata = baos.toByteArray();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (requestCode == GALLERY_OTHER && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri uri = data.getData();
                   // Toast.makeText(this, "you selected="+filePathother+uri, Toast.LENGTH_SHORT).show();

                    try {
                        //getting image from gallery
                        //getting image from gallery
                        filePathother = FileUtils.getPath(this,uri);
                        other.setText(filePathother);
                        type=FileUtils.getMimeType(this,uri);
                        extension= (String) FileUtils.getExtension(String.valueOf(uri));
                        try {
                            ContentResolver cr = getBaseContext().getContentResolver();
                            InputStream inputStream = cr.openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeFile(filePathother);
                           // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            Other = baos.toByteArray();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (requestCode == GALLERY_SIGN && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri uri = data.getData();
                   // Toast.makeText(this, "you selected="+filePathsign+uri, Toast.LENGTH_SHORT).show();


                    try {

                        //getting image from gallery
                        filePathsign = FileUtils.getPath(this,uri);
                        choosesignature.setText(filePathsign);
                        type=FileUtils.getMimeType(this,uri);
                        extension= (String) FileUtils.getExtension(String.valueOf(uri));
                        try {
                            ContentResolver cr = getBaseContext().getContentResolver();
                            InputStream inputStream = cr.openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeFile(filePathpan);
                          //  Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            Sign = baos.toByteArray();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();

                        }
                     //   Toast.makeText(this, "You selected=" + Sign, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                       // Toast.makeText(this, "selected=" +e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (requestCode == GALLERY_CHEQUE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri uri = data.getData();
                  //  Toast.makeText(this, "you selected="+filePathcheque+uri, Toast.LENGTH_SHORT).show();

                    try {
                        //getting image from gallery
                        filePathcheque = FileUtils.getPath(this,uri);
                        choosecancle.setText(filePathcheque);
                        type=FileUtils.getMimeType(this,uri);
                      extension= (String) FileUtils.getExtension(String.valueOf(uri));
//                        Toast.makeText(this, "path="+filePathcheque+uri, Toast.LENGTH_SHORT).show();
//                        type=FileUtils.getMimeType(this,uri);
//                        extension= (String) FileUtils.getExtension(String.valueOf(uri));
//
//                        Bitmap src = BitmapFactory.decodeFile(filePathcheque);
//                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        src.compress(Bitmap.CompressFormat.PNG,100,baos);
//                        Cheque=baos.toByteArray();
                       // Toast.makeText(this, "" + Pic + Adhardata + Cheque + Other + Sign + Pancard, Toast.LENGTH_SHORT).show();
                        try {
                            ContentResolver cr = getBaseContext().getContentResolver();
                            InputStream inputStream = cr.openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeFile(filePathcheque);
                           // Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            Cheque = baos.toByteArray();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();

                        }

                        //Setting image to ImageVie

                     //   Toast.makeText(this, "You selected=" + Cheque, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            private String getpath (Uri uri){

                String[] projection = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

                if (cursor != null) {
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    return cursor.getString(column_index);
                } else {
                    return null;
                }


            }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent =  new Intent(Registeration_Activity.this, SalesDashbBoard.class);
        startActivity(intent);
        finish();
    }

        }


