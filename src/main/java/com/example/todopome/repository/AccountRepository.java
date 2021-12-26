package com.example.todopome.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.example.todopome.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class AccountRepository {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private PasswordEncoder encoder;

    public Account save(Account account) {
        dynamoDBMapper.save(account);
        return account;
    }
    public List<Account> findAll(){
        return dynamoDBMapper.scan(Account.class, new DynamoDBScanExpression());
    }

    public Account findById(String id) {
        return dynamoDBMapper.load(Account.class, id);
    }



    public String delete(String id) {
        Account account = dynamoDBMapper.load(Account.class, id);
        dynamoDBMapper.delete(account);
        return "account Deleted!";
    }

    public Account update(String id, Account account) {
        dynamoDBMapper.save(account,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("id",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(id)
                                )));
        return account;
    }

    public Account findByUsername(String username) {
       List<Account> accounts=  dynamoDBMapper.scan(Account.class, new DynamoDBScanExpression());
        for (Account a: accounts
             ) {
            if(a.getUsername().equalsIgnoreCase(username)){
                return  a;
            }
        }
        return  null;
    }



    public boolean existsByUsername(String username) {
        List<Account> accounts=  dynamoDBMapper.scan(Account.class, new DynamoDBScanExpression());
        for (Account a: accounts
        ) {
            if(a.getUsername().equalsIgnoreCase(username)){
                return  true;
            }
        }
        return  false;
    }
}


