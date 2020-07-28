package com.example.poritraanvolunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonationManagementActivity extends AppCompatActivity {

    Button confirmDonationOrCancelButton;
    TextView donationInfoTextView;
    String userName,userNid,buttonType;
    String []donationInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_management);
        Intent intent=getIntent();
        donationInfo= intent.getStringArrayExtra("donationInfo");

        donationInfoTextView=findViewById(R.id.donationInfoTextView);
        donationInfoTextView.setText("");
        donationInfoTextView.append("Volunteer:"+donationInfo[0]);
        donationInfoTextView.append("\n");
        donationInfoTextView.append("Key:"+donationInfo[1]);
        donationInfoTextView.append("\n");
        donationInfoTextView.append("Name:"+donationInfo[2]);
        donationInfoTextView.append("\n");
        donationInfoTextView.append("Family Members:"+donationInfo[3]);
        donationInfoTextView.append("\n");
        donationInfoTextView.append("Mobile No:"+donationInfo[4]);
        donationInfoTextView.append("\n");
        donationInfoTextView.append("Present Address:"+donationInfo[5]);
        donationInfoTextView.append("\n");
        donationInfoTextView.append("Comment:"+donationInfo[6]);
        donationInfoTextView.append("\n");
        donationInfoTextView.append("Amount :"+donationInfo[7]);
        donationInfoTextView.append("\n");
        userName=donationInfo[8];
        userNid=donationInfo[9];
        buttonType=donationInfo[10];

        /*THE BUTTON FUNCTION*/
        if(buttonType.equals("1")){
            confirmDonationButtonSetup();
        }else if(buttonType.equals("2")){
            cancelButtonSetup();
        }


    }

    private void confirmDonationButtonSetup() {
        confirmDonationOrCancelButton =findViewById(R.id.confirmDonationButton);
        confirmDonationOrCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Write a message to the database

                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference myReference=firebaseDatabase.getReference
                        ("Request/"+donationInfo[0]+"/"+donationInfo[1]);


                myReference.child("donatedByName").setValue(userName);
                myReference.child("donatedByNid").setValue(userNid);
                myReference.child("status").setValue(2);

                Intent intent=new Intent(DonationManagementActivity.this,DonorActivity.class);
                startActivity(intent);
            }
        });
    }

    private void cancelButtonSetup( ) {
        confirmDonationOrCancelButton =findViewById(R.id.confirmDonationButton);
        confirmDonationOrCancelButton.setText("Cancel Donation");
        confirmDonationOrCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Write a message to the database

                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference myReference=firebaseDatabase.getReference
                        ("Request/"+donationInfo[0]+"/"+donationInfo[1]);


                myReference.child("donatedByName").setValue("");
                myReference.child("donatedByNid").setValue("");
                myReference.child("status").setValue(1);
                Intent intent=new Intent(DonationManagementActivity.this,DonorActivity.class);
                startActivity(intent);
            }
        });
    }

}