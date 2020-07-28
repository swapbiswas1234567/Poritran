package com.example.poritraanvolunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class SubmitPhoto extends AppCompatActivity {

    Button home, add, pending, approved, my, logout;
    TextView noOfReq;
    ListView lView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_photo);

        init();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitPhoto.this, DonorActivity.class);
                startActivity(intent);
                customType(SubmitPhoto.this, "right-to-left");
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitPhoto.this, WaitingForApproval.class);
                startActivity(intent);
                customType(SubmitPhoto.this, "right-to-left");
            }
        });

        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SubmitPhoto.this, WaitingForApproval.class);
                //startActivity(intent);
                //customType(SubmitPhoto.this, "right-to-left");
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void init(){
        home = findViewById(R.id.homeSP);
        add = findViewById(R.id.addSP);
        pending = findViewById(R.id.pendingSP);
        approved = findViewById(R.id.approvedSP);
        my = findViewById(R.id.mySP);
        logout = findViewById(R.id.logoutSP);
        noOfReq = findViewById(R.id.noOfreqSP);

        lView = findViewById(R.id.lViewSP);
    }
}