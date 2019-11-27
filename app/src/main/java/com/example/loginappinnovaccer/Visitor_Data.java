package com.example.loginappinnovaccer;

public class Visitor_Data
{
    String name,number,email,timestamp;
    public Visitor_Data()
    {

    }

    public Visitor_Data(String name, String number, String email,String timestamp) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.timestamp=timestamp;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
