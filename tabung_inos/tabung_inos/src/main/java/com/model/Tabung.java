/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author NAJIHAH
 */
public class Tabung {
    private int tID;
    private int uID;
    private int noTb;
    private String tname;
    private Date tdate;
    private BigDecimal amount;
    private BigDecimal balance;
    
    public Tabung() {
        
    }

    public Tabung(int tID, int uID, int noTb, String tname, Date tdate, BigDecimal amount, BigDecimal balance) {
        super();
        this.tID = tID;
        this.uID = uID;
        this.noTb =noTb;
        this.tname = tname;
        this.tdate = tdate;
        this.amount = amount;
        this.balance = balance;
    }

    public Tabung(int uID,int noTb, String tname, Date tdate, BigDecimal amount,BigDecimal balance) {
        super();
        this.uID = uID;
        this.noTb = noTb;
        this.tname = tname;
        this.tdate = tdate;
        this.amount = amount;
        this.balance = balance;
    }
    
    public Tabung(int noTb,String tname,int tID, Date tdate, BigDecimal amount,BigDecimal balance) {
        super();
        this.tID = tID;
        this.noTb = noTb;
        this.tname = tname;
        this.tdate = tdate;
        this.amount = amount;
        this.balance = balance;
    }

    public Tabung(int tID, int uID, int noTb, String tname, Date tdate, BigDecimal amount) {
        super();
        this.tID = tID;
        this.uID = uID;
        this.noTb = noTb;
        this.tname = tname;
        this.tdate = tdate;
        this.amount = amount;
    }
    
    

    public int gettID() {
        return tID;
    }

    public void settID(int tID) {
        this.tID = tID;
    }

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }

    public int getNoTb() {
        return noTb;
    }

    public void setNoTb(int noTb) {
        this.noTb = noTb;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Date getTdate() {
        return tdate;
    }

    public void setTdate(Date tdate) {
        this.tdate = tdate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    
    
}
