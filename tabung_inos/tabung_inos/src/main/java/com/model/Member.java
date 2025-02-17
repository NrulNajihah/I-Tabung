/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

/**
 *
 * @author NAJIHAH
 */
public class Member {
    
    private int mID;
    private int uID;
    private int id;
    private String username;
    private String position;
    
    public Member() {
        
    }

    public Member(int mID, int uID, int id, String position) {
        this.mID = mID;
        this.uID = uID;
        this.id = id;
        this.position = position;
    }
    
     public Member(int mID, String username, int id, String position) {
        this.mID = mID;
        this.username = username;
        this.id = id;
        this.position = position;
    }

    public Member(int uID,  int id, String position) {
        this.uID = uID;
        this.id = id;
        this.position = position;
    }
    
    public Member( String position ,int mID) {
        this.mID = mID;
        this.position = position;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    
}
