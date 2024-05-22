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
public class Dilive_Incident {
    private LocalDateTime time;
    private int count;
    private int deliveId;
    private int incidentId;

    public Dilive_Incident() {
    }

    public Dilive_Incident(LocalDateTime time, int count) {
        this.time = time;
        this.count = count;
    }

    public Dilive_Incident(LocalDateTime time, int count, int deliveId, int incidentId) {
        this.time = time;
        this.count = count;
        this.deliveId = deliveId;
        this.incidentId = incidentId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDeliveId() {
        return deliveId;
    }

    public void setDeliveId(int deliveId) {
        this.deliveId = deliveId;
    }

    public int getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    @Override
    public String toString() {
        return this.time+" "+this.count;
    }
}
