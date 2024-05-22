package Dao;

import java.util.List;

import model.Service;

public class ServiceSERVICEDaoImpl implements ServiceSERVICEDao {
	
    private ServiceDao serviceDao = null;

    public ServiceSERVICEDaoImpl() 
    {
        serviceDao = new ServiceDaoImpl();
    }
    
	@Override
	public List<Service> getList() {
		// TODO Auto-generated method stub
		return serviceDao.getList();
	}

	@Override
	public int Update(Service service) {
		// TODO Auto-generated method stub
		return serviceDao.Update(service);
	}

	@Override
	public int Delete(Service service) {
		// TODO Auto-generated method stub
		return serviceDao.Delete(service);
	}

	@Override
	public int Insert(Service service) {
		// TODO Auto-generated method stub
		return serviceDao.Insert(service);
	}

	@Override
	public List<Integer> getListID() {
		// TODO Auto-generated method stub
		return serviceDao.getListID();
	}

	@Override
	public String getDataFromID(int serviceid) {
		// TODO Auto-generated method stub
		return serviceDao.getDataFromID(serviceid);
	}

	@Override
	public List<Service> selectShipFeeforOrd(double weight, double distance) {
		// TODO Auto-generated method stub
		return serviceDao.selectShipFeeforOrd(weight, distance);
	}

}
