package com.headwayagent.salesadviser_headwaygms.SharedPreference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.headwayagent.salesadviser_headwaygms.activity.CheckInActivity;
import com.headwayagent.salesadviser_headwaygms.activity.LoginActivity;
import com.headwayagent.salesadviser_headwaygms.models.UserCheckin;
import com.headwayagent.salesadviser_headwaygms.models.UserDetails;

/**
 * Created by Sharad
 */

//here for this class we are using a singleton pattern

public class SharedPrefManager{

    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String MY_SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PASSWORD = "keypassword";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_AIN_NUMBER="keyUserId";
    private static final String KEY_DEPARTMENT="DEPARTMENT";
    private static final String KEY_TITLE="title";
    private static final String KEY_PROFILEIMAGE="profileimg";
    private static final String KEY_MODULENAME="modulename";
    private static final String KEY_CITY="city";
    private static final String KEY_STATE="state";
    private static final String KEY_ADDRESS="address";
    private static final String KEY_PIN="pin";
    private static final String KEY_SPONSERNAME="sponsername";
    private static final String KEY_SPONSERID="sponserid";
    private static final String KEY_SDW="sdw";
    private static final String KEY_DESIGNATION="designation";
    private static final String KEY_AADHARNUMBER="aadharnumber";
    private static final String KEY_NOMINEENAME="nomineename";
    private static final String KEY_NOMINEEDOB="nomineedob";
    private static final String KEY_NOMINEE_RELATION="nomineerelation";
    private static final String KEY_PANCARD="pancard";
    private static final String KEY_ACCOUNT_HOLDER_NAME="holdername";
    private static final String KEY_BANK_NAME="bankname";
    private static final String KEY_ACCOUNT_NUMBER="a/c";
    private static final String KEY_IFSC="ifsc";
    private static final String KEY_OFFER_LATER="offerlater";
    private static final String KEY_PROFILE_STATUS="profile_ststus";
    private static final String KEY_USER_ID="userid";
    private static final String KEY_DOWNLINE="downline";
    private static final String KEY_ORDER="order";
    private static final String KEY_AIN="ain";



    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }


    public void userLogin(UserDetails user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_AIN_NUMBER, user.getAin_number());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_DEPARTMENT, user.getDepartment());
        editor.putString(KEY_TITLE, user.getTitle());
        editor.putString(KEY_USERNAME, user.getFull_name());
        editor.putString(KEY_PROFILEIMAGE, user.getProfile_image());
        editor.putString(KEY_MODULENAME, user.getModule_name());
        editor.putString(KEY_CITY, user.getCity());
        editor.putString(KEY_STATE, user.getState());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_ADDRESS, user.getAddress());
        editor.putString(KEY_PIN, user.getPin());
        editor.putString(KEY_SPONSERNAME, user.getSponser_name());
        editor.putString(KEY_SPONSERID, user.getSponser_id());
        editor.putString(KEY_SDW, user.getSdw());
        editor.putString(KEY_DESIGNATION, user.getDesignation());
        editor.putString(KEY_AADHARNUMBER, user.getAadhar_number());
        editor.putString(KEY_NOMINEENAME, user.getNominee_name());
        editor.putString(KEY_NOMINEEDOB, user.getNominee_dob());
        editor.putString(KEY_NOMINEE_RELATION, user.getNomibee_relation());
        editor.putString(KEY_PANCARD, user.getPancard());
        editor.putString(KEY_ACCOUNT_HOLDER_NAME, user.getAccount_holder_name());
        editor.putString(KEY_BANK_NAME, user.getBank_name());
        editor.putString(KEY_ACCOUNT_NUMBER, user.getAccount_number());
        editor.putString(KEY_IFSC, user.getIfsc());
        editor.putString(KEY_OFFER_LATER, user.getOfferLater());
        editor.putString(KEY_PROFILE_STATUS, user.getProfile_status());
        editor.putString(KEY_USER_ID,user.getUserid());
        editor.putString(KEY_DOWNLINE,user.getDownline());
        editor.putString(KEY_ORDER,user.getOrder());

        editor.apply();

    }


    public void userCheckin(UserCheckin user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(MY_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_AIN_NUMBER, user.getAin());

        editor.apply();

    }


    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }


    public boolean isCheckIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(MY_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_AIN_NUMBER, null) != null;
    }


    public UserDetails getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserDetails(
                sharedPreferences.getString(KEY_AIN_NUMBER,null),
                sharedPreferences.getString(KEY_PASSWORD,null),
                sharedPreferences.getString(KEY_PHONE,null),
                sharedPreferences.getString(KEY_DEPARTMENT,null),
                sharedPreferences.getString(KEY_TITLE,null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_PROFILEIMAGE,null),
                sharedPreferences.getString(KEY_MODULENAME,null),
                sharedPreferences.getString(KEY_CITY,null),
                sharedPreferences.getString(KEY_STATE,null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_ADDRESS,null),
                sharedPreferences.getString(KEY_PIN,null),
                sharedPreferences.getString(KEY_SPONSERNAME,null),
                sharedPreferences.getString(KEY_SPONSERID,null),
                sharedPreferences.getString(KEY_SDW,null),
                sharedPreferences.getString(KEY_DESIGNATION,null),
                sharedPreferences.getString(KEY_AADHARNUMBER,null),
                sharedPreferences.getString(KEY_NOMINEENAME,null),
                sharedPreferences.getString(KEY_NOMINEEDOB,null),
                sharedPreferences.getString(KEY_NOMINEE_RELATION,null),
                sharedPreferences.getString(KEY_PANCARD,null),
                sharedPreferences.getString(KEY_ACCOUNT_HOLDER_NAME,null),
                sharedPreferences.getString(KEY_BANK_NAME,null),
                sharedPreferences.getString(KEY_ACCOUNT_NUMBER,null),
                sharedPreferences.getString(KEY_IFSC,null),
                sharedPreferences.getString(KEY_OFFER_LATER,null),
                sharedPreferences.getString(KEY_PROFILE_STATUS,null),
                sharedPreferences.getString(KEY_USER_ID,null),
                sharedPreferences.getString(KEY_DOWNLINE,null),
                sharedPreferences.getString(KEY_ORDER,null));

    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }


    public void checkOut() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(MY_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, CheckInActivity.class));
    }


}