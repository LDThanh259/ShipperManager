/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author AD
 */
public class CalculateProportion {
    private int id;
    private String nameShipper;
    private float complete;
    private float total;
    private float proportion;// tỉ lệ
    
    public CalculateProportion(){
        
    }
    public CalculateProportion(int id, String nameShipper,float complete, float total,float proportion){
        this.id = id;
        this.nameShipper = nameShipper;
        this.complete = complete;
        this.total = total;
        this.proportion = proportion;
    }
    public String getName(){
        return this.nameShipper;
    }
    public void setName(String nameShipper){
        this.nameShipper = nameShipper;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    public float getComplete(){
        return this.complete;
    }
    public void setComplete(float complete){
        this.complete = complete;
    }
    public float getTotal(){
        return this.total;
    }
    public void setTotal(float total){
        this.total = total;
    }
    public float getProportion(){
        return this.proportion;
    }
    public void setProportion(){
        this.proportion=((float)(complete/total))*100;
    }
}
