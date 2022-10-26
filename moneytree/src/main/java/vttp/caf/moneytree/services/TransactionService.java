package vttp.caf.moneytree.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.caf.moneytree.models.Transaction;
import vttp.caf.moneytree.repositories.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository tRepo;

    public void addTransaction(Transaction tx, String username){
        tRepo.addTransaction(tx, username);
    }

    public List<Transaction> getTransactions(String username) throws SQLException{
        return tRepo.getTransactionsByUsername(username);

    }

    public void deleteTransaction(String transactionId){
        tRepo.deleteTransaction(transactionId);
    }
    
}
