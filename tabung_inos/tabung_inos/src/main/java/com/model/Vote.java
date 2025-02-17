/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

/**
 *
 * @author NAJIHAH
 */
public class Vote {
    private int vID;
    private String noVote;
    private String vote;
    private int noseq;

    public Vote() {
        
    }
    
    public Vote(int vID, String noVote, String vote, int noseq) {
        super();
        this.vID = vID;
        this.noVote = noVote;
        this.vote = vote;
        this.noseq = noseq;
    }
    
    public Vote( String noVote, String vote, int noseq) {
        super();
        this.noVote = noVote;
        this.vote = vote;
        this.noseq = noseq;
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

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public int getNoseq() {
        return noseq;
    }

    public void setNoseq(int noseq) {
        this.noseq = noseq;
    }
    
    
}
