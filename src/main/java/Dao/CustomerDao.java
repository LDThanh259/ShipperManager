package Dao;

import java.util.List;

import model.Customer;

public interface CustomerDao {
	public List<Customer> getList();
	
	public List<String> getListPhone();
	
    public int Update(Customer customer);
    public int Delete(Customer customer);
    public int Insert(Customer customer);

    public List<Integer> getListID();
	public void getDataFromID(Customer customer);
	public int getDataFromCusPhone(String phoneNumber, Customer customer);
}
