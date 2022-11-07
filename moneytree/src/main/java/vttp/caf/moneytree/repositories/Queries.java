package vttp.caf.moneytree.repositories;

public interface Queries {

    public static final String SQL_SELECT_ALL_BY_USERNAME = 
        "select * from users where username = ?";

    public static final String SQL_SELECT_AND_COUNT_USERS_BY_USERNAME_AND_PASSWORD = 
        "select count(*) as user_count from users where username = ? and password = ?";

    public static final String SQL_SELECT_AND_COUNT_USERS_BY_USERNAME = 
        "select count(*) as user_count from users where username = ?";

    public static final String SQL_INSERT_NEW_USER = 
        "insert into users (username, password) values (?, ?)";

    public static final String SQL_INSERT_NEW_TRANSACTION =
        "insert into transactions (category, description, picture, amount, user_id, date_added) values (?, ?, ?, ?, ?, ?)";

    public static final String SQL_SELECT_ALL_FROM_TRANSACTIONS_BY_USER_ID =
        "select * from transactions where user_id = ? ORDER BY date_added DESC";

    public static final String SQL_GET_USER_ID_FROM_USERNAME =
        "select * from users where username = ?";

    public static final String SQL_DELETE_TRANSACTION_FROM_TRANSACTIONID = 
        "delete from transactions where transaction_id = ?";

    public static final String SQL_UPDATE_TRASACTION =
        "update transactions set category = ?, description = ?, picture = ?, amount = ?, user_id = ?,date_added = ? where transaction_id = ?";

    public static final String SQL_DELETE_USER = 
        "delete from users where id = ?";
    
    public static final String SQL_DELETE_TRANSACTIONS_BY_USERID = 
        "delete from transactions where user_id = ?";

    public static final String SQL_DELETE_USER_FROM_USER_ROLES = 
        "delete from user_roles where user_id = ?";


}
