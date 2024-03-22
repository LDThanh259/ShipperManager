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
    private String nameShipper;
    private float income;

    public IncomeBean() {
    }

    public IncomeBean(String nameShipper, float income) {
        this.nameShipper = nameShipper;
        this.income = income;
    }

    
    
    public String getNameShipper() {
        return nameShipper;
    }

    public void setNameShipper(String nameShipper) {
        this.nameShipper = nameShipper;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }
    
    
}
