package com.example.poritraanvolunteer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class AddRequest extends AppCompatActivity {

    TextView post,available;
    ImageView doneePhoto;
    Button upload;
    Button home, up, pending, approved, my, logout;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDataBaseRef;
    private ProgressDialog progressDialog;
    private EditText nid, name, phoneno, presentaddress,familymember, amount, comment;
    private LinearLayout viewHolder;

    long MAX_REQ_COUNT, MAX_REQ_AMOUNT, PENDING_COUNT=0;

    private ArrayList<String> allPendingnid;

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
        /*ADDING LISTENER TO UPLOAD(up) BUTTON ENDS*/


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
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
                Intent intent = new Intent(AddRequest.this, WaitingForApproval.class);
                startActivity(intent);
                customType(AddRequest.this, "left-to-right");
            }
        });

        approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRequest.this, Approved.class);
                startActivity(intent);
                customType(AddRequest.this, "left-to-right");
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRequest.this, SubmitPhoto.class);
                startActivity(intent);
                customType(AddRequest.this, "left-to-right");
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRequest.this, MyWork.class);
                startActivity(intent);
                customType(AddRequest.this, "left-to-right");
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
        nid = findViewById(R.id.nidEdt);
        name = findViewById(R.id.nameEdt);
        phoneno = findViewById(R.id.phoneEdt);
        presentaddress = findViewById(R.id.addressEdt);
        familymember = findViewById(R.id.memberEdt);
        amount = findViewById(R.id.amountEdt);
        comment = findViewById(R.id.commentEdt);
        viewHolder = findViewById(R.id.linearAddReq);

        post = findViewById(R.id.postAddReq);
        available = findViewById(R.id.availableAddReq);
        doneePhoto = findViewById(R.id.doneePhotoAddReq);
        upload = findViewById(R.id.uploadAddReq);

        home = findViewById(R.id.homeAR);
        pending = findViewById(R.id.pendingAR);
        approved = findViewById(R.id.approvedAR);
        my = findViewById(R.id.myAR);
        logout = findViewById(R.id.logoutAR);
        up = findViewById(R.id.uploadAR);

        progressDialog = new ProgressDialog(AddRequest.this);

        viewHolder.setVisibility(View.INVISIBLE);
        available.setText("");

        allPendingnid = new ArrayList<>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!= null && data.getData()!= null){
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(doneePhoto);
        }

        else{
            toast("No Image Selected");
        }
    }

    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    private void uploadRequest(){
        if(mImageUri!=null){
            final String VOL_NID = FunctionVariable.NID;
            final String NID = nid.getText().toString().trim();
            final String NAME = name.getText().toString().trim();
            final String PHONENO = phoneno.getText().toString().trim();
            final String ADDRESS = presentaddress.getText().toString().trim();
            final String MEMBER = familymember.getText().toString().trim();
            final String AMOUNT = amount.getText().toString().trim();
            final String COMMENT = comment.getText().toString().trim();
            final String key = mDataBaseRef.push().getKey();

            final StorageReference fileReference = mStorageRef.child(key + "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                /*public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String reqUri = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    Transaction t = new Transaction(VOL_NID,reqUri,NID,NAME,PHONENO,ADDRESS,MEMBER,AMOUNT,COMMENT,key);
                    mDataBaseRef.child(key).setValue(t);
                    PENDING_COUNT++;
                    setTotalPendingRequest();
                    progressDialog.dismiss();
                    toast("Your request has been posted for Admin's approval");
                }*/
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String reqUri = uri.toString();
                            //Log.d("TAG2","reqUri="+reqUri);
                            Transaction t = new Transaction(VOL_NID,reqUri,NID,NAME,PHONENO,ADDRESS,MEMBER,AMOUNT,COMMENT,key);
                            mDataBaseRef.child(key).setValue(t);
                            progressDialog.dismiss();
                            toast("Your request has been posted for donation");
                            Intent intent = new Intent(AddRequest.this, WaitingForApproval.class);
                            startActivity(intent);
                            customType(AddRequest.this, "left-to-right");

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    toast(e.getMessage());
                    progressDialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.setMessage("Your Request Is In Queue");
                    progressDialog.setTitle("Please Wait");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });
        }
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

        toast(FunctionVariable.NID);
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
                        allPendingnid.add(t.getNid());
                        ///Toast.makeText(getApplicationContext(), PENDING_COUNT + " " + MAX_REQ_COUNT + " " + MAX_REQ_AMOUNT, Toast.LENGTH_SHORT).show();
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

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }





    boolean validation(){
        String NID = nid.getText().toString().trim();
        String NAME = name.getText().toString().trim();
        String PHONENO = phoneno.getText().toString().trim();
        String ADDRESS = presentaddress.getText().toString().trim();
        String MEMBER = familymember.getText().toString().trim();
        String AMOUNT = amount.getText().toString().trim();

        int max = Integer.MAX_VALUE;
        long MAX = (long)max;

        if(mImageUri==null){
            toast("You Must Upload A Photo Of The Requester");
            return false;
        }

        if(NID.equals("")){
            nid.setError("Requestor's NID Number Must Be Filled Up!");
            return false;
        }

        int len = allPendingnid.size();
        for(int i=0; i<len; i++){
            if(allPendingnid.get(i).equals(NID)){
                nid.setError("This NID has already a pending request by you");
                return false;
            }
        }

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
        mStorageRef = FirebaseStorage.getInstance().getReference("Request/" + NID);
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
