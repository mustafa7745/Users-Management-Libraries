package com.example.libraries;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dialoglibs.DialogInput;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditUsers {
    private Context context;
    private   RecyclerView.LayoutManager layoutManager;
    private   RecyclerView.Adapter adapter;
    private   RecyclerView recyclerView;
    private   int customLayout,e_id,e_name,e_image;
    private   String url;

    public EditUsers(Context context, RecyclerView.LayoutManager layoutManager, RecyclerView.Adapter adapter, RecyclerView recyclerView, int customLayout, int e_id, int e_name, int e_image, String url) {
        this.context = context;
        this.layoutManager = layoutManager;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.customLayout = customLayout;
        this.e_id = e_id;
        this.e_name = e_name;
        this.e_image = e_image;
        this.url = url;
    }
    public  void editDataFromDatabase() {
        DialogInput dialogInput=new DialogInput(context);
        dialogInput.setProgressbar(View.VISIBLE).show();
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(context);
        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("message");
                    ArrayList<User> userArrayList=new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        userArrayList.add(new User(
                                jsonArray.getJSONObject(i).getString("username"),
                                jsonArray.getJSONObject(i).getString("img"),
                                jsonArray.getJSONObject(i).getString("email"),
                                Integer.parseInt(jsonArray.getJSONObject(i).getString("i_d")))
                        );
                    }
                    layoutManager=new LinearLayoutManager(context);
                    adapter =new UserListAdapterEdit(userArrayList,context,customLayout,e_id,e_name,e_image);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    dialogInput.dismiss();
                } catch (JSONException e) {
                    dialogInput.setProgressbar(View.GONE).image_fail().setFirstButtonText("OK").withFirstButtonListner(view -> {dialogInput.dismiss();}).show();
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogInput.setProgressbar(View.GONE).image_fail().setFirstButtonText("OK").withFirstButtonListner(view -> {dialogInput.dismiss();}).show();
                // method to handle errors.
//                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.

                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
}
