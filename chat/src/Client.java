import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    Socket clientsocket;
    BufferedReader reader;
    PrintWriter writer;
    public Client(){
        try{
            System.out.println("Client starting...");
            clientsocket = new Socket("192.168.101.7", 6666);

            reader = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            writer = new PrintWriter(clientsocket.getOutputStream());

            startreading();
            startwriting();


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void startreading(){
        System.out.println("Reader started");
        Runnable r1=()->{

            try {
                while(!clientsocket.isClosed()){
                    String incoming_message = reader.readLine();
                    if( incoming_message.equals("exit")){
                        System.out.println("Server terminated the connection.");
                        clientsocket.close();
                        break;
                    }
                    System.out.println("Server :" + incoming_message);
                }
            }catch (Exception e){
                System.out.println("Conenction closed");
            }
        };
        new Thread(r1).start();

    }

    public void startwriting(){
        System.out.println("Writer started..");
        Runnable r2=()->{
            try{
                BufferedReader content = new BufferedReader(new InputStreamReader(System.in));

                while(!clientsocket.isClosed()){
                    String outgoing_message = content.readLine();
                    if( outgoing_message.equals("exit")){
                        clientsocket.close();
                        break;
                    }
                    writer.println(outgoing_message);
                    writer.flush();

                }
            }catch(Exception e){
                System.out.println("Connection closed");
                System.exit(0);
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("Client starting...");
        new Client();
    }
}
