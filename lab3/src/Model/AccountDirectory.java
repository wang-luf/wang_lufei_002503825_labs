/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author lufei
 */
public class AccountDirectory {
   
    private ArrayList<Account> accounts;
    
    public AccountDirectory(){
        this.accounts = new ArrayList<Account>();
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
    
    public Account addAccount(){
        Account account = new Account();
        accounts.add(account);
        return account;
    }
    
    public void deleteAccount(Account account){
        accounts.remove(account);
    }
    public Account searchAccount(String accountNumber){
        for(Account account : accounts){
            if(account.getAccountNumber().contains(accountNumber)){
                return account;
            }
        }
        return null;
    }
}
