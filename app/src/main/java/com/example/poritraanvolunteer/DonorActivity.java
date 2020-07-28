package com.example.poritraanvolunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DonorActivity extends AppCompatActivity {
    public String userName="Shadman Aadeeb";
    public String userNID="13579";

    ListView listView;
    CustomAdapter customAdapter;
    ProgressBar progressBar;
    Button availableButton,pendingButton,clearedButton;
    /*The arraylists for the adapters*/
    ArrayList<String> amountArrayList,volunteerArrayList,keyArrayList,nameArrayList,familyMemberArrayList,phoneNumberArrayList,presentAddressArrayList,commentArrayList,reqUriArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);
        listView=findViewById(R.id.listView);
        progressBar=findViewById(R.id.progressBar);
        this.setUpTheButtons();
        this.initializeTheArrayLists();




    }

    @Override
    protected void onResume() {
        super.onResume();
        availableButton.callOnClick();
    }

    void initializeTheArrayLists(){
        this.amountArrayList=new ArrayList<String>();
        this.keyArrayList=new ArrayList<String>();
        this.volunteerArrayList=new ArrayList<String>();
        this.nameArrayList=new ArrayList<String>();
        this.familyMemberArrayList=new ArrayList<String>();
        this.phoneNumberArrayList=new ArrayList<String>();
        this.presentAddressArrayList=new ArrayList<String>();
        this.commentArrayList=new ArrayList<String>();
    }


    void setUpTheButtons(){
        availableButton=findViewById(R.id.availablDonationsButton);
        pendingButton=findViewById(R.id.pendingDonationsButton);
        clearedButton=findViewById(R.id.clearedDonationsButton);
        //SETTING UP THE AVAILABLE BUTTON
        availableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*MAKING THE LIST VIEW EMPTY*/
                listView.setAdapter(null);
                progressBar.setVisibility(View.VISIBLE);
                /*MAKING THE ARRAYLISTS EMPLTY*/
                amountArrayList=new ArrayList<String>();
                keyArrayList=new ArrayList<String>();
                volunteerArrayList=new ArrayList<String>();
                nameArrayList=new ArrayList<String>();
                familyMemberArrayList=new ArrayList<String>();
                phoneNumberArrayList=new ArrayList<String>();
                presentAddressArrayList=new ArrayList<String>();
                commentArrayList=new ArrayList<String>();
                reqUriArrayList=new ArrayList<>();
                /*ARRANGING THE BUTTON COLORS*/
                availableButton.setBackgroundColor(Color.parseColor("#3E4EA2"));
                pendingButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                clearedButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
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
                                String volunteerName=ds1.getKey();
                                String doneeName=ds2.child("name").getValue().toString();
                                String amount=ds2.child("amount").getValue().toString();
                                String familyMembers=ds2.child("familyMember").getValue().toString();
                                String phoneNumber=ds2.child("phoneNo").getValue().toString();
                                String presentAddress=ds2.child("presentAddress").getValue().toString();
                                String comment=ds2.child("comment").getValue().toString();
                                String status=ds2.child("status").getValue().toString();
                                String reqUri=ds2.child("reqUri").getValue().toString();
                                if(status.equals("0")){
                                    volunteerArrayList.add(volunteerName);
                                    keyArrayList.add(ds2.getKey());
                                    amountArrayList.add(amount);
                                    nameArrayList.add(doneeName);
                                    familyMemberArrayList.add(familyMembers);
                                    phoneNumberArrayList.add(phoneNumber);
                                    presentAddressArrayList.add(presentAddress);
                                    commentArrayList.add(comment);
                                    reqUriArrayList.add(reqUri);
                                }


                            }
                        }
                        Log.d("TAG1","Req Uri:"+reqUriArrayList);
                        CustomAdapter customAdapter=new CustomAdapter(DonorActivity.this,amountArrayList,
                                volunteerArrayList,keyArrayList,
                                nameArrayList,familyMemberArrayList,phoneNumberArrayList,
                                presentAddressArrayList,commentArrayList,1,userName,userNID,reqUriArrayList);
                        progressBar.setVisibility(View.GONE);
                        listView.setAdapter(customAdapter);
                        /*DATABASE CODE ENDS*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        pendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(null);
                progressBar.setVisibility(View.VISIBLE);
                /*MAKING THE ARRAYLISTS EMPLTY*/
                amountArrayList=new ArrayList<String>();
                keyArrayList=new ArrayList<String>();
                volunteerArrayList=new ArrayList<String>();
                nameArrayList=new ArrayList<String>();
                familyMemberArrayList=new ArrayList<String>();
                phoneNumberArrayList=new ArrayList<String>();
                presentAddressArrayList=new ArrayList<String>();
                commentArrayList=new ArrayList<String>();
                reqUriArrayList=new ArrayList<>();
                /*ARRANGING THE BUTTON COLORS*/
                availableButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                pendingButton.setBackgroundColor(Color.parseColor("#3E4EA2"));
                clearedButton.setBackgroundColor(Color.parseColor("#FFFFFF"));

                Query query= FirebaseDatabase.getInstance().getReference().child("Request");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Log.d("TAG1","debug0: "+dataSnapshot.getKey());
                        for (DataSnapshot ds1 : dataSnapshot.getChildren()){
                            for(DataSnapshot ds2:ds1.getChildren()){
                                //Log.d("TAG1",ds2.getKey());
                                //Log.d("TAG1",ds2.child("amount").getValue().toString());
                                String volunteerName=ds1.getKey();
                                String doneeName=ds2.child("name").getValue().toString();
                                String amount=ds2.child("amount").getValue().toString();
                                String familyMembers=ds2.child("familyMember").getValue().toString();
                                String phoneNumber=ds2.child("phoneNo").getValue().toString();
                                String presentAddress=ds2.child("presentAddress").getValue().toString();
                                String comment=ds2.child("comment").getValue().toString();
                                String status=ds2.child("status").getValue().toString();
                                String reqUri=ds2.child("reqUri").getValue().toString();

                                if(status.equals("1") || status.equals("2")){
                                    volunteerArrayList.add(volunteerName);
                                    keyArrayList.add(ds2.getKey());
                                    amountArrayList.add(amount);
                                    nameArrayList.add(doneeName);
                                    familyMemberArrayList.add(familyMembers);
                                    phoneNumberArrayList.add(phoneNumber);
                                    presentAddressArrayList.add(presentAddress);
                                    commentArrayList.add(comment);
                                    reqUriArrayList.add(reqUri);
                                }


                            }
                        }
                        Log.d("TAG1", String.valueOf(volunteerArrayList));
                        Log.d("TAG1", String.valueOf(keyArrayList));
                        Log.d("TAG1", String.valueOf(nameArrayList));
                        Log.d("TAG1", String.valueOf(amountArrayList));
                        Log.d("TAG1", String.valueOf(phoneNumberArrayList));
                        Log.d("TAG1", String.valueOf(presentAddressArrayList));
                        Log.d("TAG1", String.valueOf(familyMemberArrayList));
                        Log.d("TAG1", String.valueOf(commentArrayList));
                        CustomAdapter customAdapter=new CustomAdapter(DonorActivity.this,amountArrayList,
                                volunteerArrayList,keyArrayList,
                                nameArrayList,familyMemberArrayList,phoneNumberArrayList,
                                presentAddressArrayList,commentArrayList,2,userName,userNID,reqUriArrayList);
                        progressBar.setVisibility(View.GONE);
                        listView.setAdapter(customAdapter);
                        /*DATABASE CODE ENDS*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        clearedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(null);
                progressBar.setVisibility(View.VISIBLE);
                /*MAKING THE ARRAYLISTS EMPLTY*/
                amountArrayList=new ArrayList<String>();
                keyArrayList=new ArrayList<String>();
                volunteerArrayList=new ArrayList<String>();
                nameArrayList=new ArrayList<String>();
                familyMemberArrayList=new ArrayList<String>();
                phoneNumberArrayList=new ArrayList<String>();
                presentAddressArrayList=new ArrayList<String>();
                commentArrayList=new ArrayList<String>();
                reqUriArrayList=new ArrayList<>();
                /*ARRANGING THE BUTTON COLORS*/
                availableButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                pendingButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                clearedButton.setBackgroundColor(Color.parseColor("#3E4EA2"));
                Query query= FirebaseDatabase.getInstance().getReference().child("Request");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Log.d("TAG1","debug0: "+dataSnapshot.getKey());
                        for (DataSnapshot ds1 : dataSnapshot.getChildren()){
                            for(DataSnapshot ds2:ds1.getChildren()){
                                //Log.d("TAG1",ds2.getKey());
                                //Log.d("TAG1",ds2.child("amount").getValue().toString());
                                String volunteerName=ds1.getKey();
                                String doneeName=ds2.child("name").getValue().toString();
                                String amount=ds2.child("amount").getValue().toString();
                                String familyMembers=ds2.child("familyMember").getValue().toString();
                                String phoneNumber=ds2.child("phoneNo").getValue().toString();
                                String presentAddress=ds2.child("presentAddress").getValue().toString();
                                String comment=ds2.child("comment").getValue().toString();
                                String status=ds2.child("status").getValue().toString();
                                String reqUri=ds2.child("reqUri").getValue().toString();
                                if(status.equals("3")){
                                    volunteerArrayList.add(volunteerName);
                                    keyArrayList.add(ds2.getKey());
                                    amountArrayList.add(amount);
                                    nameArrayList.add(doneeName);
                                    familyMemberArrayList.add(familyMembers);
                                    phoneNumberArrayList.add(phoneNumber);
                                    presentAddressArrayList.add(presentAddress);
                                    commentArrayList.add(comment);
                                    reqUriArrayList.add(reqUri);
                                }


                            }
                        }
                        Log.d("TAG1", String.valueOf(volunteerArrayList));
                        Log.d("TAG1", String.valueOf(keyArrayList));
                        Log.d("TAG1", String.valueOf(nameArrayList));
                        Log.d("TAG1", String.valueOf(amountArrayList));
                        Log.d("TAG1", String.valueOf(phoneNumberArrayList));
                        Log.d("TAG1", String.valueOf(presentAddressArrayList));
                        Log.d("TAG1", String.valueOf(familyMemberArrayList));
                        Log.d("TAG1", String.valueOf(commentArrayList));
                        CustomAdapter customAdapter=new CustomAdapter(DonorActivity.this,amountArrayList,
                                volunteerArrayList,keyArrayList,
                                nameArrayList,familyMemberArrayList,phoneNumberArrayList,
                                presentAddressArrayList,commentArrayList,3,userName,userNID,reqUriArrayList);
                        progressBar.setVisibility(View.GONE);
                        listView.setAdapter(customAdapter);
                        /*DATABASE CODE ENDS*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}