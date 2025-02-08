package com.onlinebank.controller;

import com.onlinebank.dto.*;
import com.onlinebank.entity.BankUser;
import com.onlinebank.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public List<BankUserDTO> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public BankUserDTO getUserById(@PathVariable Long id) {
        return adminService.getUserById(id);
    }

    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts() {
        return adminService.getAllAccounts();
    }
    
    @PutMapping("/accounts/{accountId}/balance")
    public AccountDTO updateAccountBalance(@PathVariable Long accountId, @RequestParam Double newBalance) {
        return adminService.updateAccountBalance(accountId, newBalance);
    }

    @GetMapping("/transactions")
    public List<TransactionDTO> getAllTransactions() {
        return adminService.getAllTransactions();
    }
    
    @PutMapping("/users/{id}")
    public BankUserDTO updateUser(@PathVariable Long id, @RequestBody BankUser bankUser) {
        return adminService.updateUser(id, bankUser);
    }


    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return "User deleted successfully!";
    }
}
