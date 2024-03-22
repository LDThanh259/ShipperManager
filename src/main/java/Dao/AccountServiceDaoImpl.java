/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import model.Account;

/**
 *
 * @author ADMIN
 */
public class AccountServiceDaoImpl implements AccountServiceDao{
    private AccountDao accountDao;

    public AccountServiceDaoImpl() {
        accountDao = new AccountDaoImpl();
    }

    public AccountServiceDaoImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
 
    @Override
    public Account login(String username, String password) {
        return accountDao.login(username, password);
    }
    
}
