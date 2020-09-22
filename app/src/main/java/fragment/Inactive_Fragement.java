package fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.BaseUrl;
import com.headwayagent.salesadviser_headwaygms.API_Integration_Asset.VolleySingleton;
import com.headwayagent.salesadviser_headwaygms.R;
import com.headwayagent.salesadviser_headwaygms.SharedPreference.SharedPrefManager;
import com.headwayagent.salesadviser_headwaygms.activity.TotalDownlineActivity;
import com.headwayagent.salesadviser_headwaygms.activity.TotalIndirectCommissionActivity;
import com.headwayagent.salesadviser_headwaygms.adapter.Iactive_adapter;
import com.headwayagent.salesadviser_headwaygms.adapter.TotalDirectCommissionAdapter;
import com.headwayagent.salesadviser_headwaygms.adapter.TotalDownlineAdapter;
import com.headwayagent.salesadviser_headwaygms.models.TotalDirectCommisionModel;
import com.headwayagent.salesadviser_headwaygms.models.TotalDownlineModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Inactive_Fragement extends Fragment {

    private RecyclerView recyclerView;
    private List<TotalDownlineModel> approveModelList = new ArrayList<>();
    private ProgressBar progressBar;
    private String ain ;
    private TextView textView;
    public static String Ain="null";


    public Inactive_Fragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.inactive, container, false);

        recyclerView=view.findViewById(R.id.alldatarecycler);
        progressBar=view.findViewById(R.id.progressbar);
        textView = view.findViewById(R.id.inactive);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        ain=SharedPrefManager.getInstance(getActivity()).getUser().getAin_number();

        getiactivestatus();

        return view;
    }


    private void getiactivestatus()
    {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.VIEW_DOWNLINE+ain,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();

                        try {

                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("msg");
                            // Toast.makeText(TotalDownlineActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                            if (message.equals("Success")) {


                                JSONArray approveArray = obj.getJSONArray("inactive");



                                for (int y = 0; y < approveArray.length(); y++) {

                                    JSONObject jsonObject = approveArray.getJSONObject(y);

                                    String order_total  = jsonObject.getString("full_name");
                                   Ain= jsonObject.getString("ain");

                                    approveModelList.add(new TotalDownlineModel(Ain,order_total));


                                }
                                if(approveModelList.size()==0){
                                    textView.setVisibility(View.VISIBLE);

                                }

                                Iactive_adapter totalDownlineAdapter = new Iactive_adapter(approveModelList, getActivity());
                                recyclerView.setAdapter(totalDownlineAdapter);
                                totalDownlineAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getActivity(), "You Have No InDirect Commission "+obj.getString("msg"), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Server Not Responding Check Inernet Connection", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {

        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

}
