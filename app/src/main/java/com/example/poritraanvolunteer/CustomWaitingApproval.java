package com.example.poritraanvolunteer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomWaitingApproval extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<Transaction> tempList;
    ArrayList<Transaction> mainList;
    TextView noOfReq;

    public CustomWaitingApproval(Context context, List<Transaction> tempList, TextView t) {
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
            view=inflater.inflate(R.layout.admin_approval_listview,parent,false);
        }

        final TextView nameTxt = view.findViewById(R.id.nameWA);
        final TextView phoneTxt = view.findViewById(R.id.phoneWA);
        final TextView idTxt = view.findViewById(R.id.idWA);
        final TextView nidTxt = view.findViewById(R.id.nidWA);
        final TextView addressTxt = view.findViewById(R.id.addressWA);
        final TextView memberTxt = view.findViewById(R.id.memberWA);
        final TextView amountTxt = view.findViewById(R.id.amountWA);
        final TextView commentTxt = view.findViewById(R.id.commentWA);
        final Button cancelBtn = view.findViewById(R.id.cancelWA);

        Transaction t = tempList.get(i);
        noOfReq.setText(tempList.size() + "");
        nameTxt.setText(t.getName());
        phoneTxt.setText(t.getPhoneNo());
        idTxt.setText(t.getReqId());
        nidTxt.setText(t.getNid());
        addressTxt.setText(t.getPresentAddress());
        memberTxt.setText(t.getFamilyMember() + "");
        amountTxt.setText(t.getAmount() + "");
        commentTxt.setText(t.getComment());

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
