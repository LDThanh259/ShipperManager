package Dao;

import bean.*;
import java.util.List;

import model.Service;
import model.Shipper;

public class ThongKeServiceDaoImpl implements ThongKeServiceDao {
	
    private ThongKeDao thongkeDao = null;

    public ThongKeServiceDaoImpl() 
    {
        thongkeDao = new ThongKeDaoImpl();
    }
    
	@Override
	public List<IncomeBean> getListIncomeByShipper(Shipper shipper) {
		// TODO Auto-generated method stub
		return thongkeDao.getListIncomeByShipper(shipper);
	}
        
    
    @Override
	public List<IncomeBean> getIncomeByShipperAndMonth(int month) {
		// TODO Auto-generated method stub
		return thongkeDao.getIncomeByShipperAndMonth(month);
	}
        @Override
	public List<CalculateProportion> getProportion() {
		// TODO Auto-generated method stub
		return thongkeDao.getProportion();
	}
}
