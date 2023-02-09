import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public Actions actions;
    private int serverport;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Client(int serverport){
        this.serverport=serverport;
    }
    public boolean login(String username,String pwd){
        try(Socket socket=new Socket(InetAddress.getLocalHost(),serverport);
            var os = socket.getOutputStream();
            var oos = new ObjectOutputStream(os);

            var is = socket.getInputStream();
            var ois = new ObjectInputStream(is);){

            oos.writeObject(actions.LOGIN);
            oos.writeObject(username);
            oos.writeObject(pwd);
            Response response =(Response) ois.readObject();
            if(response== response.SUCCESS){
                return true;
            }else {
                return false;
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return false;
        }

    }
    public boolean Register(String Username,String pwd,String Lastname,String Firstname){
        try (Socket socket = new Socket(InetAddress.getLocalHost(),serverport);
             var os = socket.getOutputStream();
             var oos = new ObjectOutputStream(os);

             var is = socket.getInputStream();
             var ois = new ObjectInputStream(is);
        ){
            oos.writeObject(actions.REGISTER);
            oos.writeObject(Username);
            oos.writeObject(pwd);
            oos.writeObject(Lastname);
            oos.writeObject(Firstname);
            Response response =(Response) ois.readObject();
            if(response==response.SUCCESS){
                return true;
            }else {
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }






}

