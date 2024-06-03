/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

import java.util.List;

import bean.*;
import model.Shipper;
/**
 *
 * @author AD
 */
public interface ThongKeDao {
    public List<IncomeBean> getListIncomeByShipper(Shipper shipper);
	
    public List<IncomeBean> getIncomeByShipperAndMonth(int month);
    public List<CalculateProportion> getProportion();
    
}
