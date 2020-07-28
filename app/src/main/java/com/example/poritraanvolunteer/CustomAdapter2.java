package com.example.poritraanvolunteer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static maes.tech.intentanim.CustomIntent.customType;

public class CustomAdapter2 extends BaseAdapter {

    public ProgressDialog progressDialog;

    public int currentPosition;
    public ImageView currentImageView;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList <Transaction> transactionArrayList;
    ArrayList<String> donorNameArrayList,donorNidArrayList;
    public CustomAdapter2(Context context,ArrayList transactionArrayList,ArrayList donorNameArrayList,ArrayList donorNidArrayList) {
        this.transactionArrayList=transactionArrayList;
        this.donorNameArrayList=donorNameArrayList;
        this.donorNidArrayList=donorNidArrayList;
        this.context=context;
        //Log.d("TAGA", String.valueOf(transactionArrayList));
    }

    @Override
    public int getCount() {
        return donorNameArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View templateView, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        templateView = layoutInflater.inflate(R.layout.list_view_row_template_for_submit_photo_activity, viewGroup, false);
        /*SETTING UP THE TEXT VIEWS*/
        TextView doneeNameTextView=templateView.findViewById(R.id.doneeNameTextViewSP);
        doneeNameTextView.setText(transactionArrayList.get(position).name);

        TextView familyMembersTextView=templateView.findViewById(R.id.familyMembersTextViewSP);
        familyMembersTextView.setText(transactionArrayList.get(position).familyMember+"");
        TextView presentAddressTextView=templateView.findViewById(R.id.presentAddressTextViewSP);
        presentAddressTextView.setText(transactionArrayList.get(position).presentAddress);

        TextView phoneNumberTextView=templateView.findViewById(R.id.phoneNoTextViewSP);
        phoneNumberTextView.setText(transactionArrayList.get(position).phoneNo);

        TextView amountTextView=templateView.findViewById(R.id.amountTextViewSP);
        amountTextView.setText(transactionArrayList.get(position).amount+"");

        TextView donorNameTextView=templateView.findViewById(R.id.donatedByNameTextViewSP);
        donorNameTextView.setText(donorNameArrayList.get(position));

        TextView donorNidTextView=templateView.findViewById(R.id.donatedByNidTextViewSP);
        donorNidTextView.setText(donorNidArrayList.get(position));

        String downloadUrl=transactionArrayList.get(position).reqUri;
        final ImageView imageView1=templateView.findViewById(R.id.imageView1SP);
        Picasso.with(context).load(downloadUrl).fit().into(imageView1);

        final ImageView imageView2=templateView.findViewById(R.id.imageView2SP);

        final String reqId=transactionArrayList.get(position).reqId;


        Button selectPictureButton=templateView.findViewById(R.id.selectPictureButtonSP);
        Button uploadPictureButton=templateView.findViewById(R.id.uploadPictureButtonSP);
        Button rotatePictureButton=templateView.findViewById(R.id.rotatePictureSP);

        selectPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*THE CODE TO GET IMAGE FROM THE GALLERY */

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                currentImageView=imageView2;
                currentPosition=position;
                ((Activity)context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);



                /*REST OF THE CODES FOR PLACING GALLERY  IMAGE ON IMAGEVIEW  IS IN onActivityResult() FUNCTION*/
            }
        });


        rotatePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*THIS IS THE CODE FOR ROTATING THE IMAGE ON THE IMAGE VIEW*/
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap bitmapOrg = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmapOrg, 200, 200, true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                imageView2.setImageBitmap(rotatedBitmap);
            }
        });


        uploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*THE CODE HERE UPLODAS THE IMAGE TO THE FIREBASE STORAGE*/

                /*Converting the Image from image view into a byte array*/
                imageView2.setDrawingCacheEnabled(true);
                imageView2.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 65, baos);
                byte[] data = baos.toByteArray();

                /*CODE FOR PUTTING THE IMAGE INTO THE STORAGE Under Images Folder with name myImage.jpg(Extension is importatnt)*/
                progressDialog = new ProgressDialog(context);
                final StorageReference storageRef = FirebaseStorage.getInstance().getReference("ImagesTest/"+FunctionVariable.NID+"/"+reqId+".jpg");
                storageRef.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("TAGA",taskSnapshot.getMetadata().getPath());
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String reqUri = uri.toString();
                                //Log.d("TAG2","reqUri="+reqUri);
                                Log.d("TAGA","Download Uri is :");
                                Log.d("TAGA",reqUri);
                                progressDialog.dismiss();
                                Toast.makeText(context,"Successfully uploaded information",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Log.d("TAGA","NO DOWNLOAD URI :");
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context,"Successfully uploaded information",Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        progressDialog.setMessage("Your Request Is In Queue");
                        progressDialog.setTitle("Please Wait");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        Log.d("TAG1","SUCCESS");
                    }
                });
            }
        });

        return templateView;
    }

    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*THE CODE HERE SIMPLY PLACES THE CHOSEN IMAGE ON THE IMAGEVIEW*/
        if (requestCode == 1 && resultCode==RESULT_OK) {
            try {
                /*Get the Uniform Resource Identifier of the image*/
                final Uri imageUri = data.getData();
                /*Create an input stream from the image uri*/
                final InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
                /*Get the data from the input stream into the butmap*/
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                /*Resize the image and place it on another bitmap*/
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                        selectedImage, 200, 200, false);//RESIZED TO 200 by 200
                /*Place the image on imageview*/
                currentImageView.setImageBitmap(resizedBitmap);

                /*uploadImageButton.setEnabled(true);
                rotateImageButton.setEnabled(true);*/
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else{
            Log.d("TAG1","CHOSE NOTHING");
        }
    }

}
