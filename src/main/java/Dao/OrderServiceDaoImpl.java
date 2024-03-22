/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import java.util.List;
import model.Order;
import model.Shipper;

/**
 *
 * @author ADMIN
 */
public class OrderServiceDaoImpl implements OrderServiceDao{
    private OrderDao orderDao = null;

    public OrderServiceDaoImpl() {
        orderDao = new OrderDaoImpl();
    }
    
    @Override
    public List<Order> getList() {
        return orderDao.getList();
    }

    @Override
    public List<Order> getOrderListForShipper(Shipper shipper) {
        return orderDao.getOrderListForShipper(shipper);
    }

    @Override
    public int Update(Order order) {
        return orderDao.Update(order);
    }

    @Override
    public int Delete(Order order) {
        return orderDao.Delete(order);
    }

    @Override
    public int Insert(Order order) {
        return orderDao.Insert(order);
    }

    @Override
    public int getNumOfOrder() {
        return orderDao.getList().size();
    }
    
}
