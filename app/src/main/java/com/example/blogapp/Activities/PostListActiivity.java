 package com.example.blogapp.Activities;

 import android.content.Intent;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.Menu;
 import android.view.MenuItem;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import com.example.blogapp.R;
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.auth.FirebaseUser;
 import com.google.firebase.database.ChildEventListener;
 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;

 import java.util.ArrayList;
 import java.util.Collections;

 import Data.RecyclerAdapter;
 import Model.Blog;

 public class PostListActiivity extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    protected RecyclerAdapter RecyclerAdapter;
    private ArrayList<Blog> blogList;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list_actiivity);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("mBlog_images");
        mDatabaseReference.keepSynced(true);
        blogList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                if (mUser != null && mAuth != null) {
                    startActivity(new Intent(PostListActiivity.this, Add_Post.class));
                    finish();
                }
                break;
            case R.id.action_signOut:
                if (mUser != null && mAuth != null) {
                    mAuth.signOut();
                    startActivity(new Intent(PostListActiivity.this, MainActivity.class));
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.v("dfgh", "dfdffdvdfvfdvf"+snapshot.getChildrenCount());
//                //Blog blog=null;
//
//                for(DataSnapshot s:snapshot.getChildren()){
//
//                Blog blog= new Blog(s.child("Title").getValue().toString(),s.child("image").getValue().toString(),s.child("Description").getValue().toString(),s.child("timeStamp").getValue().toString(),s.child("userId").getValue().toString());
//                blogList.add(blog);
//                Log.v("qwerty",s.child("Title").getValue().toString());
//                }
//                Collections.reverse(blogList);
//                RecyclerAdapter = new RecyclerAdapter(PostListActiivity.this , blogList);
//                recyclerView.setAdapter(RecyclerAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String h) {
                       //Long.toString(dataSnapshot.getChildrenCount()));
                for(DataSnapshot s:dataSnapshot.getChildren()){

                Blog blog= new Blog(s.child("Title").getValue().toString(),s.child("image").getValue().toString(),s.child("Description").getValue().toString(),s.child("timeStamp").getValue().toString(),s.child("userId").getValue().toString());
                blogList.add(blog);
                Log.v("qwerty",s.child("Title").getValue().toString());
                }
                Collections.reverse(blogList);
                RecyclerAdapter = new RecyclerAdapter(PostListActiivity.this , blogList);
                recyclerView.setAdapter(RecyclerAdapter);
                notifyAll();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
