import java.sql.*;

public class DBHelper {
 private static final String db_name="users.db";
    public static String username;
    // creating the table using SQl Quries
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS Users " +
            "( ID int NOT NULL, " +
            "Username VARCHAR(255)"+
            "Password VARCHAR(255)"+
            "Lastname VARCHAR(255) , " +
            "Firstname VARCHAR(255)," +
             "PRIMARY KEY(ID)";

    private static final String ADD_ENTRY_SQL = "INSERT INTO Users (Username,Password,Lastname, Firstname) VALUES (?, ?,?,?)";

    private static final String CHECK_ENTRY_SQL = "SELECT * FROM Users WHERE username = ? ";

    private static final String GET_PASSWORD = "SELECT password FROM Users WHERE username=?";




    public static void Create_table(Connection connection) throws SQLException{
        try (Statement statement = connection.createStatement()){
            statement.execute(CREATE_TABLE_SQL);
        }
    }
    public static void addEntry(Connection connection ,String username , String password,String lastname,String firstname) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_ENTRY_SQL)){
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,lastname);
            preparedStatement.setString(4,firstname);
            preparedStatement.executeUpdate();
        }
    }

    public static boolean checkentryexist (Connection connection,String username) throws SQLException{
        try(PreparedStatement preparedStatement=connection.prepareStatement(CHECK_ENTRY_SQL)) {
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean exists = resultSet.next();
            return exists;
        }
    }

  public static String getpassword (Connection connection , String username) throws SQLException {
        try (PreparedStatement preparedStatement= connection.prepareStatement(GET_PASSWORD)){
            preparedStatement.setString(1,username);
            ResultSet resultSet=preparedStatement.executeQuery();
            String pwd=null;
            if(resultSet.next()) {
                pwd = resultSet.getString("password");
            }
            return pwd;
        }
  }
}






