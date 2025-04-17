package com.bankmgmt.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bankmgmt.app.entity.Account;
import com.bankmgmt.app.service.BankService;

import java.util.List;

// TODO: Make this class a Rest Controller
@RestController
public class BankController {

    // TODO Autowired the BankService class
    @Autowired
    private BankService bankService;

    // TODO: API to Create a new account
    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account accountRequest){
      Account ac =  bankService.createAccount(accountRequest);
      if(ac ==null){
        return new ResponseEntity<>(ac , HttpStatus.BAD_REQUEST);

      }
      return new ResponseEntity<>(ac , HttpStatus.CREATED);
    }
    

    // TODO: API to Get all accounts
    
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccount(){
        List<Account> accounts = bankService.getAllAccount();
        return new ResponseEntity<>(accounts , HttpStatus.OK);

    }

    // TODO: API to Get an account by ID
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Integer id){
      Account account = bankService.getAccountById(id);
      if(account==null){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(account,HttpStatus.OK);
    }
    

    // TODO: API to Deposit money
    @PostMapping("/accounts/{id}/deposit")
    public ResponseEntity<Account> depositeMoney(@PathVariable("id") Integer id , @RequestParam("amount") Double amount){
      if(amount<0){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

      }
      Account account = bankService.getAccountById(id);
      if(account==null){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }


      Account accountAfterDeposite = bankService.depositeMoneyAccount(account, amount);
     
      return new ResponseEntity<>(accountAfterDeposite,HttpStatus.OK);
    }

    // TODO: API to Withdraw money
    @PostMapping("/accounts/{id}/withdraw")
    public ResponseEntity<Account> withdrawMoney(@PathVariable("id") Integer id , @RequestParam("amount") Double amount){
     
      Account account = bankService.getAccountById(id);
      if(account==null){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      if(amount<0 || account.getBalance()-amount<0 ){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

      }



      Account accountAfterWithdraw = bankService.depositeMoneyAccount(account,amount);
   
      return new ResponseEntity<>(accountAfterWithdraw,HttpStatus.OK);
    }


    // TODO: API to Transfer money between accounts
    @PostMapping("/accounts/transfer?fromId={fromId}&toId={toId}&amount={amount}")
    public ResponseEntity<List<Account>> transfer(@PathVariable("fromId") Integer fromId , @PathVariable("toId") Integer toId,@PathVariable("amount") Integer amount){
      List<Account> data = bankService.transferAccounts(fromId, toId, amount);
      if(data == null){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

      } 
      return new ResponseEntity<>(data,HttpStatus.OK);

    }

    // TODO: API to Delete an account
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<List<Account>> transfer(@PathVariable("id") Integer id){
      boolean success = bankService.deleteAccount(id);
      if(success == false){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

      } 
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    
}
