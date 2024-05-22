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
public class Incident {
    private int id;
    private LocalDateTime time;
    private String description;
    private boolean resolved;
    private String resolutionDetails;

    public Incident() {
    }

    public Incident(int id, LocalDateTime time, String description, boolean resolved, String resolutionDetails) {
        this.id = id;
        this.time = time;
        this.description = description;
        this.resolved = resolved;
        this.resolutionDetails = resolutionDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public String getResolutionDetails() {
        return resolutionDetails;
    }

    public void setResolutionDetails(String resolutionDetails) {
        this.resolutionDetails = resolutionDetails;
    }

    @Override
    public String toString() {
        return this.id+" "+this.time+" "+this.description+" "+this.resolved+" "+this.resolutionDetails;
    }
}
