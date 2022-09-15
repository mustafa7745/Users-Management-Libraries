package com.example.libraries;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private ArrayList<User> usersLists;
    private  Context context;
    private static int customLayout,e_id,e_name,e_image;


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(customLayout,parent,false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        UserViewHolder evh=new UserViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user = usersLists.get(position);
        // Set the data to the views here
        holder.id.setText(Integer.toString(user.getId()));
        holder.name.setText(user.getName());
        Glide.with(context).load(user.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return usersLists == null? 0: usersLists.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        public TextView id;
        public TextView name;
        public ImageView image;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(e_id);
            name=itemView.findViewById(e_name);
            image=itemView.findViewById(e_image);
        }

    }

    public UserListAdapter(ArrayList<User> usersLists, Context context, int customLayout, int e_id, int e_name, int e_image) {
        this.usersLists = usersLists;
        this.context = context;
        this.customLayout = customLayout;
        this.e_id = e_id;
        this.e_name = e_name;
        this.e_image = e_image;
    }
}

