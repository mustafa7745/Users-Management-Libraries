package com.example.usersmanagementlibraries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.libraries.EditUsers;
import com.example.libraries.ReadUsers;

public class PageEditUsers extends AppCompatActivity {
    int custom_layout_user=R.layout.custom_layout_user;
    int textView_id=R.id.textView_id;
    int textView_name=R.id.textView_name;
    int imageView=R.id.imageView;
    String url="https://elctronics-tech.000webhostapp.com/read.php";
    //Define These Variable
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_edit_users);
        recyclerView=findViewById(R.id.recyclerEdit);
        EditUsers toaster=new EditUsers(this,layoutManager,adapter,recyclerView,custom_layout_user ,textView_id , textView_name,imageView,url);
        toaster.editDataFromDatabase();
    }
}