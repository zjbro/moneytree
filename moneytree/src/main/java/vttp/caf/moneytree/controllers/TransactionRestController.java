package vttp.caf.moneytree.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.caf.moneytree.models.Transaction;
import vttp.caf.moneytree.services.TransactionService;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class TransactionRestController {

    @Autowired
    private TransactionService tService;
    
    @PostMapping(path="/addTransaction", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addNewTransaction(
        @RequestPart String category,
        @RequestPart(required = false) String description,
        @RequestPart(required = false) MultipartFile picture,
        @RequestPart String amount,
        @RequestPart String date,
        @RequestPart String username
    ) throws IOException{

        Transaction tx = new Transaction();
        if(description!=null && picture!=null){
            tx = Transaction.create(category, description, picture, amount, date);
        } else if (description!=null){
            tx = Transaction.create(category, description, amount, date);
        } else if (picture!=null){
            tx = Transaction.create(category, description, picture, amount, date);
        } else {
            tx = Transaction.create(category, amount, date);
        }
        tService.addTransaction(tx, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/getTransactions/{username}/all")
    public ResponseEntity<String> getAllTransaction(@PathVariable("username") String transactionId) throws SQLException{
        List<Transaction> transactions = tService.getTransactions(transactionId);
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Transaction transaction: transactions){
            arrBuilder.add(transaction.toJson());
        }

        return ResponseEntity.ok(
            arrBuilder.build().toString());
    }

    @DeleteMapping(path="/deleteTransaction")
    public ResponseEntity<String> deleteTransaction(@RequestParam String transactionId){
        tService.deleteTransaction(transactionId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path="/updateTransaction", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateTransaction(
        @RequestPart String category,
        @RequestPart(required = false) String description,
        @RequestPart(required = false) MultipartFile picture,
        @RequestPart String amount,
        @RequestPart String date,
        @RequestPart String username,
        @RequestPart String transactionId
    ) throws IOException{

        Integer txId = Integer.parseInt(transactionId);
        Transaction tx = new Transaction();
        if(description!=null && picture!=null){
            tx = Transaction.create(category, description, picture, amount, txId, date);
        } else if (description!=null){
            tx = Transaction.create(category, description, amount, txId, date);
        } else if (picture!=null){
            tx = Transaction.create(category, description, picture, amount, txId, date);
        } else {
            tx = Transaction.create(category, amount, txId, date);
        }
        tService.updateTransaction(tx, username);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping(path="/deleteAccount")
    public ResponseEntity<String> deleteAccount(
        @RequestPart String username
    ){
        tService.deleteUser(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/catfact")
    public ResponseEntity<String> getCatFact(){
        JsonObject o = Json.createObjectBuilder()
        .add("catFact", tService.getRandomCatFact())
        .build();
        return ResponseEntity.ok(o.toString());
    
    }
        

}
