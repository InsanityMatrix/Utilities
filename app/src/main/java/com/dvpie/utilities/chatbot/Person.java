package com.dvpie.utilities.chatbot;

import java.util.Calendar;

public class Person {
    private MessageMemory allMessages;
    private String firstName, lastName;
    private String favoriteColor;
    private int age;
    private Calendar Birthday;

    public Person () {

    }
    public Person(String firstName) {
        this.firstName = firstName;
    }
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Calendar getBirthday() {
        return Birthday;
    }

    public void setBirthday(Calendar birthday) {
        Birthday = birthday;
    }


}
