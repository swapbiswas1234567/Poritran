package com.example.poritraanvolunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class SubmitPhoto extends AppCompatActivity {

    Button homeButton, addButton, pendingButton, approvedButton, my, logout;
    TextView noOfReq;
    ListView listView;
    TextView refreshTextView;
    CustomAdapter2 customAdapter;
    /*ARRAYLISTS FOR SENDING DATA TO THE */
    ArrayList <Transaction>transationArrayList;
    ArrayList <String> donorNameArrayList;
    ArrayList <String> donorNidArrayList;

    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Log Out For Exit", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_photo);
        init();
        setListenerToButtons();
        this.setListenerToRefreshTextVIew();
        this.refreshTextView.callOnClick();
    }


    private  void setListenerToRefreshTextVIew(){
        refreshTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubmitPhoto.this,"Refreshing",Toast.LENGTH_SHORT).show();

                /*MAKING THE LIST VIEW EMPTY*/
                listView.setAdapter(null);
                /*INITIALIZING THE ADAPTER ARRAYLISTS*/
                transationArrayList=new ArrayList<>();
                donorNameArrayList=new ArrayList<>();
                donorNidArrayList=new ArrayList<>();

                /*DATABASE CODE STARTS*/
                Query query= FirebaseDatabase.getInstance().getReference().child("Request");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Log.d("TAG1","debug0: "+dataSnapshot.getKey());
                        for (DataSnapshot ds1 : dataSnapshot.getChildren()){
                            for(DataSnapshot ds2:ds1.getChildren()){
                                //Log.d("TAG1",ds2.getKey());
                                //Log.d("TAG1",ds2.child("amount").getValue().toString());

                                /*GETTING ALL THE DATA INTO STRING VARIABLES*/
                                String volunteerNid=ds1.getKey();
                                String reqUri=ds2.child("reqUri").getValue().toString();
                                String doneeNid=ds2.child("nid").getValue().toString();
                                String doneeName=ds2.child("name").getValue().toString();
                                String phoneNumber=ds2.child("phoneNo").getValue().toString();
                                String presentAddress=ds2.child("presentAddress").getValue().toString();
                                String familyMembers=ds2.child("familyMember").getValue().toString();
                                String amount=ds2.child("amount").getValue().toString();
                                String comment=ds2.child("comment").getValue().toString();
                                String confirmationUri=ds2.child("confirmationUri").getValue().toString();
                                String shared=ds2.child("shared").getValue().toString();
                                String donatedByName=ds2.child("donatedByName").getValue().toString();
                                String donatedByNid=ds2.child("donatedByNid").getValue().toString();
                                String reqId=ds2.child("reqId").getValue().toString();
                                String status=ds2.child("status").getValue().toString();
                                if(status.equals("2")){
                                    Log.d("TAGA",volunteerNid+","+reqUri+","+doneeNid+","+doneeName+","+phoneNumber
                                            +","+presentAddress+","+familyMembers+","+amount+","+comment+","+confirmationUri+","+shared+","+donatedByName
                                            +","+donatedByNid+","+reqId+","+status);
                                    Transaction transaction=new Transaction(volunteerNid,reqUri,doneeNid,doneeName,phoneNumber,presentAddress
                                            ,familyMembers,amount,comment,reqId);
                                    transationArrayList.add(transaction);
                                    donorNameArrayList.add(donatedByName);
                                    donorNidArrayList.add(donatedByNid);
                                }
                            }
                        }
                        customAdapter=new CustomAdapter2(SubmitPhoto.this,transationArrayList,donorNameArrayList,donorNidArrayList);
                        listView.setAdapter(customAdapter);
                        noOfReq.setText(customAdapter.getCount()+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                /*DATABASE CODE ENDS*/
            }
        });
    }


    private void setListenerToButtons() {
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitPhoto.this, DonorActivity.class);
                startActivity(intent);
                customType(SubmitPhoto.this, "right-to-left");
            }
        });

        pendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitPhoto.this, WaitingForApproval.class);
                startActivity(intent);
                customType(SubmitPhoto.this, "right-to-left");
            }
        });

        approvedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitPhoto.this, Approved.class);
                startActivity(intent);
                customType(SubmitPhoto.this, "right-to-left");
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitPhoto.this, AddRequest.class);
                startActivity(intent);
                customType(SubmitPhoto.this, "right-to-left");
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitPhoto.this, MyWork.class);
                startActivity(intent);
                customType(SubmitPhoto.this, "left-to-right");
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(SubmitPhoto.this);
                alertDialogBuilder.setMessage("Are you sure want to logout?");
                alertDialogBuilder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(SubmitPhoto.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    void init(){
        homeButton = findViewById(R.id.homeSP);
        addButton = findViewById(R.id.addSP);
        pendingButton = findViewById(R.id.pendingSP);
        approvedButton = findViewById(R.id.approvedSP);
        my = findViewById(R.id.mySP);
        logout = findViewById(R.id.logoutSP);
        noOfReq = findViewById(R.id.noOfreqSP);

        listView = findViewById(R.id.lViewSP);


        /*MY CODES ARE HERE*/
        refreshTextView=findViewById(R.id.refreshSP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        customAdapter.onActivityResult(requestCode, resultCode, data);
    }

}