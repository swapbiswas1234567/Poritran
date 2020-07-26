package com.example.poritraanvolunteer;

public class Transaction {
    String volNid;

    String reqUri, nid, name, phoneNo, presentAddress;
    int familyMember, amount;
    String comment;

    String confirmationUri;
    int shared;

    String donatedByName, donatedByNid;

    String reqId;
    int status;

    public Transaction() {
    }


    public Transaction(String volNid, String reqUri, String nid, String name, String phoneNo, String presentAddress, String familyMember, String amount, String comment, String reqId) {
        this.volNid = volNid;
        this.reqUri = reqUri;
        this.nid = nid;
        this.name = name;
        this.phoneNo = phoneNo;
        this.presentAddress = presentAddress;
        this.familyMember = Integer.parseInt(familyMember);
        this.amount = Integer.parseInt(amount);
        this.comment = comment;
        this.confirmationUri = "";
        this.shared = 0;
        this.donatedByName = "";
        this.donatedByNid = "";
        this.reqId = reqId;
        status = 0;
    }

    public String getVolNid() {
        return volNid;
    }

    public void setVolNid(String volNid) {
        this.volNid = volNid;
    }

    public String getReqUri() {
        return reqUri;
    }

    public void setReqUri(String reqUri) {
        this.reqUri = reqUri;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public int getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(int familyMember) {
        this.familyMember = familyMember;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getConfirmationUri() {
        return confirmationUri;
    }

    public void setConfirmationUri(String confirmationUri) {
        this.confirmationUri = confirmationUri;
    }

    public int getShared() {
        return shared;
    }

    public void setShared(int shared) {
        this.shared = shared;
    }

    public String getDonatedByName() {
        return donatedByName;
    }

    public void setDonatedByName(String donatedByName) {
        this.donatedByName = donatedByName;
    }

    public String getDonatedByNid() {
        return donatedByNid;
    }

    public void setDonatedByNid(String donatedByNid) {
        this.donatedByNid = donatedByNid;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
