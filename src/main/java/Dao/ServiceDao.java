package Dao;

import java.util.List;

import model.Service;
/**
*
* @author ADMIN
*/
public interface ServiceDao {
	public List<Service> getList();
	
    public int Update(Service service);
    public int Delete(Service service);
    public int Insert(Service service);

    public List<Integer> getListID();
    public String getDataFromID(int serviceid);
    public List<Service> selectShipFeeforOrd(double weight, double distance);

}
