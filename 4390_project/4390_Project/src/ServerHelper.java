import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

public class ServerHelper implements Runnable {

    BufferedReader inFromClient;
    DataOutputStream outToClient;

    Socket connectionSocket;

    // constructor, takes a socket as a parameter from driver code
    public ServerHelper(Socket connSocket) throws Exception {
        connectionSocket = connSocket;
        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 

        outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 
    }

    // run method, runs when thread is started. Calculates expression and sends result back to client
    public void calc() {
        int result = 0;
        String expr = "";
        // read the expression from the client
        try {
            expr = inFromClient.readLine();
            
        } 
        catch (Exception e) {
            try {
                outToClient.writeBytes("Error: " + e.getMessage() + "\n");
            } 
            catch (IOException e1) {
                System.out.println("Error: " + e1.getMessage());
            }
        }
        // calculate the expression, repeat until client quits
        while(expr != null || expr != "" || expr != "quit") {
            //dijkstra's two-stack algorithm
            String tokens[] = expr.split(" ");
            Stack<Integer> nums = new Stack<Integer>();
            Stack<String> ops = new Stack<String>();
            //parse the expression
            for(int i = 0; i < tokens.length; i++) {
                if(tokens[i].equals("(")) {
                    ops.push(tokens[i]);
                }
                //when ")" is reached, backtrack until "(" is reached
                else if(tokens[i].equals(")")) {
                    while(!ops.peek().equals("(")) {
                        nums.push(eval(nums.pop(), nums.pop(), ops.pop()));
                    }
                    ops.pop();
                }
                //calculate operation
                else if(tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("*") || tokens[i].equals("/")) {
                    while(!ops.empty() && !ops.peek().equals("(") && precedence(ops.peek()) >= precedence(tokens[i])) {
                        nums.push(eval(nums.pop(), nums.pop(), ops.pop()));
                    }
                    ops.push(tokens[i]);
                }
                //parse number and add to the stack
                else {
                    nums.push(Integer.parseInt(tokens[i]));
                }
            }
            //backtrack to calculate remaining operations
            while(!ops.empty()) {
                nums.push(eval(nums.pop(), nums.pop(), ops.pop()));
            }
            //send result to client
            result = nums.pop();
            try {
                outToClient.writeBytes("The result is: " + result + "\r\n");
                
            } 
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            try {
                //read from client again
                expr = inFromClient.readLine();
                
            } 
            catch (Exception e) {
                try {
                    outToClient.writeBytes("Error: " + e.getMessage() + "\n");
                } 
                catch (IOException e1) {
                    System.out.println("Error: " + e1.getMessage());
                }
            }
        }
    }

    // helper method to calculate expression
    private int eval(int a, int b, String op) {
        if(op.equals("+")) {
            return a + b;
        }
        else if(op.equals("-")) {
            return a - b;
        }
        else if(op.equals("*")) {
            return a * b;
        }
        else if(op.equals("/")) {
            return a / b;
        }
        else {
            return 0;
        }
    }

    //helper method to determine precedence of operators
    private int precedence(String op) {
        if(op.equals("+") || op.equals("-")) {
            return 1;
        }
        else if(op.equals("*") || op.equals("/")) {
            return 2;
        }
        else {
            return 0;
        }
    }

    public void run() {
        calc();
    }
}
