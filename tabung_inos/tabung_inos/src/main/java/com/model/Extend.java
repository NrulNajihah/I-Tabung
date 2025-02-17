/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import java.sql.Date;

/**
 *
 * @author NAJIHAH
 */
public class Extend {
    private int exID;
    private int id;
    private Date endP;
    
    public Extend() {
        
    }

    public Extend(int exID, int id, Date endP) {
        this.exID = exID;
        this.id = id;
        this.endP = endP;
    }

    public Extend(int exID, Date endP) {
        this.exID = exID;
        this.endP = endP;
    }
    
    public Extend( Date endP, int id) {
        this.id = id;
        this.endP = endP;
    }

    public int getExID() {
        return exID;
    }

    public void setExID(int exID) {
        this.exID = exID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getEndP() {
        return endP;
    }

    public void setEndP(Date endP) {
        this.endP = endP;
    }
    
    
}
