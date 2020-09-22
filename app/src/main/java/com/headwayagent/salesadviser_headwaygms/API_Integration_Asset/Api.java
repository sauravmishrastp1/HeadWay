package com.headwayagent.salesadviser_headwaygms.API_Integration_Asset;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("register-cda-step-three")
    Call<ResponseBody>registercdastepthree(

    );
}
