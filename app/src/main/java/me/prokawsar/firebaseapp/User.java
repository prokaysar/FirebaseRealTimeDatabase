package me.prokawsar.firebaseapp;

public class User {
    String id;
    String name;
    String email;
    String phone;
    String gender;
    String age;
    public User() {
    }

    public User(String id, String name, String email, String phone, String gender, String age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Name : "+name+"\n"+"Email : "+email+"\n"+"Phone : "+phone+"\n"+"Gender : "+gender+"\n"+"Age : "+age;
    }
}
