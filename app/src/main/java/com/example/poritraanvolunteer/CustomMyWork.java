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

public class CustomMyWork extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<Transaction> tempList;
    ArrayList<Transaction> mainList;
    TextView noOfReq;

    public CustomMyWork(Context context, List<Transaction> tempList, TextView t){
        this.context = context;
        this.tempList = tempList;
        inflater = LayoutInflater.from(context);
        this.mainList = new ArrayList<>();
        this.mainList.addAll(tempList);
        this.noOfReq = t;
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
            view=inflater.inflate(R.layout.mywork_listview,parent,false);
        }

        final TextView nameTxt = view.findViewById(R.id.nameMWL);
        final TextView phoneTxt = view.findViewById(R.id.phoneMWL);
        final TextView idTxt = view.findViewById(R.id.idMWL);
        final TextView nidTxt = view.findViewById(R.id.nidMWL);
        final TextView addressTxt = view.findViewById(R.id.addressMWL);
        final TextView memberTxt = view.findViewById(R.id.memberMWL);
        final TextView amountTxt = view.findViewById(R.id.amountMWL);
        final TextView commentTxt = view.findViewById(R.id.commentMWL);
        //final Button cancelBtn = view.findViewById(R.id.cancelAL);
        final ImageView photoImg = view.findViewById(R.id.photoMWL);
        final TextView donorNameTxt = view.findViewById(R.id.donorNameMWL);
        final TextView donorAddTxt = view.findViewById(R.id.donorAddressMWL);
        final TextView donorPhoneTxt = view.findViewById(R.id.donorContactMWL);
        final TextView donorEmailTxt = view.findViewById(R.id.donorEmailMWL);

        Transaction t = tempList.get(i);
        final Transaction tCopy = t;
        final String reqId = t.getReqId();
        final String donorNid = t.getDonatedByNid();

        noOfReq.setText(tempList.size() + "");
        nameTxt.setText(t.getName());
        phoneTxt.setText(t.getPhoneNo());
        idTxt.setText(t.getReqId());
        nidTxt.setText(t.getNid());
        addressTxt.setText(t.getPresentAddress());
        memberTxt.setText(t.getFamilyMember() + "");
        amountTxt.setText(t.getAmount() + "");
        commentTxt.setText(t.getComment());
        donorNameTxt.setText(t.getDonatedByName());
        String link=t.getConfirmationUri();
        Picasso.with(context).load(link).fit().into(photoImg);

        DatabaseReference d = FirebaseDatabase.getInstance().getReference("user/"+donorNid);
        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user u = dataSnapshot.getValue(user.class);
                donorAddTxt.setText(u.getAddress());
                donorPhoneTxt.setText(u.getContact());
                donorEmailTxt.setText(u.getMail());
                donorNameTxt.setText(u.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    public void filter(String x){
        String searchText = x.toLowerCase(Locale.getDefault());
        tempList.clear();
        int searchTextLength = searchText.length();

        if(searchTextLength==0){
            tempList.addAll(mainList);
        }

        else{
            for(Transaction t:mainList){
                String name = t.getName().toLowerCase();
                String phone = t.getPhoneNo().toLowerCase();
                String reqId = t.getReqId().toLowerCase();
                String nid = t.getNid().toLowerCase();
                String address = t.getPresentAddress().toLowerCase();

                if(name.contains(searchText) || phone.contains(searchText) || reqId.contains(searchText) || nid.contains(searchText) || address.contains(searchText)){
                    tempList.add(t);
                }
            }
        }

        notifyDataSetChanged();
    }
}
