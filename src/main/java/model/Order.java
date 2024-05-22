package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {
    private int id;
    private String name;
    private double weight;
    private double shipFee;
    private double price;
    private String ward;
    private String province;
    private String distinct;
    private double distance;
    private String description;
    private LocalDateTime orderDate;
    private LocalDate expectedDeliveryDate;
    private boolean isDeleted;
    private boolean respond;
    private int shipperId;
    private int cusId;
    private LocalDateTime shipTime;
    private int shipCount;
    private LocalDateTime completedTime;
    private boolean confirm;
    private int serviceId;

    public Order() {
    }

    public Order(int id, String name, double weight, double shipFee, double price, String ward, String province, String distinct, double distance, String description, LocalDateTime orderDate, LocalDate expectedDeliveryDate, boolean isDeleted, boolean respond, int shipperId, int cusId, LocalDateTime shipTime, int shipCount, LocalDateTime completedTime, boolean confirm, int serviceId) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.shipFee = shipFee;
        this.price = price;
        this.ward = ward;
        this.province = province;
        this.distinct = distinct;
        this.distance = distance;
        this.description = description;
        this.orderDate = orderDate;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.isDeleted = isDeleted;
        this.respond = respond;
        this.shipperId = shipperId;
        this.cusId = cusId;
        this.shipTime = shipTime;
        this.shipCount = shipCount;
        this.completedTime = completedTime;
        this.confirm = confirm;
        this.serviceId = serviceId;
    }

    // Getters and setters
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getShipFee() {
        return shipFee;
    }

    public void setShipFee(double shipFee) {
        this.shipFee = shipFee;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setDistinct(String distinct) {
        this.distinct = distinct;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isRespond() {
        return respond;
    }

    public void setRespond(boolean respond) {
        this.respond = respond;
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

    public LocalDateTime getShipTime() {
        return shipTime;
    }

    public void setShipTime(LocalDateTime shipTime) {
        this.shipTime = shipTime;
    }

    public int getShipCount() {
        return shipCount;
    }

    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }

    public LocalDateTime getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(LocalDateTime completedTime) {
        this.completedTime = completedTime;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
