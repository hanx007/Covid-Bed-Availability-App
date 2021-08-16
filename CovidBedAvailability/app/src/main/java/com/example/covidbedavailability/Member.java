package com.example.covidbedavailability;

public class Member {
    String name;
    String age;
    String phone;
    String relation;
    String gender;
    String time;
    String condition;
    String hospital;
    public Member(String name, String age, String phone, String relation, String gender, String time, String condition, String hospital) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.relation = relation;
        this.gender = gender;
        this.time = time;
        this.condition = condition;
        this.hospital = hospital;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }



    public Member() {
    }


}
