/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

import java.time.Month;
import java.util.List;
import model.Order;
import model.Shipper;

/**
 *
 * @author ADMIN
 */
public interface OrderServiceDao {
    public List<Order> getList();
    public int Update(Order order);
    public int Delete(Order order);
    public int Insert(Order order);
    public int getNumOfOrder();
    public List<Order> getOrderListForShipper(Shipper shipper,int month);
}
