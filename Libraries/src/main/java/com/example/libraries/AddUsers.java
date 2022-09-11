package com.example.libraries;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;



import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddUsers {
    private Context contextAddPage;
    private Activity activity;
    private int layout_progress_dialog;
    private Class classMainPage;
    private  ProgressDialog pdialog;
    private ImageView imgView;




    public AddUsers( Class classMainPage,Context contextAddPage,Activity activity,ProgressDialog pdialog, int layout_progress_dialog,ImageView imgView) {
        this.classMainPage = classMainPage;
        this.contextAddPage = contextAddPage;

        this.activity=activity;
        this.layout_progress_dialog = layout_progress_dialog;
        this.pdialog=pdialog;
        this.imgView=imgView;
    }

    public String encodeImgString(ImageView imgView){
        Bitmap bitmap= ((BitmapDrawable) imgView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
    }

    public void addDataToDatabase(String username, String email , String extension, String url) {
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(contextAddPage);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Intent intent=new Intent(contextAddPage,classMainPage);
                activity.startActivity(intent);
                pdialog.hide();
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(contextAddPage, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.hide();
                // method to handle errors.
                Toast.makeText(contextAddPage, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();
                // on below line we are passing our
                // key and value pair to our parameters.
                if (extension == null)
                {
                    params.put("username", username);
                    params.put("email", email);
                    params.put("noimage", "noimage");
                }
                else {
                    params.put("username", username);
                    params.put("email", email);
                    params.put("image",encodeImgString(imgView));
                    params.put("extension",extension);
                }
                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    public void funProgressDialog(){
        pdialog =new ProgressDialog(contextAddPage);
        pdialog.show();
        pdialog.setCancelable(false);
        pdialog.setContentView(layout_progress_dialog);

    }

    public void ImageChooser(){
        ImagePicker.with(activity)
                .crop(1f, 1f)    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

}
