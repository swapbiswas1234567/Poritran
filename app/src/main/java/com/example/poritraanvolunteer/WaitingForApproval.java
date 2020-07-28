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

public class WaitingForApproval extends AppCompatActivity {

    Button home, add, approved, up, my, logout;
    TextView noOfReq,refresh;
    ListView lView;
    ArrayList<Transaction> pendingRequests;

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Log Out For Exit", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_approval);

        init();
        retrievePendingRequests();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrievePendingRequests();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForApproval.this, DonorActivity.class);
                startActivity(intent);
                customType(WaitingForApproval.this, "right-to-left");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForApproval.this, AddRequest.class);
                startActivity(intent);
                customType(WaitingForApproval.this, "right-to-left");
            }
        });

        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForApproval.this, Approved.class);
                startActivity(intent);
                customType(WaitingForApproval.this, "left-to-right");
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForApproval.this, SubmitPhoto.class);
                startActivity(intent);
                customType(WaitingForApproval.this, "left-to-right");
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForApproval.this, MyWork.class);
                startActivity(intent);
                customType(WaitingForApproval.this, "left-to-right");
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(WaitingForApproval.this);
                alertDialogBuilder.setMessage("Are you sure want to logout?");
                alertDialogBuilder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(WaitingForApproval.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    void init(){
        home = findViewById(R.id.homeWA);
        add = findViewById(R.id.addWA);
        approved = findViewById(R.id.approvedWA);
        my = findViewById(R.id.myWA);
        logout = findViewById(R.id.logoutWA);
        noOfReq = findViewById(R.id.noOfreqWA);
        lView = findViewById(R.id.lViewWA);
        up = findViewById(R.id.uploadWA);
        refresh = findViewById(R.id.refreshWA);

        pendingRequests = new ArrayList<>();
    }

    void retrievePendingRequests(){
        refresh.setVisibility(View.INVISIBLE);
        pendingRequests.clear();
        DatabaseReference d = FirebaseDatabase.getInstance().getReference("Request/"+FunctionVariable.NID);
        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot i:dataSnapshot.getChildren()){
                    Transaction t = i.getValue(Transaction.class);
                    if(t.getStatus()==0)    pendingRequests.add(t);
                }
                noOfReq.setText(pendingRequests.size()+"");
                CustomWaitingApproval c = new CustomWaitingApproval(WaitingForApproval.this, pendingRequests, noOfReq);
                lView.setAdapter(c);
                refresh.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void toast(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
