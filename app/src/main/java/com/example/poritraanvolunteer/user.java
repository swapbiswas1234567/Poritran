package com.example.poritraanvolunteer;

public class user {
    String name;
    String address;
    String contact;
    String mail;
    String password;
    String dob;
    String nid;

    public user() {

    }

    public user(String name, String address, String contact, String mail, String password, String dob, String nid) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.mail = mail;
        this.password = password;
        this.dob = dob;
        this.nid = nid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getDob() {
        return dob;
    }

    public String getNid() {
        return nid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }
}