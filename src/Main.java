import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection connection =null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:dbname");
            DBHelper.Create_table(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
        Scanner User=new Scanner(System.in);
        System.out.println("Bitte geben sie Die Username");
        String username= User.nextLine();
        System.out.println("Bitte geben sie Die password");
        String Password= User.nextLine();
        System.out.println("Bitte geben sie Die lastname");
        String Lastname= User.nextLine();
        System.out.println("Bitte geben sie Die Firstname");
        String Firstname= User.nextLine();

        try {
            if(DBHelper.checkentryexist(connection,username)){
                System.out.println("DIE username Existieret schon");
            }else {
                DBHelper.addEntry(connection,username,Password,Lastname,Firstname);
                System.out.println("Registration successful");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }try {
            if(DBHelper.checkentryexist(connection,username)){
                String storedpAAS= DBHelper.getpassword(connection,username);
                        if(storedpAAS.equals(Password)){
                            System.out.println("login Successful");
                        }else {
                            System.out.println("wrong password");
                        }
            }else {
                System.out.println("user not exist");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
}