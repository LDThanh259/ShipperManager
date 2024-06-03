package Dao;

import java.util.List;

import model.Customer;

public class CustomerServiceDaoImpl implements CustomerServiceDao {

    private CustomerDao customerDao = null;

    public CustomerServiceDaoImpl() {
        customerDao = new CustomerDaoImpl();
    }

    @Override
    public List<Customer> getList(boolean is_delete) {
        // TODO Auto-generated method stub
        return customerDao.getList(is_delete);
    }

    @Override
    public int Update(Customer customer) {
        // TODO Auto-generated method stub
        return customerDao.Update(customer);
    }

    @Override
    public int Delete(Customer customer) {
        // TODO Auto-generated method stub
        return customerDao.Delete(customer);
    }

    @Override
    public int Insert(Customer customer) {
        // TODO Auto-generated method stub
        return customerDao.Insert(customer);
    }

    @Override
    public List<Integer> getListID() {
        // TODO Auto-generated method stub
        return customerDao.getListID();
    }

    @Override
    public int getNumOfOfCustomer() 
    {
        return customerDao.getList(false).size();        
    }
    
    @Override
	public List<String> getListPhone() {

		return customerDao.getListPhone();
		// TODO Auto-generated method stub
	}
	@Override
	public void getDataFromID(Customer customer)
	{
		customerDao.getDataFromID(customer);
	}
	
	@Override
	public int getDataFromCusPhone(String phoneNumber, Customer customer) 
	{
		return customerDao.getDataFromCusPhone(phoneNumber, customer);
	}
}
