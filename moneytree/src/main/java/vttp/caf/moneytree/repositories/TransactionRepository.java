package vttp.caf.moneytree.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.caf.moneytree.models.Transaction;

import static vttp.caf.moneytree.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

@Repository
public class TransactionRepository {
    
    @Autowired
    private JdbcTemplate template;

    public void addTransaction(Transaction tx, String username){
        Integer userId = 0;
        SqlRowSet rs = template.queryForRowSet(SQL_GET_USER_ID_FROM_USERNAME, username);
        while(rs.next()){
            userId = rs.getInt("id");
        }
        
        template.update(SQL_INSERT_NEW_TRANSACTION, tx.getCategory(), tx.getDescription(), tx.getPicture(), tx.getAmount(), userId, tx.getDate());
    }

    public List<Transaction> getTransactionsByUsername(String username) {
        List<Transaction> transactions = new LinkedList<>();
        Integer userId = 0;
        SqlRowSet rs = template.queryForRowSet(SQL_GET_USER_ID_FROM_USERNAME, username);
        while(rs.next()){
            userId = rs.getInt("id");
            
        }
        transactions = template.query(SQL_SELECT_ALL_FROM_TRANSACTIONS_BY_USER_ID, Transaction.rowMapper, userId);
        
        return transactions;
    }

    public void deleteTransaction(String transactionId){
        template.update(SQL_DELETE_TRANSACTION_FROM_TRANSACTIONID, transactionId);
    }

    public void updateTransaction(Transaction tx, String username){
        Integer userId = 0;
        SqlRowSet rs = template.queryForRowSet(SQL_GET_USER_ID_FROM_USERNAME, username);
        while(rs.next()){
            userId = rs.getInt("id");
        }
        template.update(SQL_UPDATE_TRASACTION, tx.getCategory(), tx.getDescription(), tx.getPicture(), tx.getAmount(), userId, tx.getDate(), tx.getTransactionId());
    }

    @Transactional
    public void deleteUser (String username){
        Integer userId = 0;
        SqlRowSet rs = template.queryForRowSet(SQL_GET_USER_ID_FROM_USERNAME, username);
        while(rs.next()){
            userId = rs.getInt("id");
        }
        template.update(SQL_DELETE_TRANSACTIONS_BY_USERID, userId);
        List<Transaction> transactions = getTransactionsByUsername(username);
        if (transactions.size()==0){
            template.update(SQL_DELETE_USER_FROM_USER_ROLES, userId);
            template.update(SQL_DELETE_USER, userId);
        }
    }

}
