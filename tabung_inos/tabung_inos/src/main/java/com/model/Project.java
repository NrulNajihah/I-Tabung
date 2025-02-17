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
public class Project {

    private int id;
    private int uID;
    private String username; // Updated from kpID
    private String pname;
    private int tID;
    private int cID;
    private String tname; // Updated from tID
    private BigDecimal total_All;
    private BigDecimal total_Exp;
    private BigDecimal baki;
    private Date startP;
    private Date endP;
    private String status;
    private int numTbg;
    private int numPro;

    public Project(int id, String username, String pname, String tname, BigDecimal total_All, BigDecimal total_Exp, BigDecimal baki, Date startP, Date endP) {
        this.id = id;
        this.username = username;
        this.pname = pname;
        this.tname = tname;
        this.total_All = total_All;
        this.total_Exp = total_Exp;
        this.baki = baki;
        this.startP = startP;
        this.endP = endP;
    }

    public Project(int id, int uID, String username, String pname, String tname, BigDecimal total_All, BigDecimal total_Exp, BigDecimal baki, Date startP, Date endP) {
        super();
        this.id = id;
        this.uID = uID;
        this.username = username;
        this.pname = pname;
        this.tname = tname;
        this.total_All = total_All;
        this.total_Exp = total_Exp;
        this.baki = baki;
        this.startP = startP;
        this.endP = endP;
    }

    public Project(int id, int uID,  String pname, int tID, BigDecimal total_All, BigDecimal total_Exp, BigDecimal baki, Date startP, Date endP) {
        super();
        this.id = id;
        this.uID = uID;
        this.pname = pname;
        this.tID = tID;
        this.total_All = total_All;
        this.total_Exp = total_Exp;
        this.baki = baki;
        this.startP = startP;
        this.endP = endP;
    }

    public Project(int id, int uID, Date endP) {
        this.id = id;
        this.uID = uID;
        this.endP = endP;
    }

    public Project(int id, int uID, String username, String pname, int tID, BigDecimal total_All, BigDecimal total_Exp, BigDecimal baki, Date startP, Date endP) {
        super();
        this.id = id;
        this.uID = uID;
        this.username = username;
        this.pname = pname;
        this.tID = tID;
        this.total_All = total_All;
        this.total_Exp = total_Exp;
        this.baki = baki;
        this.startP = startP;
        this.endP = endP;
    }


    public Project(int uID, String pname, int tID, BigDecimal total_All, BigDecimal total_Exp, BigDecimal baki, Date startP, Date endP) {
        super();
        this.uID = uID;
        this.pname = pname;
        this.tID = tID;
        this.total_All = total_All;
        this.total_Exp = total_Exp;
        this.baki = baki;
        this.startP = startP;
        this.endP = endP;
    }
    
    public Project(Integer uID, String pname, Integer cID, BigDecimal total_All, BigDecimal total_Exp, 
                   BigDecimal baki, java.sql.Date startP, java.sql.Date endP) {
        this.uID = uID;
        this.pname = pname;
        this.cID = cID; // Ensure this is correct
        this.total_All = total_All;
        this.total_Exp = total_Exp;
        this.baki = baki;
        this.startP = startP;
        this.endP = endP;
    }

    public Project(int id, String pname, BigDecimal total_All, BigDecimal total_Exp, BigDecimal baki, Date startP, Date endP) {
        super();
        this.id = id;
        this.pname = pname;
        this.total_All = total_All;
        this.total_Exp = total_Exp;
        this.baki = baki;
        this.startP = startP;
        this.endP = endP;
    }

    public Project(int id, int uID, String pname, Date startP, Date endP) {
        super();
        this.id = id;
        this.uID = uID;
        this.pname = pname;
        this.startP = startP;
        this.endP = endP;
    }

    public Project(int numTbg, int numPro) {
        this.numTbg = numTbg;
        this.numPro = numPro;
    }

    public Project(int id, int uID, String pname, int tID, BigDecimal total_All, BigDecimal total_Exp, BigDecimal baki) {
        this.id = id;
        this.uID = uID;
        this.pname = pname;
        this.tID = tID;
        this.total_All = total_All;
        this.total_Exp = total_Exp;
        this.baki = baki;
    }

    public Project(int id, int uID, String username, String pname, String tname, BigDecimal total_All, BigDecimal total_Exp, BigDecimal baki, Date startP, Date endP, String status) {
        super();
        this.id = id;
        this.uID = uID;
        this.username = username;
        this.pname = pname;
        this.tname = tname;
        this.total_All = total_All;
        this.total_Exp = total_Exp;
        this.baki = baki;
        this.startP = startP;
        this.endP = endP;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int gettID() {
        return tID;
    }

    public void settID(int tID) {
        this.tID = tID;
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public BigDecimal getTotal_All() {
        return total_All;
    }

    public void setTotal_All(BigDecimal total_All) {
        this.total_All = total_All;
    }

    public BigDecimal getTotal_Exp() {
        return total_Exp;
    }

    public void setTotal_Exp(BigDecimal total_Exp) {
        this.total_Exp = total_Exp;
    }

    public BigDecimal getBaki() {
        return baki;
    }

    public void setBaki(BigDecimal baki) {
        this.baki = baki;
    }

    public Date getStartP() {
        return startP;
    }

    public void setStartP(Date startP) {
        this.startP = startP;
    }

    public Date getEndP() {
        return endP;
    }

    public void setEndP(Date endP) {
        this.endP = endP;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumTbg() {
        return numTbg;
    }

    public void setNumTbg(int numTbg) {
        this.numTbg = numTbg;
    }

    public int getNumPro() {
        return numPro;
    }

    public void setNumPro(int numPro) {
        this.numPro = numPro;
    }

}
