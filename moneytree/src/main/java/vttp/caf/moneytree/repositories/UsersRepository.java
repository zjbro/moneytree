package vttp.caf.moneytree.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.caf.moneytree.models.User;

import static vttp.caf.moneytree.repositories.Queries.*;

import java.util.Optional;

@Repository
public class UsersRepository {

    @Autowired
    private JdbcTemplate template;

    public int countUsersByUsernameAndPassword(String username, String password){
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_AND_COUNT_USERS_BY_USERNAME_AND_PASSWORD, username, password);
        if (!rs.next())
            return 0;
        return rs.getInt("user_count");
    }

    public int countUsersByUsername(String username){
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_AND_COUNT_USERS_BY_USERNAME, username);
        if (!rs.next())
            return 0;
        return rs.getInt("user_count");
    }

    public boolean addUser (String username, String password){
        Integer added = 0;
        Optional<String> optName = Optional.empty();
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ALL_BY_USERNAME, username);
        while (rs.next()){
            optName = Optional.of(rs.getString("username"));
        }
        if(optName.isEmpty()){
            added = template.update(SQL_INSERT_NEW_USER, username, password);
            return added > 0;
        } else {
            return added > 0;
        }
    }

    public Optional<User> getUser(String username){ 
        Optional<User> optUser = Optional.empty();
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ALL_BY_USERNAME, username);
        while (rs.next()){
            User user = new User(rs.getString("username"),rs.getString("password"));
            optUser = Optional.of(user);
        }
        return optUser;
    }




}
