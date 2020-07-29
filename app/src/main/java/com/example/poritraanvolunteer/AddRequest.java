package com.example.poritraanvolunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static maes.tech.intentanim.CustomIntent.customType;


public class AddRequest extends AppCompatActivity {

    TextView post,available;
    Button home, up, pending, approved, my, logout;

    private DatabaseReference mDataBaseRef;
    private ProgressDialog progressDialog;
    private EditText nid, name, phoneno, presentaddress,familymember, amount, comment;
    private LinearLayout viewHolder;

    long MAX_REQ_COUNT, MAX_REQ_AMOUNT, PENDING_COUNT=0;

    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Log Out For Exit", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);

        init();
        setLimits();

        /*ADDING LISTENER TO UPLOAD(up) BUTTON STARTS*/
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddRequest.this,SubmitPhoto.class);
                startActivity(intent);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!FunctionVariable.isConnected(AddRequest.this)){
                    toast("You must be connected with internet");
                    return;
                }
                String s = available.getText().toString().trim();
                if(s.equals("")){
                    toast("Loading! Please Wait...");
                    return;
                }
                int availableReq = Integer.parseInt(s);
                if(availableReq==0){
                    toast("You have posted maximum number of request");
                    return;
                }
                if(validation()){
                    Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                    postRequest();
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRequest.this, DonorActivity.class);
                startActivity(intent);
                customType(AddRequest.this, "right-to-left");
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Feature Coming Soon", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddRequest.this, WaitingForApproval.class);
                startActivity(intent);
                customType(AddRequest.this, "left-to-right");
            }
        });

        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Feature Coming Soon", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(AddRequest.this, Approved.class);
                startActivity(intent);
                customType(AddRequest.this, "left-to-right");*/
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Feature Coming Soon", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(AddRequest.this, SubmitPhoto.class);
                startActivity(intent);
                customType(AddRequest.this, "left-to-right");*/
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Feature Coming Soon", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(AddRequest.this, MyWork.class);
                startActivity(intent);
                customType(AddRequest.this, "left-to-right");*/
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(AddRequest.this);
                alertDialogBuilder.setMessage("Are you sure want to logout?");
                alertDialogBuilder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FunctionVariable.NID = "NULL";
                                Intent intent = new Intent(AddRequest.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void init(){
        name = findViewById(R.id.nameEdt);
        phoneno = findViewById(R.id.phoneEdt);
        presentaddress = findViewById(R.id.addressEdt);
        familymember = findViewById(R.id.memberEdt);
        amount = findViewById(R.id.amountEdt);
        comment = findViewById(R.id.commentEdt);
        viewHolder = findViewById(R.id.linearAddReq);

        post = findViewById(R.id.postAddReq);
        available = findViewById(R.id.availableAddReq);

        home = findViewById(R.id.homeAR);
        pending = findViewById(R.id.pendingAR);
        approved = findViewById(R.id.approvedAR);
        my = findViewById(R.id.myAR);
        logout = findViewById(R.id.logoutAR);
        up = findViewById(R.id.uploadAR);

        progressDialog = new ProgressDialog(AddRequest.this);

        viewHolder.setVisibility(View.INVISIBLE);
        available.setText("");
    }



    private void uploadRequest(){
            mDataBaseRef = FirebaseDatabase.getInstance().getReference("Request/" + FunctionVariable.NID);
            final String VOL_NID = FunctionVariable.NID;
            final String NAME = name.getText().toString().trim();
            final String PHONENO = phoneno.getText().toString().trim();
            final String ADDRESS = presentaddress.getText().toString().trim();
            final String MEMBER = familymember.getText().toString().trim();
            final String AMOUNT = amount.getText().toString().trim();
            final String COMMENT = comment.getText().toString().trim();
            final String key = mDataBaseRef.push().getKey();

            Transaction t = new Transaction(VOL_NID,"","",NAME,PHONENO,ADDRESS,MEMBER,AMOUNT,COMMENT,key);
            mDataBaseRef.child(key).setValue(t);
            toast("Your request has been posted for donation");
            Intent intent = new Intent(AddRequest.this, WaitingForApproval.class);
            startActivity(intent);
            customType(AddRequest.this, "left-to-right");

    }

    private void setLimits(){
        DatabaseReference dAmount = FirebaseDatabase.getInstance().getReference("Limits/maxamount");
        final DatabaseReference dReq = FirebaseDatabase.getInstance().getReference("Limits/requestcount");
        dAmount.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MAX_REQ_AMOUNT = dataSnapshot.getValue(Integer.class);
                dReq.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        MAX_REQ_COUNT = dataSnapshot.getValue(Integer.class);
                        setTotalPendingRequest();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void setTotalAvailableRequest(){
        available.setText((MAX_REQ_COUNT - PENDING_COUNT) + "");
        if(MAX_REQ_COUNT - PENDING_COUNT != 0){
            viewHolder.setVisibility(View.VISIBLE);
        }
    }

    void setTotalPendingRequest(){
        DatabaseReference d = FirebaseDatabase.getInstance().getReference("Request/" + FunctionVariable.NID);
        PENDING_COUNT = 0;
        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot i: dataSnapshot.getChildren()){
                    Transaction t = i.getValue(Transaction.class);
                    if(t.getStatus()==0){
                        PENDING_COUNT++;
                    }
                }
                setTotalAvailableRequest();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    boolean validation(){
        String NAME = name.getText().toString().trim();
        String PHONENO = phoneno.getText().toString().trim();
        String ADDRESS = presentaddress.getText().toString().trim();
        String MEMBER = familymember.getText().toString().trim();
        String AMOUNT = amount.getText().toString().trim();

        int max = Integer.MAX_VALUE;
        long MAX = (long)max;

        if(NAME.equals("")){
            name.setError("Requestor's Name Must Be Filled Up!");
            return false;
        }

        if(PHONENO.equals("")){
            phoneno.setError("Requestor's Phone No Must Be Filled Up!");
            return false;
        }

        if(ADDRESS.equals("")){
            presentaddress.setError("Requestor's Address Must Be Filled Up!");
            return false;
        }

        long FAM = 0;
        try{
            FAM = Long.parseLong(MEMBER);
        }catch (Exception e){
            amount.setError("Number of Family Member Must Be A Number (Maximum: 20");
            return false;
        }
        if(FAM > 20){
            familymember.setError("Number of Family Member Can't Be Greater Than 20");
            return false;
        }

        long AMT = 0;
        try{
            AMT = Long.parseLong(AMOUNT);
        }catch (Exception e){
            amount.setError("Requested Ammount Must Be Number (Maximum: " + MAX_REQ_AMOUNT + ")");
            return false;
        }

        if(AMT > MAX_REQ_AMOUNT){
            amount.setError("Requested Amount Can't Be Greater Than " + MAX_REQ_AMOUNT);
            return false;
        }
        return true;
    }

    private void postRequest(){
        String NID = FunctionVariable.NID;
        mDataBaseRef = FirebaseDatabase.getInstance().getReference("Request/" + NID);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddRequest.this);
        builder1.setMessage("Are you sure want to post the request?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        uploadRequest();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
