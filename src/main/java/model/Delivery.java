/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author V
 */
public class Delivery {
    private int id;
    private LocalDateTime pickupTime;
    private String description;
    private String startLocation;
    private String endLocation;

    public Delivery() {
    }

    public Delivery(int id, LocalDateTime pickupTime, String description, String startLocation, String endLocation) {
        this.id = id;
        this.pickupTime = pickupTime;
        this.description = description;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    @Override
    public String toString() {
        return this.id+" "+this.pickupTime+" "+this.description+" "+this.startLocation+" "+this.endLocation;
    }
}
