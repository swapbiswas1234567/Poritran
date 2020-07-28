package com.example.poritraanvolunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WaitingForApproval extends AppCompatActivity {

    Button home, add, pending, approved, submit, my, logout;
    TextView noOfReq;
    ArrayList<Transaction> pendingRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_approval);

        init();
        retrievePendingRequests();
    }

    void init(){
        home = findViewById(R.id.homeWA);
        add = findViewById(R.id.addWA);
        pending = findViewById(R.id.pendingWA);
        approved = findViewById(R.id.approvedWA);
        my = findViewById(R.id.myWA);
        logout = findViewById(R.id.logoutWA);
        noOfReq = findViewById(R.id.noOfreqWA);

        pendingRequests = new ArrayList<>();
    }

    void retrievePendingRequests(){
        pendingRequests.clear();
        DatabaseReference d = FirebaseDatabase.getInstance().getReference("Request/"+FunctionVariable.NID);
        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot i:dataSnapshot.getChildren()){
                    Transaction t = i.getValue(Transaction.class);
                    toast(t.getNid());
                    if(t.getStatus()==0)    pendingRequests.add(t);
                }
                noOfReq.setText(pendingRequests.size()+"");
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
