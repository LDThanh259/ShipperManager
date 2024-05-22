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
public interface ShipperDao {
    public List<Shipper> getList(boolean is_delete);
    
    public int update(Shipper shipper);
    
    public int delete(Shipper shipper);
    
    public int insert(Shipper shipper);
    
    public List<Integer> getListId();
}
