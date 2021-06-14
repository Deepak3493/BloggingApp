package com.example.blogapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blogapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import Model.Blog;
public class Add_Post extends AppCompatActivity {
    private ImageButton imageButton;
    private EditText Title;
    private EditText Description;
    private ProgressBar mprogress;
    private Button submitButton;
    private DatabaseReference mPostDatabse;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorage;
    private Uri imageUri;
    public static final int GALLERY_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__post);
        mStorage = FirebaseStorage.getInstance().getReference();//FromUrl("gs://blogapp-3557e.appspot.com/");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mPostDatabse= mDatabase.getReference().child("mBlog_images");
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
            }
        });

        Title = findViewById(R.id.postTitle);
        Description = findViewById(R.id.postDescription);
        submitButton = findViewById(R.id.submit_post);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Posting to our database
                StartPosting();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imageButton.setImageURI(imageUri);
        }
    }

                                                                //    Run kar rha hon kr rha
    private void StartPosting(){
     //   mprogress.setVisibility(View.VISIBLE);
        String titletext = Title.getText().toString();
        String descriptiontext = Description.getText().toString();
        if (!TextUtils.isEmpty(titletext) && !TextUtils.isEmpty(descriptiontext)
                && imageUri != null)  {
            ///continue to add post
            StorageReference filepath = mStorage.child("mBlog_images").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                            DatabaseReference newpost = mPostDatabse.push();
                            HashMap<String, String> dataTosave = new HashMap<String,String>();
                            dataTosave.put("Title", titletext);
                            dataTosave.put("image", uri.toString());
                            dataTosave.put("Description", descriptiontext);
                            dataTosave.put("timeStamp", String.valueOf(System.currentTimeMillis()));
                            dataTosave.put("userId", mUser.getUid().toString());
                            newpost.setValue(dataTosave);
//                            mprogress.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Posted", Toast.LENGTH_SHORT).show();
                            Log.v("asdf","posted");
                            startActivity(new Intent(Add_Post.this, PostListActiivity.class));
                            finish();

                        }
                    });
                   }
            });
        }
        else{
            Toast.makeText(this, "can't post right now", Toast.LENGTH_SHORT).show();
        }
    }
};