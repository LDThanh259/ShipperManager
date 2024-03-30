/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import bean.IncomeBean;
import java.util.List;
import model.Shipper;

/**
 *
 * @author ADMIN
 */
public class ThongkeServiceDaoImpl implements ThongkeServiceDao {

    private ThongkeDao thongkeDao = null;

    public ThongkeServiceDaoImpl() {
        this.thongkeDao = new ThongkeDaoImpl();
    }

    @Override
    public List<IncomeBean> getListIncome() {
        return thongkeDao.getListIncome();
    }

    @Override
    public List<IncomeBean> getIncomeByShipperAndMonth(Shipper shipper) {
        return thongkeDao.getIncomeByShipperAndMonth(shipper);
    }

}
