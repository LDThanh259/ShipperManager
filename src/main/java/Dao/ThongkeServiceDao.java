/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

import bean.IncomeBean;
import java.util.List;
import model.Shipper;

/**
 *
 * @author ADMIN
 */
public interface ThongkeServiceDao {
    public List<IncomeBean> getListIncome();
    public List<IncomeBean> getIncomeByShipperAndMonth(Shipper shipper);
}
