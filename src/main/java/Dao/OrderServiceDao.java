/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

import java.time.Month;
import java.util.List;

import model.Customer;
import model.Order;
import model.Shipper;

/**
 *
 * @author ADMIN
 */
public interface OrderServiceDao {

    public List<Order> getList();

    public List<Order> getDeletedOrders(boolean isdeleted);

    public List<Order> getUnDeliveryOrders();

    public int Update(Order order);

    public int Delete(Order order);

    public int Insert(Order order);

    public int getNumOfOrder();

    public void getDataFromID(Order order);

    public List<Order> getCompletedOrders();

    public List<Integer> getList_ID();

    public List<Order> getOrderListForShipper(Shipper shipper, int month, String type);

    public int checkPhoneNumberExists(String phoneNumber, Customer customer);

    public int UpdateOrderTime(Order order);

    public int UpdateShipCount(Order order);

    public int UpdateCompleteTime(Order order);

}
