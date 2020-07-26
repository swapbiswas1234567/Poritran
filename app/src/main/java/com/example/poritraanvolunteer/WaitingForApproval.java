package com.example.poritraanvolunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class WaitingForApproval extends AppCompatActivity {

    Button home, add, pending, approved, submit, my, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_approval);

        home = findViewById(R.id.homeWA);
    }
}
