package com.example.poritraanvolunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class MyWork extends AppCompatActivity {

    Button home, add, pending, up, approved, logout;
    TextView noOfReq, refresh;
    ListView lView;
    ArrayList<Transaction> completedRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);

        init();
        retrieveCompletedRequests();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveCompletedRequests();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWork.this, DonorActivity.class);
                startActivity(intent);
                customType(MyWork.this, "right-to-left");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWork.this, AddRequest.class);
                startActivity(intent);
                customType(MyWork.this, "right-to-left");
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWork.this, WaitingForApproval.class);
                startActivity(intent);
                customType(MyWork.this, "right-to-left");
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWork.this, SubmitPhoto.class);
                startActivity(intent);
                customType(MyWork.this, "right-to-left");
            }
        });

        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWork.this, Approved.class);
                startActivity(intent);
                customType(MyWork.this, "right-to-left");
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void init(){
        home = findViewById(R.id.homeMW);
        add = findViewById(R.id.addMW);
        pending = findViewById(R.id.pendingMW);
        up = findViewById(R.id.uploadMW);
        approved = findViewById(R.id.approvedMW);
        logout = findViewById(R.id.logoutMW);
        noOfReq = findViewById(R.id.noOfreqMW);

        refresh = findViewById(R.id.refreshMW);

        lView = findViewById(R.id.lViewMW);
        completedRequests = new ArrayList<>();
    }

    void retrieveCompletedRequests(){
        refresh.setVisibility(View.INVISIBLE);
        completedRequests.clear();
        DatabaseReference d = FirebaseDatabase.getInstance().getReference("Request/"+FunctionVariable.NID);
        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot i:dataSnapshot.getChildren()){
                    Transaction t = i.getValue(Transaction.class);
                    if(t.getStatus()==3)    completedRequests.add(t);
                }
                noOfReq.setText(completedRequests.size()+"");
                CustomApproved c = new CustomApproved(MyWork.this, completedRequests, noOfReq);
                lView.setAdapter(c);
                refresh.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}