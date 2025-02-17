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
public class Income {
    private int iID;
    private int id;
    private int vID;
    private String noVote;
    private String vote;
    private BigDecimal total_All;
    private Date idate;
    private BigDecimal total_Exp;
    private BigDecimal balances;
    
    
    
   // Constructor with Integer parameters
    public Income( Integer id, String noVote, BigDecimal total_All, java.sql.Date idate) {
        this.id = id;
        this.noVote=noVote;
        this.total_All = total_All;
        this.idate = idate;
    }
    
    public Income( Integer id, String noVote, BigDecimal total_All) {
        this.id = id;
        this.noVote=noVote;
        this.total_All = total_All;
        
    }

    public Income(int id, int vID, String noVote, String vote) {
        this.id = id;
        this.vID = vID;
        this.noVote = noVote;
        this.vote = vote;
    }

    public Income(int id, int vID, BigDecimal total_All) {
        this.id = id;
        this.vID = vID;
        this.total_All = total_All;
    }

    
    public Income(String noVote, BigDecimal total_Exp, BigDecimal total_All, BigDecimal balances) {
        this.noVote = noVote;
        this.total_Exp = total_Exp;
        this.total_All = total_All;
        this.balances = balances;
    }
    
    
//    public Income(Integer uID, int id, int vID, BigDecimal total_All, java.sql.Date idate) {
//    this.uID = uID;
//    this.id = id;
//    this.vID = vID;
//    this.total_All = total_All;
//    this.idate = idate;
//}


    // Constructor with int parameters
    public Income( int id, int vID, BigDecimal total_All, java.sql.Date idate) {
        this.id = id;
        this.vID = vID;
        this.total_All = total_All;
        this.idate = idate;
    }
    
    
    
    public Income(int iID, int id, int vID, BigDecimal total_All, Date idate) {
        super();
        this.iID = iID;
        this.id = id;
        this.vID = vID;
        this.total_All = total_All;
        this.idate = idate;
    }
    
    public Income(int iID, int id, String noVote, BigDecimal total_All, Date idate) {
    this.iID = iID;
    this.id = id;
    this.noVote = noVote;
    this.total_All = total_All;
    this.idate = idate;
}
    
     public Income(int iID, int id, String noVote, BigDecimal total_All) {
    this.iID = iID;
    this.id = id;
    this.noVote = noVote;
    this.total_All = total_All;
 
}

    

    public Income(int iID, BigDecimal total_All, Date idate) {
        super();
        this.iID = iID;
        this.total_All = total_All;
        this.idate = idate;
    }

   

    public int getiID() {
        return iID;
    }

    public void setiID(int iID) {
        this.iID = iID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getvID() {
        return vID;
    }

    public void setvID(int vID) {
        this.vID = vID;
    }

    public String getNoVote() {
        return noVote;
    }

    public void setNoVote(String noVote) {
        this.noVote = noVote;
    }
    
    

    public BigDecimal getTotal_All() {
        return total_All;
    }

    public void setTotal_All(BigDecimal total_All) {
        this.total_All = total_All;
    }

    public Date getIdate() {
        return idate;
    }

    public void setIdate(Date idate) {
        this.idate = idate;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public BigDecimal getTotal_Exp() {
        return total_Exp;
    }

    public void setTotal_Exp(BigDecimal total_Exp) {
        this.total_Exp = total_Exp;
    }

    public BigDecimal getBalances() {
        return balances;
    }

    public void setBalances(BigDecimal balances) {
        this.balances = balances;
    }

    
}
