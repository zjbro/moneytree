package vttp.caf.moneytree.models;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.multipart.MultipartFile;


import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Transaction {

    private Integer transactionId;
    private String category;
    private String description;
    private byte[] picture;
    private String amount;
    private String date;

    

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
        t.setDate(rs.getString("date"));
        return t;
    }

    public static RowMapper rowMapper = (rs, rowNum) -> {
        Transaction t = new Transaction();
        t.setTransactionId(rs.getInt("transaction_id"));
        t.setCategory(rs.getString("category"));
        t.setDescription(rs.getString("description"));
        t.setPicture(rs.getBytes("picture"));
        t.setAmount(rs.getString("amount"));
        t.setDate(rs.getString("date_added"));
        return t;       
    };


    public static Transaction create (String category, String description, MultipartFile picture, String amount, Integer transactionId, String date) throws IOException {
        Transaction t = new Transaction();
        t.setCategory(category);
        t.setDescription(description);
        t.setAmount(amount);
        t.setPicture(picture.getBytes());
        t.setDate(date);
        t.setTransactionId(transactionId);
        return t;
    }

    public static Transaction create (String category, String description, String amount, Integer transactionId, String date) throws IOException {
        Transaction t = new Transaction();
        t.setCategory(category);
        t.setDescription(description);
        t.setAmount(amount);
        t.setDate(date);
        t.setTransactionId(transactionId);
        return t;
    }

    public static Transaction create (String category, MultipartFile picture, String amount, Integer transactionId, String date) throws IOException {
        Transaction t = new Transaction();
        t.setCategory(category);
        t.setAmount(amount);
        t.setPicture(picture.getBytes());
        t.setDate(date);
        t.setTransactionId(transactionId);
        return t;
    }

    public static Transaction create (String category, String amount, Integer transactionId, String date) throws IOException {
        Transaction t = new Transaction();
        t.setCategory(category);
        t.setAmount(amount);
        t.setDate(date);
        t.setTransactionId(transactionId);
        return t;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
        .add("transactionId", transactionId)
        .add("category", category)
        .add("description", (description == null) ? "" : description)
        .add("picture", (picture == null) ? "" : Base64.getEncoder().encodeToString(picture) )
        .add("amount", (amount == null) ? "" : amount)
        .add("date", date)
        .build();
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public static RowMapper getRowMapper() {
        return rowMapper;
    }
    public static void setRowMapper(RowMapper rowMapper) {
        Transaction.rowMapper = rowMapper;
    }


    public static Transaction create (String category, String description, MultipartFile picture, String amount, String date) throws IOException {
        Transaction t = new Transaction();
        t.setCategory(category);
        t.setDescription(description);
        t.setAmount(amount);
        t.setPicture(picture.getBytes());
        t.setDate(date);
        return t;
    }

    public static Transaction create (String category, String description, String amount, String date) throws IOException {
        Transaction t = new Transaction();
        t.setCategory(category);
        t.setDescription(description);
        t.setAmount(amount);
        t.setDate(date);
        return t;
    }

    public static Transaction create (String category, MultipartFile picture, String amount, String date) throws IOException {
        Transaction t = new Transaction();
        t.setCategory(category);
        t.setAmount(amount);
        t.setPicture(picture.getBytes());
        t.setDate(date);
        return t;
    }

    public static Transaction create (String category, String amount, String date) throws IOException {
        Transaction t = new Transaction();
        t.setCategory(category);
        t.setAmount(amount);
        t.setDate(date);
        return t;
    }
    
    
}
