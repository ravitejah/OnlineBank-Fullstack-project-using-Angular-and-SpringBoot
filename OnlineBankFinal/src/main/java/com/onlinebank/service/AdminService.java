package com.onlinebank.service;

import com.onlinebank.dto.AccountDTO;
import com.onlinebank.dto.BankUserDTO;
import com.onlinebank.dto.TransactionDTO;
import com.onlinebank.entity.BankUser;

import java.util.List;

public interface AdminService {
    List<BankUserDTO> getAllUsers();
    BankUserDTO getUserById(Long id);
    List<AccountDTO> getAllAccounts();
    List<TransactionDTO> getAllTransactions();
    BankUserDTO updateUser(Long id, BankUser bankUser); // Add this method
    void deleteUser(Long id); // Optionally include delete functionality
    AccountDTO updateAccountBalance(Long accountId, Double newBalance);
}
