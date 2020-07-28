package com.example.poritraanvolunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class Approved extends AppCompatActivity {

    Button home, add, pending, up, my, logout;
    TextView noOfReq, refresh;
    ListView lView;
    ArrayList<Transaction> donatedRequests;

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Log Out For Exit", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved);

        init();
        retrieveDonatedRequests();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveDonatedRequests();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Approved.this, DonorActivity.class);
                startActivity(intent);
                customType(Approved.this, "right-to-left");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Approved.this, AddRequest.class);
                startActivity(intent);
                customType(Approved.this, "right-to-left");
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Approved.this, WaitingForApproval.class);
                startActivity(intent);
                customType(Approved.this, "right-to-left");
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Approved.this, SubmitPhoto.class);
                startActivity(intent);
                customType(Approved.this, "left-to-right");
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Approved.this, MyWork.class);
                startActivity(intent);
                customType(Approved.this, "left-to-right");
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(Approved.this);
                alertDialogBuilder.setMessage("Are you sure want to logout?");
                alertDialogBuilder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Approved.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    void init(){
        home = findViewById(R.id.homeA);
        add = findViewById(R.id.addA);
        pending = findViewById(R.id.pendingA);
        up = findViewById(R.id.uploadA);
        my = findViewById(R.id.myA);
        logout = findViewById(R.id.logoutA);
        noOfReq = findViewById(R.id.noOfreqA);

        refresh = findViewById(R.id.refreshA);

        lView = findViewById(R.id.lViewA);
        donatedRequests = new ArrayList<>();
    }

    void retrieveDonatedRequests(){
        refresh.setVisibility(View.INVISIBLE);
        donatedRequests.clear();
        DatabaseReference d = FirebaseDatabase.getInstance().getReference("Request/"+FunctionVariable.NID);
        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot i:dataSnapshot.getChildren()){
                    Transaction t = i.getValue(Transaction.class);
                    if(t.getStatus()==1)    donatedRequests.add(t);
                }
                noOfReq.setText(donatedRequests.size()+"");
                CustomApproved c = new CustomApproved(Approved.this, donatedRequests, noOfReq);
                lView.setAdapter(c);
                refresh.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}