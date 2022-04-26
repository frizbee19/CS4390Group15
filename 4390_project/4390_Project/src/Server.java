import java.net.*;
import java.io.*;
import java.util.*;
import java.time.*;

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
        logWriter.println(LocalDateTime.now() + " Server started");
        //create arraylist of threads
        ArrayList<Thread> threads = new ArrayList<Thread>();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //main loop
        while(true) { 
            //close the server
            if(in.readLine().equals("quit")) {
                break;
            }
            //blocks program until a client connects successfully
            Socket connectionSocket = welcomeSocket.accept(); 

            try {
                //create a new thread for each client, run the calc method
                ServerHelper helper = new ServerHelper(connectionSocket, logWriter);
                Thread t = new Thread(helper);
                t.start();
                threads.add(t);
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                break;
            }
        }
        logWriter.println(LocalDateTime.now() + " Server stopped");
        logWriter.close();
        in.close();
        welcomeSocket.close(); 
    } 
    
}


