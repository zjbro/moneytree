package vttp.caf.moneytree.models;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

public class Transaction {

    private Integer transactionId;
    private String category;
    private String description;
    private byte[] picture;
    private String amount;

    

    public Integer getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public byte[] getPicture() {
        return picture;
    }
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public static Transaction create (ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setTransactionId(rs.getInt("transaction_id"));
        t.setCategory(rs.getString("category"));
        t.setDescription(rs.getString("description"));
        t.setPicture(rs.getBytes("picture"));
        t.setAmount(rs.getString("amount"));
        return t;
    }


    public static Transaction create (String category, String description, MultipartFile picture, String amount, String username) throws IOException {
        Transaction t = new Transaction();
        t.setCategory(category);
        t.setDescription(description);
        t.setAmount(amount);
        t.setPicture(picture.getBytes());
        return t;
    }

    public static Transaction create (String category, String description, String amount, String username) throws IOException {
        Transaction t = new Transaction();
        t.setCategory(category);
        t.setDescription(description);
        t.setAmount(amount);
        return t;
    }

    public static Transaction create (String category, MultipartFile picture, String amount, String username) throws IOException {
        Transaction t = new Transaction();
        t.setCategory(category);
        t.setAmount(amount);
        t.setPicture(picture.getBytes());
        return t;
    }

    public static Transaction create (String category, String amount, String username) throws IOException {
        Transaction t = new Transaction();
        t.setCategory(category);
        t.setAmount(amount);
        return t;
    }
    
    
}
