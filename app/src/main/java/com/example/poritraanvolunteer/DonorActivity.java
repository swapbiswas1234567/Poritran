package com.example.poritraanvolunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class DonorActivity extends AppCompatActivity {
    public String userName="Shadman Aadeeb";
    public String userNID="13579";

    ListView listView;
    CustomAdapter customAdapter;
    ProgressBar progressBar;
    //Button availableButton,pendingButton,clearedButton;

    Button add, up, pending, approved, my, logout;
    ArrayList<Transaction> allRequests;

    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Log Out For Exit", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);

        init();
        retrieveAllRequests();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FunctionVariable.NID.equals("NULL")){
                    Toast.makeText(getApplicationContext(), "You logged in as public", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(DonorActivity.this, AddRequest.class);
                startActivity(intent);
                customType(DonorActivity.this, "left-to-right");
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FunctionVariable.NID.equals("NULL")){
                    Toast.makeText(getApplicationContext(), "You logged in as public", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(DonorActivity.this, WaitingForApproval.class);
                startActivity(intent);
                customType(DonorActivity.this, "left-to-right");
            }
        });

        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Feature Coming Soon", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(DonorActivity.this, Approved.class);
                startActivity(intent);
                customType(DonorActivity.this, "left-to-right");*/
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Feature Coming Soon", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(DonorActivity.this, SubmitPhoto.class);
                startActivity(intent);
                customType(DonorActivity.this, "left-to-right");*/
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Feature Coming Soon", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(DonorActivity.this, MyWork.class);
                startActivity(intent);
                customType(DonorActivity.this, "left-to-right");*/
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(DonorActivity.this);
                alertDialogBuilder.setMessage("Are you sure want to logout?");
                alertDialogBuilder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FunctionVariable.NID = "NULL";
                                Intent intent = new Intent(DonorActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    void init(){
        add = findViewById(R.id.addDA);
        pending = findViewById(R.id.pendingDA);
        approved = findViewById(R.id.approvedDA);
        my = findViewById(R.id.myDA);
        logout = findViewById(R.id.logoutDA);
        up = findViewById(R.id.uploadDA);
        listView = findViewById(R.id.listViewHP);

        allRequests = new ArrayList<>();
    }

    void retrieveAllRequests(){
        DatabaseReference d = FirebaseDatabase.getInstance().getReference("Request");
        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot i:dataSnapshot.getChildren()){
                    for(DataSnapshot j: i.getChildren()){
                        Transaction t = j.getValue(Transaction.class);
                        if(t.getStatus()==0)    allRequests.add(t);
                    }
                }

                CustomHomePage c = new CustomHomePage(DonorActivity.this, allRequests);
                listView.setAdapter(c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //availableButton.callOnClick();
    }




}