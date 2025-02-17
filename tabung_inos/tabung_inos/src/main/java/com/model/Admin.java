/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

/**
 *
 * @author NAJIHAH
 */
public class Admin {
    private int aID;
    private String username;
    private String password;
    private String email;
    private String phone;
    
    public Admin() {
        
    }

    public Admin(int aID, String username, String password) {
        this.aID = aID;
        this.username = username;
        this.password = password;
    }

    public int getaID() {
        return aID;
    }

    public void setaID(int aID) {
        this.aID = aID;
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
    
    
}
