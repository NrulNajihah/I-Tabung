/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

/**
 *
 * @author NAJIHAH
 */
public class User {
   private int uID;
   private String username;
   private String email;
   private String password;
   private String phone;
   private String role;

    public User(String username, String email, String password, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
    
     public User(String username, String email, String password, String phone, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role=role;
    }

    public User(int uID, String role) {
        this.uID = uID;
        this.role = role;
    }
    
    public User( String password, int uID) {
        this.uID = uID;
        this.password = password;
    }
   
   public User() {
       
   }

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
   
    
   
}
