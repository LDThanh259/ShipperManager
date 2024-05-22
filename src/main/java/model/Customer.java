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
public class Customer {

    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private Boolean gender;
    private String ward;
    private String distinct;
    private String province;
    private boolean isDeleted;
    private String updated;

    public Customer() {

    }

    public Customer(int id, String name, String phoneNumber, String email, Boolean gender, String ward, String distinct, String province) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.ward = ward;
        this.distinct = distinct;
        this.province = province;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
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

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", email=" + email + ", gender=" + gender + ", ward=" + ward + ", distinct=" + distinct + ", province=" + province + '}';
    }

    public void setUpdated(String updated) {
        this.updated=updated;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted=isDeleted;
    }

}
