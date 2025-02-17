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
public class Expense {
    private int eID;
    private int cID;
    private int id;
    private String pname;
    private int vID;
    private String noVote;
    private String claim;
    private Date edate;
    private String noPO;
    private BigDecimal total_Exp;
    private byte[] receipt;
    private String receiptFilename;
    
    public Expense () {
        
    }

    public Expense(int eID, String pname,String noVote, String claim, Date edate,String noPO, BigDecimal total_Exp) {
        super();
        this.eID = eID;
        this.pname = pname;
        this.noVote = noVote;
        this.claim = claim;
        this.edate = edate;
        this.noPO=noPO;
        this.total_Exp = total_Exp;
    }

    public Expense(int eID, int id, int vID, String claim, Date edate, String noPO, BigDecimal total_Exp, byte[] receipt, String receiptFilename) {
        this.eID = eID;
        this.id = id;
        this.vID = vID;
        this.claim = claim;
        this.edate = edate;
        this.noPO = noPO;
        this.total_Exp = total_Exp;
        this.receipt = receipt;
        this.receiptFilename = receiptFilename;
    }
    
     public Expense(int eID, int id, String noVote, String claim, Date edate, String noPO, BigDecimal total_Exp) {
         super();
        this.eID = eID;
        this.id = id;
        this.noVote = noVote; // Assign noVote here
        this.claim = claim;
        this.edate = edate;
        this.noPO = noPO;
        this.total_Exp = total_Exp;
    }

    public Expense(int eID,int id, String noVote, String claim, Date edate, String noPO, BigDecimal total_Exp, byte[] receipt, String receiptFilename) {
        this.eID = eID;
        this.id = id;
        this.noVote = noVote;
        this.claim = claim;
        this.edate = edate;
        this.noPO = noPO;
        this.total_Exp = total_Exp;
        this.receipt = receipt;
        this.receiptFilename = receiptFilename;
    }
     
     
     
     public Expense(int eID, int id, int vID, String claim, BigDecimal total_Exp, java.sql.Date edate) {
         super();
        this.eID = eID;
        this.id = id;
        this.vID = vID;
        this.claim = claim;
        this.total_Exp = total_Exp;
        this.edate = edate;
    }
     
     public Expense(int eID, int id, String noVote,  BigDecimal total_Exp) {
         super();
        this.eID = eID;
        this.id = id;
        this.noVote = noVote;
        this.total_Exp = total_Exp;
    }

    public Expense( int id,int vID, String claim, Date edate,String noPO, BigDecimal total_Exp) {
        super();
        this.id = id;
        this.vID = vID;
        this.claim = claim;
        this.edate = edate;
        this.noPO=noPO;
        this.total_Exp = total_Exp;
    }
    
    public Expense( Integer cID, String claim, Date edate,String noPO, BigDecimal total_Exp) {
        super();
        this.cID = cID;
        this.claim = claim;
        this.edate = edate;
        this.noPO=noPO;
        this.total_Exp = total_Exp;
    }

    public Expense( String pname, String noVote, String claim, Date edate, String noPO, BigDecimal total_Exp) {
        super();
        this.pname = pname;
        this.noVote = noVote;
        this.claim = claim;
        this.edate = edate;
        this.noPO = noPO;
        this.total_Exp = total_Exp;
    }
    

    public Expense(int eID, String claim, Date edate, String noPO, BigDecimal total_Exp) {
        super();
        this.eID = eID;
        this.claim = claim;
        this.edate = edate;
        this.noPO=noPO;
        this.total_Exp = total_Exp;
    }
    
    public Expense(int eID, int id,int vID, String claim, Date edate, String noPO, BigDecimal total_Exp){
        super();
        this.eID = eID;
        this.id = id;
        this.vID = vID;
        this.claim = claim;
        this.edate = edate;
        this.noPO = noPO;
        this.total_Exp = total_Exp;
    }

    public Expense(int eID, int id, String pname, int vID, String noVote, String claim, Date edate, String noPO, BigDecimal total_Exp, byte[] receipt, String receiptFilename) {
        this.eID = eID;
        this.id = id;
        this.pname = pname;
        this.vID = vID;
        this.noVote = noVote;
        this.claim = claim;
        this.edate = edate;
        this.noPO = noPO;
        this.total_Exp = total_Exp;
        this.receipt = receipt;
        this.receiptFilename = receiptFilename;
    }

    public Expense( int id, int vID, String claim, Date edate, String noPO, BigDecimal total_Exp, byte[] receipt, String receiptFilename) {
        this.id = id;
        this.vID = vID;
        this.claim = claim;
        this.edate = edate;
        this.noPO = noPO;
        this.total_Exp = total_Exp;
        this.receipt = receipt;
        this.receiptFilename = receiptFilename;
    }

    public int geteID() {
        return eID;
    }

    public void seteID(int eID) {
        this.eID = eID;
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
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

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getNoPO() {
        return noPO;
    }

    public void setNoPO(String noPO) {
        this.noPO = noPO;
    }

    public BigDecimal getTotal_Exp() {
        return total_Exp;
    }

    public void setTotal_Exp(BigDecimal total_Exp) {
        this.total_Exp = total_Exp;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getNoVote() {
        return noVote;
    }

    public void setNoVote(String noVote) {
        this.noVote = noVote;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public String getReceiptFilename() {
        return receiptFilename;
    }

    public void setReceiptFilename(String receiptFilename) {
        this.receiptFilename = receiptFilename;
    }

}
