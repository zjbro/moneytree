package vttp.caf.moneytree.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vttp.caf.moneytree.models.Transaction;
import vttp.caf.moneytree.services.TransactionService;

@RestController
@RequestMapping
public class TransactionRestController {

    @Autowired
    private TransactionService tService;
    
    @PostMapping(path="/addTransaction", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addNewTransaction(
        @RequestPart String category,
        @RequestPart(required = false) String description,
        @RequestPart(required = false) MultipartFile picture,
        // @RequestPart(required = false) String username,
        @RequestPart String amount
    ) throws IOException{

        String username = "fred";
        Transaction tx = new Transaction();
        if(description!=null && picture!=null){
            tx = Transaction.create(category, description, picture, amount, username);
        } else if (description!=null){
            tx = Transaction.create(category, description, amount, username);
        } else if (picture!=null){
            tx = Transaction.create(category, description, picture, amount, username);
        } else {
            tx = Transaction.create(category, amount, username);
        }
        tService.addTransaction(tx, username);
        return ResponseEntity.ok().build();
    }
}
