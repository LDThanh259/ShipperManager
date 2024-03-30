/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author ADMIN
 */
public class IncomeBean {
    private int id;
    private String nameShipper;
    private int month;
    private float income;

    public IncomeBean() {
    }

    public IncomeBean(int id, String nameShipper, float income) {
        this.id = id;
        this.nameShipper = nameShipper;
        this.income = income;
    }
    
    public IncomeBean(int id, String nameShipper,int month, float income) {
        this.id = id;
        this.nameShipper = nameShipper;
        this.month = month;
        this.income = income;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameShipper() {
        return nameShipper;
    }

    public void setNameShipper(String nameShipper) {
        this.nameShipper = nameShipper;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
    
    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }
    
    
}
