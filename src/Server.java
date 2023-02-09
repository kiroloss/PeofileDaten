import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class Server {
    private int port;
    private Connection dbconnection;

    public Server(int port ,Connection dbconnection){
        this.port=port;
        this.dbconnection=dbconnection;
    }

    public void service(Socket socket , Connection dbconnection){
        try {
            ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream=new ObjectOutputStream((socket.getOutputStream()));
            Actions action = (Actions) inputStream.readObject();
            String username = (String) inputStream.readObject();
            String pass =(String) inputStream.readObject();

            if(action == action.LOGIN){
                if(DBHelper.checkentryexist(dbconnection,DBHelper.username)){
                    String storedpass= DBHelper.getpassword(dbconnection,DBHelper.username);
                    if(storedpass.equals(pass)){
                    outputStream.writeObject(Response.SUCCESS);
                    }else{
                        outputStream.writeObject(Response.FAILURE);
                    }
                }else {
                    outputStream.writeObject(Response.FAILURE);
                }
            }else if(action==action.REGISTER){
                String firstname= (String) inputStream.readObject();
                String lastname=(String) inputStream.readObject();
                if (DBHelper.checkentryexist(dbconnection,username)){
                    outputStream.writeObject(Response.FAILURE);
                }else {
                    DBHelper.addEntry(dbconnection,username,pass,lastname,firstname);
                    outputStream.writeObject(Response.SUCCESS);
                }
            }
        }catch (IOException | ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
