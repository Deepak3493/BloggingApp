 package com.example.blogapp.Activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.blogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

 public class MainActivity extends AppCompatActivity {
     private FirebaseAuth mAuth;
     private FirebaseAuth.AuthStateListener mAuthListener;
     private TextInputEditText email;
     private TextInputEditText password;
     private Button signIn;
     private Button createAccount;
     private ProgressBar progressBar;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         email = findViewById(R.id.email);
         email.setError(null);
         password = findViewById(R.id.password);
         password.setError(null); 
         signIn = (Button) findViewById(R.id.signIn);

         createAccount = (Button) findViewById(R.id.create_account);
         createAccount.setBackgroundColor(Color.WHITE);
         progressBar = findViewById(R.id.progressBar);
         progressBar.setVisibility(View.INVISIBLE);
         createAccount.setTextColor(Color.rgb(200,0,0));
         mAuth = FirebaseAuth.getInstance();
         mAuthListener = new FirebaseAuth.AuthStateListener() {
             @Override
             public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 FirebaseUser user = mAuth.getCurrentUser();
                 if (user == null) {
                     Toast.makeText(MainActivity.this, "user is signed out", Toast.LENGTH_SHORT).show();
                      //startActivity(new Intent(MainActivity.this,Add_Post.class));
                 } else {
                     Toast.makeText(MainActivity.this, "user is signed in", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(MainActivity.this,PostListActiivity.class));
                 }
             }
         };

         signIn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 progressBar.setVisibility(View.VISIBLE);
                 String emailStr = email.getText().toString();
                 String pass = password.getText().toString();
                 if (!emailStr.isEmpty() && !pass.isEmpty()) {
                     mAuth.signInWithEmailAndPassword(emailStr, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful()) {
                                 Toast.makeText(MainActivity.this, "user is logged in successfully", Toast.LENGTH_SHORT).show();
                                 progressBar.setVisibility(View.INVISIBLE);
                                 startActivity(new Intent(MainActivity.this,PostListActiivity.class));
                             } else {
                                 Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                                 progressBar.setVisibility(View.INVISIBLE);
                             }
                         }
                     });
                 }
             }
         });

         createAccount.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 progressBar.setVisibility(View.VISIBLE);
                 String emailStr = email.getText().toString();
                 String pass = password.getText().toString();
                 if (!emailStr.isEmpty() && !pass.isEmpty()) {

                     mAuth.createUserWithEmailAndPassword(emailStr, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task != null) {
                                 progressBar.setVisibility(View.INVISIBLE);
                                 Toast.makeText(MainActivity.this, "Account is created succesfully", Toast.LENGTH_SHORT).show();
                             } else {
                                 progressBar.setVisibility(View.INVISIBLE);
                                 Toast.makeText(MainActivity.this, "Fialed to create account", Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
                 }

             }
         });
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         if(item.getItemId()==R.id.action_signOut){
             mAuth.signOut();
         }
         return super.onOptionsItemSelected(item);
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.main_menu,menu);
         return super.onCreateOptionsMenu(menu);
     }

     @Override
     protected void onStart() {
         super.onStart();
         mAuth.addAuthStateListener(mAuthListener);
     }

     @Override
     protected void onStop() {
         super.onStop();
         if(mAuthListener==null){
             mAuth.removeAuthStateListener(mAuthListener);
         }
     }
 };