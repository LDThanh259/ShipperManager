package Dao;

import java.util.List;

import model.Customer;

public interface CustomerServiceDao {

    public List<Customer> getList(boolean is_delete);

    public int Update(Customer customer);
    
    public List<String> getListPhone();

    public int Delete(Customer customer);

    public int Insert(Customer customer);

    public List<Integer> getListID();

    public int getNumOfOfCustomer();

    public void getDataFromID(Customer customer);

    public int getDataFromCusPhone(String phoneNumber, Customer customer);
}
