package com.dao;

import com.bean.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMapper {
    List<Account> selectbyidandpwd(Account account);
    List<Account> selectbyid(Account account);
    int insert(Account account);
    int updateMoney(Account account);
    List<Account> selectbypager(Account account);
    int insertUser(Account account);
    int delete(String id);
    int updateUser(Account account);
}
