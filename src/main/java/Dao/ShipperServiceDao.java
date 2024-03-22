/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

import java.util.List;
import model.Shipper;

/**
 *
 * @author ADMIN
 */
public interface ShipperServiceDao {
    public List<Shipper> getList();
    public int Update(Shipper shipper);
    public int Delete(Shipper shipper);
    public int Insert(Shipper shipper);
    
    public int getNumOfShipper();
    public List<Integer> getListId();
}
