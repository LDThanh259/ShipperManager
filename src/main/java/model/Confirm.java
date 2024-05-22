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
public class Confirm {
    private LocalDateTime time;
    private int shipperId;
    private int cusId;
    private int deliveId;
    
    public Confirm()
    {
        
    }
    
    public Confirm(LocalDateTime time)
    {
        this.time=time;
    }

    public Confirm(LocalDateTime time, int shipperId, int cusId, int deliveId) {
        this.time = time;
        this.shipperId = shipperId;
        this.cusId = cusId;
        this.deliveId = deliveId;
    }
    
    public LocalDateTime getTime()
    {
        return this.time;
    }
    
    public void setTime(LocalDateTime time)
    {
        this.time=time;
    }

    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public int getDeliveId() {
        return deliveId;
    }

    public void setDeliveId(int deliveId) {
        this.deliveId = deliveId;
    }

    @Override
    public String toString() {
        return this.time+" ";
    }
    
    
}
