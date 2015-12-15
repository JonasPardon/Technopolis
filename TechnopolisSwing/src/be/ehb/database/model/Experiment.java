/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ehb.database.model;

/**
 *
 * @author ruben_000
 */
public class Experiment {
    private int id;
    private int nummer;
    private int actief;
    private int zone;  

    public void setId(int Id) {
        this.id = Id;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public void setActief(int actief) {
        this.actief = actief;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }


    public int getId() {
        return id;
    }

    public int getNummer() {
        return nummer;
    }

    public int getActief() {
        return actief;
    }

    public int getZone() {
        return zone;
    }

    public Experiment(int Id, int nummer, int actief, int zone) {
        this.id = Id;
        this.nummer = nummer;
        this.actief = actief;
        this.zone = zone;
    }
    
    
   
    
}
