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
    public List<Shipper> getList() {
        return shipperDao.getList();
    }

    @Override
    public int Update(Shipper shipper) {
        return shipperDao.Update(shipper);
    }

    @Override
    public int Delete(Shipper shipper) {
        return shipperDao.Delete(shipper);
    }

    @Override
    public int Insert(Shipper shipper) {
        return shipperDao.Insert(shipper);
    }

    @Override
    public int getNumOfShipper() {
        return shipperDao.getList().size();
    }

    @Override
    public List<Integer> getListId() {
        return shipperDao.getListId();
    }
    
    
}
