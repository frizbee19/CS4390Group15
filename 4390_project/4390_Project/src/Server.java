import java.net.*;
import java.io.*;

public class Server {

    private static final int PORT = 9876;

    //main method
    public static void main(String[] args) throws Exception {
        //create server socket
        ServerSocket welcomeSocket = new ServerSocket(PORT); 
        //create log file, append if it exists
        File log = new File("log.txt");
        log.createNewFile();
        PrintWriter logWriter = new PrintWriter(log);
        logWriter.println("Server started");
        //main loop
        while(true) { 
            //blocks program until a client connects successfully
            Socket connectionSocket = welcomeSocket.accept(); 

            try {
                //create a new thread for each client, run the calc method
                ServerHelper helper = new ServerHelper(connectionSocket, logWriter);
                new Thread(helper).start();
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                break;
            }
        }
        welcomeSocket.close(); 
    } 
    
}


