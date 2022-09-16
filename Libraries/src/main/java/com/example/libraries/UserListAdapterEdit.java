package com.example.libraries;
import android.content.Context;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.bumptech.glide.Glide;
        import com.example.dialoglibs.DialogInput;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;

public class UserListAdapterEdit extends RecyclerView.Adapter<UserListAdapterEdit.UserEditViewHolder> {
    private ArrayList<User> usersLists;
    private Context context;
    private static int customLayout,e_id,e_name,e_image;
    DialogInput dialogInput1;

    @NonNull
    @Override
    public UserEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(customLayout,parent,false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        UserEditViewHolder evh=new UserEditViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserEditViewHolder holder, int position) {

        User user = usersLists.get(position);
        // Set the data to the views here
        holder.id.setText(Integer.toString(user.getId()));
        holder.name.setText(user.getName());
        Glide.with(context).load(user.getImage()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialogInput1=new DialogInput(context);
                dialogInput1.setTitle("User Information"+ Integer.toString(holder.getAdapterPosition()+1))
                        .setFirstTextField("Username:",user.getName())
                        .setSecondTextField("Email:",user.getEmail())
                        .isEnabledFirstTextField(true)
                        .isEnabledSecondTextField(true)
                        .setIconString(user.getImage())
                        .setFirstButtonText("OK")
                        .setSecondButtonText("Cencel")
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                           editDataFromDatabase(holder.getAdapterPosition()+1,dialogInput1.getFirstTextField(),dialogInput1.getSecondTextField());
                            }
                        })
                        .withSecondButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogInput1.dismiss();
                            }
                        }).show();
            }
        });
    }

    public  void editDataFromDatabase(int id,String username,String email) {
        String url="https://elctronics-tech.000webhostapp.com/edit.php";
        DialogInput dialogInput=new DialogInput(context);
        dialogInput.setProgressbar(View.VISIBLE).show();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    dialogInput.setProgressbar(View.GONE).setSubtitle(jsonObject.getString("message")).image_success().setFirstButtonText("OK").withFirstButtonListner(view -> {
                      dialogInput.dismiss();
                        dialogInput1.dismiss();
                    }).show();
                } catch (JSONException e) {
                     dialogInput.setProgressbar(View.GONE).image_fail().setFirstButtonText("OK").withFirstButtonListner(view -> {dialogInput.dismiss();dialogInput1.dismiss();}).show();
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogInput.setProgressbar(View.GONE).image_fail().setFirstButtonText("OK").withFirstButtonListner(view -> {dialogInput.dismiss();dialogInput1.dismiss();}).show();
                // method to handle errors.
//                Toast.makeText(context, "Fail to get response zzz = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("id", Integer.toString(id));
                params.put("username", username);
                params.put("email", email);
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    @Override
    public int getItemCount() {
        return usersLists == null? 0: usersLists.size();
    }

    public static class UserEditViewHolder extends RecyclerView.ViewHolder{

        public TextView id;
        public TextView name;
        public ImageView image;
        public UserEditViewHolder(@NonNull View itemView){
            super(itemView);
            id=itemView.findViewById(e_id);
            name=itemView.findViewById(e_name);
            image=itemView.findViewById(e_image);
        }

    }

    public UserListAdapterEdit(ArrayList<User> usersLists, Context context, int customLayout, int e_id, int e_name, int e_image) {
        this.usersLists = usersLists;
        this.context = context;
        this.customLayout = customLayout;
        this.e_id = e_id;
        this.e_name = e_name;
        this.e_image = e_image;
    }
}

