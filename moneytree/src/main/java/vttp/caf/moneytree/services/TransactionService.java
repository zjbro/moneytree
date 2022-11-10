package vttp.caf.moneytree.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.caf.moneytree.models.Transaction;
import vttp.caf.moneytree.repositories.TransactionRepository;

@Service
public class TransactionService {

    private static final String URL = "https://meowfacts.herokuapp.com/";

    @Autowired
    private TransactionRepository tRepo;

    public void addTransaction(Transaction tx, String username){
        tRepo.addTransaction(tx, username);
    }

    @Cacheable(value="transaction")
    public List<Transaction> getTransactions(String username) throws SQLException{
        return tRepo.getTransactionsByUsername(username);
    }

    @CacheEvict(value="transaction", allEntries=true)
    public void deleteTransaction(String transactionId){
        tRepo.deleteTransaction(transactionId);
    }

    @CacheEvict(value="transaction")
    public void updateTransaction(Transaction tx, String username){
        tRepo.updateTransaction(tx, username);
    }

    public void deleteUser(String username){
        tRepo.deleteUser(username);
    }

    public String getRandomCatFact(){
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> req= RequestEntity.get(URL).build();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
        String catFact = "";

        try {
            InputStream is = new ByteArrayInputStream(resp.getBody().getBytes());
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            catFact = o.getJsonArray("data").getString(0);
        } catch(Exception ex){
            ex.printStackTrace();
        }

        return catFact;
    }

    
}
