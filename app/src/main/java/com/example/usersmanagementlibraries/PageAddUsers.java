package com.example.usersmanagementlibraries;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.libraries.AddUsers;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class PageAddUsers extends AppCompatActivity {

    Button chooseImage,upload;
    AddUsers addUsers;
    ImageView userImage;
    EditText uname,uemail;
    ProgressDialog pDialog;
    String ext;
    String url = "https://elctronics-tech.000webhostapp.com/index.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_add_users);
        chooseImage=findViewById(R.id.chooseimage);
        userImage=findViewById(R.id.imageViewuser);
        uname=findViewById(R.id.editTextTextPersonName);
        uemail=findViewById(R.id.editTextTextPersonName2);
        upload=findViewById(R.id.uploaddata);
        addUsers=new AddUsers(MainActivity.class,PageAddUsers.this,PageAddUsers.this,pDialog,R.layout.progress_dialog,userImage);
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUsers.ImageChooser();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload.setEnabled(false);
                addUsers.funProgressDialog();
                addUsers.addDataToDatabase(uname.getText().toString(),uemail.getText().toString(),ext,url);
            }
        });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri= data.getData();
            userImage.setImageURI(uri);
            ext=uri.getPath().substring(uri.getPath().lastIndexOf("."));
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

}