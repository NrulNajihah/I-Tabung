/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

/**
 *
 * @author NAJIHAH
 */
public class Lead {
    private int kpID;
    private int uID;
    private String username;

    public Lead() {
        
    }

    public Lead(int kpID, int uID) {
        super();
        this.kpID = kpID;
        this.uID = uID;
    }

    public Lead(int kpID, String username) {
        this.kpID = kpID;
        this.username = username;
    }

    
    public Lead(int uID) {
        super();
        this.uID = uID;
    }
    
  
    public int getKpID() {
        return kpID;
    }

    public void setKpID(int kpID) {
        this.kpID = kpID;
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
    
    
    
}
