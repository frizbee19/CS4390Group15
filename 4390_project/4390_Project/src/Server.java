import java.util.*;
import java.net.*;
import java.io.*;

public class Server {

    private static final int PORT = 9876;
    // parse a string containing an expression and return the result
    // public static int eval(String expr) {
    //     int result = 0;
    //     Scanner scanner = new Scanner(expr);
    //     while(scanner.hasNext()) {
    //         String token = scanner.next();
    //         if (token.equals("+")) {
    //             result += scanner.nextInt();
    //         } 
    //         else if (token.equals("-")) {
    //             result -= scanner.nextInt();
    //         } 
    //         else if (token.equals("*")) {
    //             result *= scanner.nextInt();
    //         } 
    //         else if (token.equals("/")) {
    //             result /= scanner.nextInt();
    //         } 
    //         else {
    //             result = Integer.parseInt(token);
    //         }
    //     }
    //     return result;
    // }
        
    //

    //main method
    public static void main(String[] args) throws Exception {

        
        // try {
        //     //make the connection to client
        //     ServerSocket serverSocket = new ServerSocket(portNumber);
        //     Socket clientSocket = serverSocket.accept();
        //     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        //     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //     String input;
        //     String output;
            
        //     //read the input from client
        //     while ((input = in.readLine()) != null) {
        //         output = "" + eval(input);
        //         out.println(output);
        //         if (output.equals(""))
        //             break;
        //     }
        // } 
        // catch (Exception e) {
        //     System.out.println(e);
        // }
        // read the expression from the command line
        // Scanner kbReader = new Scanner(System.in);
        // while(true) {
        //     System.out.print("Enter an expression: ");
        //     String expr = kbReader.nextLine();
        //     if (expr.equals("")) {
        //         break;
        //     }
        //     System.out.println("The result is " + eval(expr));
        // }
    }

    public void connect() throws IOException{
        ServerSocket welcomeSocket = new ServerSocket(PORT); 
        ServerHelper helper;
    
            while(true) { 
    
                Socket connectionSocket = welcomeSocket.accept(); 

                try {
                    helper = new ServerHelper(connectionSocket);
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

