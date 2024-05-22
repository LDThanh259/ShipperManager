/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import java.util.List;
import model.Shipper;

/**
 *
 * @author ADMIN
 */
public class ShipperServiceDaoImpl implements ShipperServiceDao{

    private ShipperDao shipperDao = null;

    public ShipperServiceDaoImpl() {
        shipperDao = new ShipperDaoImpl();
    }
    
    @Override
    public List<Shipper> getList(boolean is_delete) {
        return shipperDao.getList(is_delete);
    }

    @Override
    public int update(Shipper shipper) {
        return shipperDao.update(shipper);
    }

    @Override
    public int delete(Shipper shipper) {
        return shipperDao.delete(shipper);
    }

    @Override
    public int insert(Shipper shipper) {
        return shipperDao.insert(shipper);
    }

    @Override
    public int getNumOfShipper() {
        return shipperDao.getList(false).size();
    }

    @Override
    public List<Integer> getListId() {
        return shipperDao.getListId();
    }
    
    
}
