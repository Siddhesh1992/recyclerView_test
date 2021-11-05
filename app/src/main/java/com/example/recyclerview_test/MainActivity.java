package com.example.recyclerview_test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recyclerview_test.models.Plans;
import com.example.recyclerview_test.models.ProductDetails;

import org.json.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    HashSet plans = null;
    ArrayList<Plans> unique_plans = null;
    RecyclerView rvPlans = null;
    PlansAdapter planAdapter = null;
    HashMap<String, ArrayList<Integer>> specificPlan = null;
    Button gold = null;
    Button silver = null;
    Button platinum = null;
    TextView totalView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPlans = (RecyclerView) findViewById(R.id.rvPlans);
        gold = findViewById(R.id.goldBtn);
        silver = findViewById(R.id.silverBtn);
        platinum = findViewById(R.id.platBtn);
        totalView = findViewById(R.id.total);



        gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateData("Gold"); //no domisillary
            }
        });

        silver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateData("Silver");
            }
        });

        platinum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateData("Platinum");
            }
        });

        // Initialize contacts
        unique_plans = new ArrayList<>();
        specificPlan = new HashMap();
        // Create adapter passing in the sample user data
        planAdapter = new PlansAdapter(unique_plans, new PlansAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(Plans item, int position) {
                ArrayList<ProductDetails> productDetails = item.getProduct_detail();
                int allPremium = 0;
                for(int i = 0; i < productDetails.size(); ++i) {
                    allPremium += productDetails.get(i).getPremium();
                }

                int total = Integer.parseInt(totalView.getText().toString());
                total += allPremium;

                totalView.setText(String.valueOf(total));

                Toast.makeText(getApplicationContext(), "Added " + String.valueOf(allPremium), Toast.LENGTH_SHORT).show();
            }
        });
        // Attach the adapter to the recyclerview to populate items
        rvPlans.setAdapter(planAdapter);
        // Set layout manager to position the items
        rvPlans.setLayoutManager(new LinearLayoutManager(this));

        Runnable runner = new Runnable() {
            public void run() {
                initDataApi();
            }
        };

        runner.run();

    }

    private void calculateData(String planType) {
        if(unique_plans != null) {
            for(int i = 0; i < unique_plans.size(); ++i) {
                ArrayList specificPlanArray = specificPlan.get(planType);
                unique_plans.get(i).setCovered(false);
                if(specificPlanArray.contains(unique_plans.get(i).getProduct_feature_id())) {
                    unique_plans.get(i).setCovered(true);
                }
                Log.d("particular plans", specificPlanArray.toString());
            }

            planAdapter.notifyDataSetChanged();
        }
    }

    public void initDataApi() {

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://eb.fynity.in/api/admin/get-all-quotes", new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                try {

                JSONObject obj1 = new JSONObject(response);
                JSONArray objArray1 = obj1.getJSONArray("data");
                    plans = new HashSet<Integer>();
//                    unique_plans = new ArrayList();
                for(int i = 0; i < objArray1.length(); ++i) {
                    JSONArray planFeatureArray = objArray1.getJSONObject(i).getJSONArray("plan_product_features");
                    ArrayList<Integer> planFetureIds = new ArrayList();
                    for(int j = 0; j < planFeatureArray.length(); ++j) {
                        JSONObject planFeatureObj = planFeatureArray.getJSONObject(j);
                        int planFetureId = (int) planFeatureObj.get("product_feature_id");

                        planFetureIds.add(planFetureId);

                        if(plans.add(planFetureId)) {
                            int id = planFeatureObj.getInt("id");
                            int product_feature_id = planFeatureObj.getInt("product_feature_id");
                            String product_feature_name = planFeatureObj.getString("product_feature_name");
//                            int product_type = planFeatureObj.getInt("product_type");
//                            String content = planFeatureObj.getString("content");;
//                            int order = planFeatureObj.getInt("order");
//                            int is_mandantory = planFeatureObj.getInt("is_mandantory");
                            ArrayList<ProductDetails> product_detail = new ArrayList();
                            JSONArray product_detailArray = planFeatureObj.getJSONArray("product_detail");
                            for(int k = 0; k < product_detailArray.length(); ++k){
                                JSONObject product_detailObj = product_detailArray.getJSONObject(k);
                                int product_detail_id = product_detailObj.getInt("id");
                                int plan_feature_mapping_id = product_detailObj.getInt("plan_feature_mapping_id");
                                int premium = product_detailObj.getInt("premium");
//                                int plan_feature_mapping_id = 704;
//                                String name = null;
//                                int is_wavied_off = 0;
//                                int sum_insured = 0;
//                                int sum_insured_type = 0;
//                                int deductible_from = 0;
//                                int premium = 0;
//                                int premium_type = 1;
//                                int duration_type = 0;
//                                int duration_value = 0;
//                                int duration_unit = 0;
                                product_detail.add(new ProductDetails(product_detail_id, plan_feature_mapping_id, premium));
                            }
                            unique_plans.add(new Plans(id, product_feature_id, product_feature_name, product_detail));
                        }
                    }
                    specificPlan.put(objArray1.getJSONObject(i).getString("name"), planFetureIds);
                }
                   Log.d("unique plans", unique_plans.toString());
                    planAdapter.notifyDataSetChanged();
                }catch(Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("responseVolleyPlus", error.toString());
            }
        });
        HashMap<String, String> params = new HashMap<>();
        params.put("enquiry_id", "nzX/KYDjD1GH");
        stringRequest.setParams(params);
        mRequestQueue.add(stringRequest);
    }
}