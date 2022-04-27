import java.net.*;
import java.io.*;
import java.time.*;
import java.util.ArrayList;

public class Server {

    private static final int PORT = 6789;
   

    //main method
    public static void main(String[] args) throws Exception {
        //create server socket
        ServerSocket welcomeSocket = new ServerSocket(PORT); 
        //create log file, append if it exists
        FileWriter logFile = new FileWriter("log.txt", true);
        PrintWriter logWriter = new PrintWriter(logFile);
        logWriter.println(LocalDateTime.now() + " Server started");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //arraylist of threads
        ArrayList<Thread> threads = new ArrayList<Thread>();
        //main loop
        while(true) { 
            //close the server
            if(in.readLine().equals("quit")) {
                break;
            }
            //blocks program until a client connects successfully
            Socket connectionSocket = welcomeSocket.accept(); 
            logWriter.println(LocalDateTime.now() + " Client connected");
            System.out.println("Client connected");

            // try {
            //     //create a new thread for each client, run the calc method
            //     ServerHelper helper = new ServerHelper(connectionSocket, logWriter);
            //     Thread t = new Thread(helper);
            //     threads.add(t);
            //     t.start();
            // }
            // catch (Exception e) {
            //     System.out.println("Error: " + e.getMessage());
            //     break;
            // }
            ServerHelper helper = new ServerHelper(connectionSocket, logWriter);
            helper.run();
        }
        logWriter.println(LocalDateTime.now() + " Server stopped");
        logWriter.close();
        in.close();
        welcomeSocket.close(); 
    } 
    
}


