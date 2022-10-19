package vttp.caf.moneytree.services;

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
    
}
