package com.example.owner;

public class owner_order_record_list {
    private String member_u_id;
    private String member_date;
    private String member_u_address;

    public String getMember_u_id() {
        return member_u_id;
    }

    public String getMember_date() {
        return member_date;
    }

    public String getMember_u_address(){return member_u_address;}


    public void setMember_u_id(String member_u_id) {
        this.member_u_id = member_u_id;
    }

    public void setMember_date(String member_date) {
        this.member_date = member_date;
    }

    public void setMember_u_address(String member_u_address){this.member_u_address = member_u_address;}


}

/*
public class Human {
    public String name;
    public String gender;
    public String age;
    public int image;

    public String getName() {
        return name;
    }
    public String getAge(){
        return age;
    }
    public String getGender(){
        return gender;
    }
    public int getImage(){
        return image;
    }

    Human(String name, String gender, String age, int image){
        this.image = image;
        this.name =name;
        this.gender = gender;
        this.age = age;
    }


}*/
