package com.example.poritraanvolunteer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class password_change extends AppCompatActivity {

    EditText pass,new_pass,re_new_pass;
    Button submit;

    String pass_s;
    String new_pass_s;
    String re_new_pass_s;

//    public static String name_s=acc_info.name_s;
//    public static String address_s=acc_info.address_s;
//    public static String contact_s=acc_info.contact_s;
//    public static String mail_s=acc_info.mail_s;
//    public static String password_s=acc_info.password_s;
//    public static String description_s=acc_info.description_s ;
//    public static String dob_s=acc_info.dob_s;
//    public static String role_s=acc_info.role_s;
    public static String key;
    //=acc_info.key;
    String old_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        pass=findViewById(R.id.pass);
        new_pass=findViewById(R.id.new_pass);
        re_new_pass=findViewById(R.id.retype_new_pass);

        submit=findViewById(R.id.submit);

        SharedPreferences sharedPreferences=getSharedPreferences("rememberFile",MODE_PRIVATE);
        old_pass=sharedPreferences.getString("password","");
        key=sharedPreferences.getString("key","");



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass_s=pass.getText().toString();
                new_pass_s=new_pass.getText().toString();
                re_new_pass_s=re_new_pass.getText().toString();
               // Toast.makeText(getApplicationContext(),pass_s+" "+new_pass_s+re_new_pass_s,Toast.LENGTH_LONG).show();
                if(pass_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter current password",Toast.LENGTH_LONG).show();
                }
                else if(new_pass_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter new password",Toast.LENGTH_LONG).show();
                }
                else if(re_new_pass_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter new password",Toast.LENGTH_LONG).show();
                }
                else if (!new_pass_s.equals(re_new_pass_s))
                {
                    Toast.makeText(getApplicationContext(),"New password and retype new password did not match",Toast.LENGTH_LONG).show();
                }
                else if (!pass_s.equals(old_pass))
                {
                   // Toast.makeText(getApplicationContext(),,Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"Current password incorrect",Toast.LENGTH_LONG).show();
                }
                else
                {
                    AlertDialog.Builder myAlertDialogBuilder = new AlertDialog.Builder(password_change.this);
                    myAlertDialogBuilder.setMessage("Are you sure to update your password?");
                    myAlertDialogBuilder.setCancelable(true);
                    myAlertDialogBuilder.setTitle("Confirmation");
                    myAlertDialogBuilder.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                        DatabaseReference myRef;
                                        myRef = FirebaseDatabase.getInstance().getReference("user");
                                        myRef.child(key).child("password").setValue(new_pass_s);

                                        SharedPreferences sharedPreferences3=getSharedPreferences("rememberFile",MODE_PRIVATE);
                                        SharedPreferences.Editor editor2=sharedPreferences3.edit();
                                        editor2.putString("password",new_pass_s);
                                        editor2.apply();

                                        Toast.makeText(getApplicationContext(),"Password successfully updated", Toast.LENGTH_LONG).show();
                                        Intent intent=new Intent(password_change.this,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                }
                            });

                    myAlertDialogBuilder.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    AlertDialog alertDialog = myAlertDialogBuilder.create();
                    alertDialog.show();
                }

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.home_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            Intent intent=new Intent(getApplicationContext(),AddRequest.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            // do something here
        }
        return super.onOptionsItemSelected(item);
    }
}