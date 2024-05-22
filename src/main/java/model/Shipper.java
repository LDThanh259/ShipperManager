/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author V
 */
public class Shipper {
    private int id;
    private String name;
    private String gender;
    private String phoneNumber;
    private String distinct;
    private String province;
    private String ward;
    private String licensePlate;
    private String status;
    private double rating;
    private boolean isDeleted;

    public Shipper() {
    }

    public Shipper(int id, String name, String gender, String phoneNumber, String distinct, String province, String ward, String licensePlate, String status, double rating, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.distinct = distinct;
        this.province = province;
        this.ward = ward;
        this.licensePlate = licensePlate;
        this.status = status;
        this.rating = rating;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String isGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setDistinct(String distinct) {
        this.distinct = distinct;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Shipper{" + "id=" + id + ", name=" + name + ", gender=" + gender + ", phoneNumber=" + phoneNumber + ", distinct=" + distinct + ", province=" + province + ", ward=" + ward + ", licensePlate=" + licensePlate + ", status=" + status + ", rating=" + rating + ", isDeleted=" + isDeleted + '}';
    }
    
    
    
}
