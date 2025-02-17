/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Dell
 */
public class Component {
    private int cID;
    private int tID;
    private String compID;
    private String compName;
    private BigDecimal total_All;
    private Date compDate;
    private String tname;
    
    public Component() {
        
    }

    public Component(int cID, int tID, String compID, String compName, BigDecimal total_All, Date compDate) {
        this.cID = cID;
        this.tID = tID;
        this.compID = compID;
        this.compName = compName;
        this.total_All = total_All;
        this.compDate = compDate;
    }
    public Component( Integer tID, String compID, String compName, BigDecimal total_All, Date compDate) {
        this.tID = tID;
        this.compID = compID;
        this.compName = compName;
        this.total_All = total_All;
        this.compDate = compDate;
    }

    public Component(int cID, String compID, String compName, BigDecimal total_All, Date compDate) {
        this.cID = cID;
        this.compID = compID;
        this.compName = compName;
        this.total_All = total_All;
        this.compDate = compDate;
    }
    
    public Component(int cID, String tname, String compID, String compName, BigDecimal total_All, Date compDate) {
        this.cID = cID;
        this.tname = tname;
        this.compID = compID;
        this.compName = compName;
        this.total_All = total_All;
        this.compDate = compDate;
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public int gettID() {
        return tID;
    }

    public void settID(int tID) {
        this.tID = tID;
    }

    public String getCompID() {
        return compID;
    }

    public void setCompID(String compID) {
        this.compID = compID;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public BigDecimal getTotal_All() {
        return total_All;
    }

    public void setTotal_All(BigDecimal total_All) {
        this.total_All = total_All;
    }

    public Date getCompDate() {
        return compDate;
    }

    public void setCompDate(Date compDate) {
        this.compDate = compDate;
    }



}
    
