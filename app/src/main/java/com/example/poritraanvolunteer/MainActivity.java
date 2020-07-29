package com.example.poritraanvolunteer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText id,password;
    Button log_in;
    TextView forgot,sign_up;
    CheckBox rememberMeCheckBox;
    Button testButton;
    ProgressBar progressBar;
    Button home;


    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = new Intent(MainActivity.this, AddRequest.class);
        //startActivity(intent);
        progressBar=findViewById(R.id.progressBar);
        //rememberMeCheckBox=findViewById(R.id.rememberMeCheckBox);
        id=findViewById(R.id.id);
        password=findViewById(R.id.password);
        log_in=findViewById(R.id.log_in);
        forgot=findViewById(R.id.forgot);
        sign_up=findViewById(R.id.sign_up);
        home=findViewById(R.id.home);


        String remeber;
        SharedPreferences sharedPreferences=getSharedPreferences("rememberFile",MODE_PRIVATE);

        remeber =sharedPreferences.getString("nid_login","123");
        if (remeber.equals("123"))
        {

        }
        else if(remeber.equals("NULL"))
        {

        }
        else
        {
            id.setText(remeber);
        }


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(getApplicationContext(),"Clicked on sign up", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this,sign_up.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Clicked on forgot pass", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this,forgot_pass.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences3=getSharedPreferences("rememberFile",MODE_PRIVATE);
                SharedPreferences.Editor editor2=sharedPreferences3.edit();
                editor2.putString("nid_login","NULL");


                editor2.apply();

                FunctionVariable.NID="NULL";
                Intent intent=new Intent(MainActivity.this,DonorActivity.class);
                startActivity(intent);




            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id_s=id.getText().toString();
                final String password_s=password.getText().toString();

                if (id_s.isEmpty() && password_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter ID and Password", Toast.LENGTH_LONG).show();
                }
                else if(id_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter ID",Toast.LENGTH_LONG).show();
                }
                else if(password_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_LONG).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    String str = id_s;
                    final String strNew = str.replace(".", "");

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("user");
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(strNew)) {

                                user u1=snapshot.child(strNew).getValue(user.class);
                                if (u1.password.equals(password_s))
                                {
                                    {
                                        //The remember me checkbox code has been dealt with
                                        FunctionVariable.NID=strNew;
                                        Intent intent=new Intent(MainActivity.this,AddRequest.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                        SharedPreferences sharedPreferences3=getSharedPreferences("rememberFile",MODE_PRIVATE);
                                        SharedPreferences.Editor editor2=sharedPreferences3.edit();
                                        editor2.putString("nid_login",strNew);


                                        editor2.apply();


                                        progressBar.setVisibility(View.GONE);
                                        startActivity(intent);
                                        //
                                    }


                                }
                                else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(),"invalid login credentials", Toast.LENGTH_LONG).show();
                                }


                            }
                            else
                            {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"invalid login credentials",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });



    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}
