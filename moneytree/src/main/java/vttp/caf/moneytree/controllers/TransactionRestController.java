package vttp.caf.moneytree.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import vttp.caf.moneytree.models.Transaction;
import vttp.caf.moneytree.services.TransactionService;

@RestController
@RequestMapping("/api")
// @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
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
        HttpSession sess
    ) throws IOException{

        String username = (String)sess.getAttribute("username");
        Transaction tx = new Transaction();
        if(description!=null && picture!=null){
            tx = Transaction.create(category, description, picture, amount, username, date);
        } else if (description!=null){
            tx = Transaction.create(category, description, amount, username, date);
        } else if (picture!=null){
            tx = Transaction.create(category, description, picture, amount, username, date);
        } else {
            tx = Transaction.create(category, amount, username, date);
        }
        tService.addTransaction(tx, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/getTransactions/{username}/all")
    public ResponseEntity<String> getAllTransaction(@PathVariable("username") String username) throws SQLException{
        List<Transaction> transactions = tService.getTransactions(username);
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Transaction transaction: transactions){
            arrBuilder.add(transaction.toJson());
        }
        // System.out.println(">>>>>list of transactions: " + arrBuilder.build().toString());
        return ResponseEntity.ok(
            arrBuilder.build().toString());
    }
}
