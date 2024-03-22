package model;

import java.util.Date; // Import kiểu dữ liệu Date

/**
 *
 * @author ADMIN
 */
public class Order {
    private int Id;
    private String Name;
    private String Delivery_Location;
    private Date Delivery_Time; 
    private String Receive_Location;
    private Date Receive_Time; 
    private String Status;
    private String Feedback;
    private double Distance;
    private double Price;
    private int Shipper_ID;

    public Order() {
    }

    public Order(int Id, String Name, String Delivery_Location, Date Delivery_Time, String Receive_Location, Date Receive_Time, String Status, String Feedback, double Distance, double Price, int Shipper_ID) {
        this.Id = Id;
        this.Name = Name;
        this.Delivery_Location = Delivery_Location;
        this.Delivery_Time = Delivery_Time;
        this.Receive_Location = Receive_Location;
        this.Receive_Time = Receive_Time;
        this.Status = Status;
        this.Feedback = Feedback;
        this.Distance = Distance;
        this.Price = Price;
        this.Shipper_ID = Shipper_ID;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDelivery_Location() {
        return Delivery_Location;
    }

    public void setDelivery_Location(String Delivery_Location) {
        this.Delivery_Location = Delivery_Location;
    }

    public String getReceive_Location() {
        return Receive_Location;
    }

    public void setReceive_Location(String Receive_Location) {
        this.Receive_Location = Receive_Location;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String Feedback) {
        this.Feedback = Feedback;
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double Distance) {
        this.Distance = Distance;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public int getShipper_ID() {
        return Shipper_ID;
    }

    public void setShipper_ID(int Shipper_ID) {
        this.Shipper_ID = Shipper_ID;
    }

    public Date getDelivery_Time() {
        return Delivery_Time;
    }

    public void setDelivery_Time(Date Delivery_Time) {
        this.Delivery_Time = Delivery_Time;
    }

    public Date getReceive_Time() {
        return Receive_Time;
    }

    public void setReceive_Time(Date Receive_Time) {
        this.Receive_Time = Receive_Time;
    }

    
    
    @Override
    public String toString() {
        return "Order{" + "Id=" + Id + ", Name=" + Name + ", Delivery_Location=" + Delivery_Location + ", Receive_Location=" + Receive_Location + ", Status=" + Status + ", Feedback=" + Feedback + ", Distance=" + Distance + ", Price=" + Price + ", Shipper_ID=" + Shipper_ID + '}';
    }
    
    
}
