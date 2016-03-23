package edu.utdallas.myapplication;

import java.io.Serializable;

/**
 * Person class, contain main information entries
 * Created by Peiyang on 3/20/16.
 */
public class Person implements Serializable{
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private int addDay;
    private int addMonth;
    private int addYear;

    /* Constructor, two
     * Author: Peiyang Shangguan
     * Created on: 03/20/2016
     */
    public Person() {

    }
    public Person(String firstName, String lastName, String phone,String email,int year,int month,int day) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.addYear = year;
        this.addMonth = month;
        this.addDay = day;
    }

    /* Getters and setters
     * Author: Peiyang Shangguan
     * Created on: 03/20/2016
     */
    public int getAddMonth() {
        return addMonth;
    }

    public void setAddMonth(int addMonth) {
        this.addMonth = addMonth;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAddDay() {
        return addDay;
    }

    public void setAddDay(int addDay) {
        this.addDay = addDay;
    }

    public int getAddYear() {
        return addYear;
    }

    public void setAddYear(int addYear) {
        this.addYear = addYear;
    }

}
