package com.example.usersmanagementlibraries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.libraries.ReadUsers;


public class PageReadUsers extends AppCompatActivity {
    //   Use This Variable With Same Name
    int custom_layout_user=R.layout.custom_layout_user;
    int textView_id=R.id.textView_id;
    int textView_name=R.id.textView_name;
    int imageView=R.id.imageView;
    //   Place Your URl Inside Variable url as String
    String url="https://elctronics-tech.000webhostapp.com/read.php";
    //Define These Variable
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_read_users);
        recyclerView=findViewById(R.id.recycler);
        ReadUsers toaster=new ReadUsers(this,layoutManager,adapter,recyclerView,custom_layout_user ,textView_id , textView_name,imageView,url);
        toaster.readDataFromDatabase();
    }
}