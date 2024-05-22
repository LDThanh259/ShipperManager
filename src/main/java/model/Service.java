/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author V
 */
public class Service {
    private int id;
    private String name;
    private int  maxDistance;
    private int maxWeight;
    private int price;
    

    public Service() {
    }

    public Service(int id, String name,int maxDistance, int maxWeight, int price) {
        this.id = id;
        this.maxDistance = maxDistance;
        this.maxWeight = maxWeight;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.id+" "+this.name+" "+this.maxDistance+" "+this.maxWeight+" "+this.price;
    } 
}
