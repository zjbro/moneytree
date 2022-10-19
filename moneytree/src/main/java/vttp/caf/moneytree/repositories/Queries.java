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
        "insert into moneyflow (category, description, picture, amount, username) values (?, ?, ?, ?, ?)";

    public static final String SQL_SELECT_ALL_TRANSACTION_FROM_USERNAME =
        "select * from moneyflow where username = ?";
}
