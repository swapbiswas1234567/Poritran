package com.example.poritraanvolunteer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> amountArrayList, volunteerArrayList, keyArrayList, nameArrayList, familyMemberArrayList, phoneNumberArrayList, presentAddressArrayList, commentArrayList,reqUriArrayList;

    LayoutInflater layoutInflater;
    int buttonType;
    String userName, userNid;

    ImageView doneeimageView;

    public CustomAdapter(Context context, ArrayList amountArrayList, ArrayList volunteerArrayList, ArrayList keyArrayList, ArrayList nameArrayList, ArrayList familyMemberArrayList, ArrayList phoneNumberArrayList, ArrayList presentAddressArrayList, ArrayList commentArrayList, int buttonType, String userName, String userNid,ArrayList reqUriArrayList) {
        this.context = context;
        this.amountArrayList = amountArrayList;
        this.volunteerArrayList = volunteerArrayList;
        this.keyArrayList = keyArrayList;
        this.nameArrayList = nameArrayList;
        this.familyMemberArrayList = familyMemberArrayList;
        this.phoneNumberArrayList = phoneNumberArrayList;
        this.presentAddressArrayList = presentAddressArrayList;
        this.commentArrayList = commentArrayList;
        this.buttonType = buttonType;
        this.userName = userName;
        this.userNid = userNid;
        this.reqUriArrayList=reqUriArrayList;
    }

    @Override
    public int getCount() {
        return nameArrayList.size();
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
        templateView = layoutInflater.inflate(R.layout.list_view_row_template, viewGroup, false);

        TextView volunteerTextView = templateView.findViewById(R.id.volunteerTextView);
        volunteerTextView.setText("Volunteer:" + volunteerArrayList.get(position));
        TextView keyTextView = templateView.findViewById(R.id.requestIdTextView);
        keyTextView.setText("key:" + keyArrayList.get(position));
        TextView nameTextView = templateView.findViewById(R.id.nameTextView);
        nameTextView.setText(nameArrayList.get(position));
        TextView familyMembersTextView = templateView.findViewById(R.id.familyMembersTextView);
        familyMembersTextView.setText("Family Members:" + familyMemberArrayList.get(position));
        TextView phoneNumberTextView = templateView.findViewById(R.id.phoneNoTextView);
        phoneNumberTextView.setText("Contact:" + phoneNumberArrayList.get(position));
        TextView addressTextView = templateView.findViewById(R.id.presentAddressTextView);
        addressTextView.setText("Present Address:\n" + presentAddressArrayList.get(position));
        TextView commentTextView = templateView.findViewById(R.id.commentTextView);
        commentTextView.setText("Comment:" + commentArrayList.get(position));
        TextView amountTextView = templateView.findViewById(R.id.amountTextView);
        amountTextView.setText("Tk " + amountArrayList.get(position));
        Button donateButton = templateView.findViewById(R.id.donateButton);
        if (buttonType == 1) {
            donateButton.setVisibility(View.VISIBLE);
            donateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String volunteerName = volunteerArrayList.get(position);
                    final String key = keyArrayList.get(position);
                    String name = nameArrayList.get(position);
                    String familyMembers = familyMemberArrayList.get(position);
                    String phoneNumber = phoneNumberArrayList.get(position);
                    String presentAddress = presentAddressArrayList.get(position);
                    String comment = commentArrayList.get(position);
                    String amount = amountArrayList.get(position);


                    /*String[] donationInfoArray = new String[]{volunteerName, key, name, familyMembers, phoneNumber
                            , presentAddress, comment, amount, userName, userNid, buttonType + ""};
                    Intent intent=new Intent(context,DonationManagementActivity.class);
                    intent.putExtra("donationInfo",donationInfoArray);
                    context.startActivity(intent);*/

                    AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Are you sure you want to donate Tk"+amount+"?");
                    alertDialogBuilder.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                                    DatabaseReference myReference=firebaseDatabase.getReference
                                            ("Request/"+volunteerName+"/"+key);


                                    myReference.child("donatedByName").setValue(userName);
                                    myReference.child("donatedByNid").setValue(userNid);
                                    myReference.child("status").setValue(1);

                                    Intent intent=new Intent(context,context.getClass());
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                    context.startActivity(intent);
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            });
        } else if (buttonType == 2) {
            donateButton.setVisibility(View.VISIBLE);
            donateButton.setText("Cancel");
            donateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String volunteerName = volunteerArrayList.get(position);
                    final String key = keyArrayList.get(position);
                    String name = nameArrayList.get(position);
                    String familyMembers = familyMemberArrayList.get(position);
                    String phoneNumber = phoneNumberArrayList.get(position);
                    String presentAddress = presentAddressArrayList.get(position);
                    final String comment = commentArrayList.get(position);
                    String amount = amountArrayList.get(position);


                    /*String[] donationInfoArray = new String[]{volunteerName, key, name, familyMembers, phoneNumber
                            , presentAddress, comment, amount, userName, userNid, buttonType + ""};
                    Intent intent=new Intent(context,DonationManagementActivity.class);
                    intent.putExtra("donationInfo",donationInfoArray);
                    context.startActivity(intent);*/
                    AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Are you sure you want to cancel this donaition of Tk"+amount+"?");
                    alertDialogBuilder.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                                    DatabaseReference myReference=firebaseDatabase.getReference
                                            ("Request/"+volunteerName+"/"+key);


                                    myReference.child("donatedByName").setValue("");
                                    myReference.child("donatedByNid").setValue("");
                                    myReference.child("status").setValue(0);
                                    Intent intent=new Intent(context,context.getClass());
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                    context.startActivity(intent);
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            });
        }

        /*SETTING THE IMAGE*/
        doneeimageView=templateView.findViewById(R.id.doneeImageView);
        String link=reqUriArrayList.get(position);
        Log.d("TAG2","link="+link);
        Picasso.with(context).load(link).fit().into(doneeimageView);


        return templateView;
    }
}