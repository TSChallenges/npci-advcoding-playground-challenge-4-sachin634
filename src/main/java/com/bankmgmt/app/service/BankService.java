package com.bankmgmt.app.service;

import org.springframework.stereotype.Service;

import com.bankmgmt.app.entity.Account;

import java.sql.SQLOutput;
import java.util.*;

@Service
public class BankService {

    private List<Account> accounts = new ArrayList<>();
    private Map<Integer,String> emailUniqueMap = new HashMap<>();
    private Integer currentId = 1;

    // TODO: Method to Create a new Account
    public Account createAccount(Account ac){
        ac.setId(currentId);
        ac.setBalance(0.00);
        if(emailUniqueMap.containsKey(ac.getEmail()))
            return null;
        else{
            emailUniqueMap.put(currentId, ac.getEmail());
        }
        currentId++;
        accounts.add(ac);
        return ac;
    }
    

    // TODO: Method to Get All Accounts
   public List<Account> getAllAccount(){
    return accounts;
   } 

    // TODO: Method to Get Account by ID
    public Account getAccountById(Integer id){
     Account ac = findAccountById(id);
     if(ac==null){
        return null;
     }
     return ac;
       
    }

    // TODO: Method to Deposit Money to the specified account id
    public Account depositeMoneyAccount(Account ac , double amount){   
     ac.setBalance(ac.getBalance()+amount);
     setAccountData(ac);
     return ac;
        }

    // TODO: Method to Withdraw Money from the specified account id
    public Account withdrawMoneyAccount(Account ac,double amount){
             
        ac.setBalance(ac.getBalance()-amount);
        setAccountData(ac);
        return ac;
      }

    // TODO: Method to Transfer Money from one account to another
    public List<Account> transferAccounts(Integer sender , Integer receiver ,double amount){
        Account senderAccount = findAccountById(sender);
        Account receiverAccount = findAccountById(receiver);

        if(senderAccount==null || receiverAccount==null || senderAccount.getBalance()<amount){
            return null;
        }
        senderAccount.setBalance(senderAccount.getBalance()-amount);
        receiverAccount.setBalance(receiverAccount.getBalance()+amount);
        setAccountData(receiverAccount);
        setAccountData(senderAccount);
        List<Account> details = new ArrayList<>();
        details.add(senderAccount);
        details.add(receiverAccount);
        return details;
    }
    
    // TODO: Method to Delete Account given a account id
    public boolean deleteAccount(Integer id){
        for(int i=0;i<accounts.size();i++){
            if(accounts.get(i).getId()==id){
                accounts.remove(i);
                return true;
            }
        }
        return false;
    }

    public Account findAccountById(Integer id){
        for(Account ac : accounts){
            if(ac.getId()==id){
                return ac;
            }
        }
        return null;
    }

    public void setAccountData(Account account){
        for(int i=0;i<accounts.size();i++){
            if(accounts.get(i).getId()==account.getId()){
                accounts.set(i, account);
            }
        }
        

    
}
}
