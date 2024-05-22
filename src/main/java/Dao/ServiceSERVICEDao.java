package Dao;

import java.util.List;

import model.Order;
import model.Service;

public interface ServiceSERVICEDao {
	public List<Service> getList();
	
	
    public int Update(Service service);
    public int Delete(Service service);
    public int Insert(Service service);

    public List<Integer> getListID();
    public String getDataFromID(int serviceid);
    public List<Service> selectShipFeeforOrd(double weight, double distance);
}
