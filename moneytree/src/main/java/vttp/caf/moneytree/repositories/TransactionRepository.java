package vttp.caf.moneytree.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.caf.moneytree.models.Transaction;

import static vttp.caf.moneytree.repositories.Queries.*;

@Repository
public class TransactionRepository {
    
    @Autowired
    private JdbcTemplate template;

    public void addTransaction(Transaction tx, String username){
        // Integer added = 0;
        // Optional<String> optTransaction = Optional.empty();
        template.update(SQL_INSERT_NEW_TRANSACTION, tx.getCategory(), tx.getDescription(), tx.getPicture(), tx.getAmount(), username);
        
    }

}
