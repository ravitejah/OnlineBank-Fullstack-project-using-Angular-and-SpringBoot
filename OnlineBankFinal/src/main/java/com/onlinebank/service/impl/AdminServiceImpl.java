//package com.onlinebank.service.impl;
//
//import com.onlinebank.dto.AccountDTO;
//import com.onlinebank.dto.BankUserDTO;
//import com.onlinebank.dto.TransactionDTO;
//import com.onlinebank.entity.Account;
//import com.onlinebank.entity.BankUser;
//import com.onlinebank.entity.Transaction;
//import com.onlinebank.exception.ResourceNotFoundException;
//import com.onlinebank.repository.AccountRepository;
//import com.onlinebank.repository.BankUserRepository;
//import com.onlinebank.repository.TransactionRepository;
//import com.onlinebank.service.AdminService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class AdminServiceImpl implements AdminService {
//
//    @Autowired
//    private BankUserRepository userRepository;
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Autowired
//    private TransactionRepository transactionRepository;
//    
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public List<BankUserDTO> getAllUsers() {
//        return userRepository.findAll().stream()
//                .map(user -> {
//                    BankUserDTO dto = new BankUserDTO();
//                    dto.setName(user.getName());
//                    dto.setId(user.getId());
//                    dto.setEmail(user.getEmail());
//                    dto.setMobileNumber(user.getMobileNumber());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public BankUserDTO getUserById(Long id) {
//        BankUser user = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
//        BankUserDTO dto = new BankUserDTO();
//        dto.setName(user.getName());
//        dto.setId(user.getId());
//        dto.setEmail(user.getEmail());
//        dto.setMobileNumber(user.getMobileNumber());
//        return dto;
//    }
//    
//    
//    @Override
//    public AccountDTO updateAccountBalance(Long accountId, Double newBalance) {
//        Account account = accountRepository.findById(accountId)
//                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));
//     
//        account.setBalance(newBalance);
//        Account updatedAccount = accountRepository.save(account);
//     
//        return mapToDTO(updatedAccount);
//    }
//     
//    private AccountDTO mapToDTO(Account account) {
//        AccountDTO dto = new AccountDTO();
//        dto.setAccountNumber(account.getAccountNumber());
//        dto.setBalance(account.getBalance());
//        dto.setBankUserName(account.getBankUser().getName());
//        dto.setEmail(account.getBankUser().getEmail());
//        dto.setId(account.getId());
//        return dto;
//    }
//     
//
//    @Override
//    public List<AccountDTO> getAllAccounts() {
//        return accountRepository.findAll().stream()
//                .map(account -> {
//                    AccountDTO dto = new AccountDTO();
//                    dto.setAccountNumber(account.getAccountNumber());
//                    dto.setBalance(account.getBalance());
//                    dto.setBankUserName(account.getBankUser().getName());
//                    dto.setEmail(account.getBankUser().getEmail());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<TransactionDTO> getAllTransactions() {
//        return transactionRepository.findAll().stream()
//                .map(transaction -> {
//                    TransactionDTO dto = new TransactionDTO();
//                    dto.setTransactionId(transaction.getId());
//                    dto.setTransactionDateTime(transaction.getTransactionDateTime());
//                    dto.setTransactionType(transaction.getTransactionType());
//                    dto.setAmount(transaction.getAmount());
//                    dto.setAccountNumber(transaction.getAccount().getAccountNumber());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//    
//    @Override
//    public BankUserDTO updateUser(Long id, BankUser updatedUser) {
//        BankUser existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
//        
//        // Update the existing user's details
//        existingUser.setName(updatedUser.getName());
//        existingUser.setEmail(updatedUser.getEmail());
//        existingUser.setAadharNumber(updatedUser.getAadharNumber());
//        existingUser.setMobileNumber(updatedUser.getMobileNumber());
//        
//        // Optionally update the password (encrypt it first)
//        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
//            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
//        }
//
//        // Save the updated user back to the database
//        BankUser savedUser = userRepository.save(existingUser);
//
//        // Map the updated user to a DTO and return it
//        BankUserDTO userDTO = new BankUserDTO();
//        userDTO.setName(savedUser.getName());
//        userDTO.setId(savedUser.getId());
//        userDTO.setEmail(savedUser.getEmail());
//        userDTO.setMobileNumber(savedUser.getMobileNumber());
//        return userDTO;
//    }
//    
//    @Override
//    public void deleteUser(Long id) {
//        BankUser existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
//        userRepository.delete(existingUser);
//    }
//
//
//}

package com.onlinebank.service.impl;

import com.onlinebank.dto.AccountDTO;
import com.onlinebank.dto.BankUserDTO;
import com.onlinebank.dto.TransactionDTO;
import com.onlinebank.entity.Account;
import com.onlinebank.entity.BankUser;
import com.onlinebank.entity.Transaction;
import com.onlinebank.exception.ResourceNotFoundException;
import com.onlinebank.repository.AccountRepository;
import com.onlinebank.repository.BankUserRepository;
import com.onlinebank.repository.TransactionRepository;
import com.onlinebank.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private BankUserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    /** ====================================
     *  USER MANAGEMENT
     *  ==================================== */
    
    @Override
    public List<BankUserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToBankUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BankUserDTO getUserById(Long id) {
        BankUser user = findUserById(id);
        return mapToBankUserDTO(user);
    }

    @Override
    public BankUserDTO updateUser(Long id, BankUser updatedUser) {
        BankUser existingUser = findUserById(id);

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
//        existingUser.setAadharNumber(updatedUser.getAadharNumber());
        existingUser.setMobileNumber(updatedUser.getMobileNumber());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return mapToBankUserDTO(userRepository.save(existingUser));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(findUserById(id));
    }

    /** ====================================
     *  ACCOUNT MANAGEMENT
     *  ==================================== */

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::mapToAccountDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO updateAccountBalance(Long accountId, Double newBalance) {
        Account account = findAccountById(accountId);
        account.setBalance(newBalance);
        return mapToAccountDTO(accountRepository.save(account));
    }

    /** ====================================
     *  TRANSACTION MANAGEMENT
     *  ==================================== */

    @Override
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToTransactionDTO)
                .collect(Collectors.toList());
    }

    /** ====================================
     *  HELPER METHODS
     *  ==================================== */

    private BankUser findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    private Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + id));
    }

    private BankUserDTO mapToBankUserDTO(BankUser user) {
        BankUserDTO dto = new BankUserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setMobileNumber(user.getMobileNumber());
        return dto;
    }

    private AccountDTO mapToAccountDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setBankUserName(account.getBankUser().getName());
        dto.setEmail(account.getBankUser().getEmail());
        return dto;
    }

    private TransactionDTO mapToTransactionDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(transaction.getId());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDateTime(transaction.getTransactionDateTime());
        dto.setAccountNumber(transaction.getAccount().getAccountNumber());
        return dto;
    }
}
