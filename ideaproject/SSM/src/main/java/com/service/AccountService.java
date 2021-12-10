package com.service;

import com.bean.Account;
import com.dao.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(rollbackFor = java.lang.Exception.class)
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;

    public Account login(Account account){
        List<Account> accountList = accountMapper.selectbyidandpwd(account);
        if (accountList.isEmpty())
            return null;
        else
            return accountList.get(0);
    }

    public  boolean checkid(Account account){
        List<Account> accountList = accountMapper.selectbyid(account);
        if (accountList.isEmpty())
            return true;
        else
            return false;
    }

    public int register(Account account) {
        return accountMapper.insert(account);
    }
    public int updateMoney(Account account){
        return accountMapper.updateMoney(account);
    }
    public int update(Account account) {
        return accountMapper.updateUser(account);
    }

    public Account select(Account account) {
        List<Account> accountList = accountMapper.selectbyid(account);
        if (!accountList.isEmpty())
            return accountList.get(0);
        else
            return null;
    }

    public List<Account> selectbypager(Account account) {
        return accountMapper.selectbypager(account);
    }

    public int insert(Account account) {
        return accountMapper.insertUser(account);
    }

    public int delete(String id) {
        return accountMapper.delete(id);
    }
}
