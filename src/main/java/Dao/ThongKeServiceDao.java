/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

import bean.CalculateProportion;
import java.util.List;

import bean.IncomeBean;
import model.Shipper;
/**
 *
 * @author AD
 */
public interface ThongKeServiceDao {
    public List<IncomeBean> getListIncomeByShipper(Shipper shipper);
	
   

    public List<IncomeBean> getIncomeByShipperAndMonth(int month);
    public List<CalculateProportion> getProportion();
}
