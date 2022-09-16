package com.example.libraries;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;



import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dialoglibs.DialogInput;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddUsers {
    private Context contextAddPage;
    private Activity activity;
    private Class classMainPage;
    private ImageView imgView;

    public AddUsers( Class classMainPage,Context contextAddPage,Activity activity,ImageView imgView) {
        this.classMainPage = classMainPage;
        this.contextAddPage = contextAddPage;
        this.activity=activity;
        this.imgView=imgView;
    }

    public String encodeImgString(ImageView imgView){
        Bitmap bitmap= ((BitmapDrawable) imgView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
    }

    public void addDataToDatabase(String username, String email , String extension, String url) {
        DialogInput dialogInput=new DialogInput(contextAddPage);
        dialogInput.setProgressbar(View.VISIBLE).show();
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(contextAddPage);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    dialogInput.setProgressbar(View.GONE).setSubtitle(jsonObject.getString("message")).image_success().setFirstButtonText("OK").withFirstButtonListner(view -> {
                        Intent intent=new Intent(contextAddPage,classMainPage);
                        activity.startActivity(intent);
                        activity.finish();
                    }).show();
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

    public void ImageChooser(){
        ImagePicker.with(activity)
                .crop(1f, 1f)    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }
}
