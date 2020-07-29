package com.example.poritraanvolunteer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomHomePage extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<Transaction> tempList;

    public CustomHomePage(Context context, List<Transaction> tempList){
        this.context = context;
        this.tempList = tempList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tempList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final int i = position;
        if(view==null){
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.homepage_listview,parent,false);
        }

        final TextView nameTxt = view.findViewById(R.id.nameHPL);
        final TextView phoneTxt = view.findViewById(R.id.phoneHPL);
        final TextView idTxt = view.findViewById(R.id.idHPL);
        final TextView addressTxt = view.findViewById(R.id.addressHPL);
        final TextView memberTxt = view.findViewById(R.id.memberHPL);
        final TextView amountTxt = view.findViewById(R.id.amountHPL);
        final TextView commentTxt = view.findViewById(R.id.commentHPL);
        final TextView volNameTxt = view.findViewById(R.id.volunteerNameHPL);
        final TextView volContactTxt = view.findViewById(R.id.volunteerContactHPL);
        final TextView volEmailTxt = view.findViewById(R.id.volunteerMailHPL);

        final Button donateBtn = view.findViewById(R.id.donateHPL);

        Transaction t = tempList.get(i);
        final Transaction tCopy = t;
        final String reqId = t.getReqId();
        final String donorNid = t.getDonatedByNid();

        nameTxt.setText(t.getName());
        phoneTxt.setText(t.getPhoneNo());
        idTxt.setText(t.getReqId());
        addressTxt.setText(t.getPresentAddress());
        memberTxt.setText(t.getFamilyMember() + "");
        amountTxt.setText(t.getAmount() + "");
        commentTxt.setText(t.getComment());

        DatabaseReference d= FirebaseDatabase.getInstance().getReference("user/"+t.getVolNid());
        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user u = dataSnapshot.getValue(user.class);
                volNameTxt.setText(u.getName());
                volEmailTxt.setText(u.getMail());
                volContactTxt.setText(u.getContact());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Feature Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}
