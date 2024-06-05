import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
public class Server {
    ServerSocket server;
    Socket clientsocket;
    BufferedReader reader;
    PrintWriter writer;
    public Server(){
        try {
            server = new ServerSocket(6666);
            System.out.println("Server ready...");
            System.out.println("Waiting.......");
            clientsocket = server.accept(); //clientsocket ma chai client ko yeauta instance basxa
                                            //accept() le connection establish garxa

            //to read
            reader = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            // getInputStream le byte ma data tanxa
            //inputstreamreader le byte lai character ma convert garxa;
            //bufferedreader le buffer capablities dinxa

            //to write
            writer = new PrintWriter(clientsocket.getOutputStream());

            startreading();
            startwriting();

        }catch (Exception e){
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
                        System.out.println("Client terminated the connection.");
                        clientsocket.close();
                        break;
                    }
                    System.out.println("Client :" + incoming_message);
                }
            }catch (Exception e){
                System.out.println("Connection is terminated");
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
    public static void main(String[] args){
        System.out.println("Server starting...");
        new Server();
    }
}
