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
public class Zone {
    int id;
    String naam;
    int nummer;

    public void setId(int id) {
        this.id = id;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getNummer() {
        return nummer;
    }
    
    
}
