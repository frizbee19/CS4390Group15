import java.io.*;
import java.net.*;
import java.util.*;

class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
	int port = 9876;
	
        ServerSocket start = new ServerSocket(port); 

	Scanner sc = new Scanner(System.in);

	try {
	    Socket socket = new Socket("127.0.0.1", port);

	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	    Thread sendMessage = new Thread(new Runnable() {
		@Override
		public void run() {
		    while (true) {
			String line = sc.nextLine();
			if(line != null) {
			    out.println(line);
			    out.flush();

			    if(line.equals("stop"))
				return;
			}
		    }
		}
	    });

	    Thread readMessage = new Thread(new Runnable() {
		@Override
		public void run() {
		    while(true) {
			try {
			    String line = in.readLine();
			    
			    if(line.equals("quit"))
				return;
			    else if(line != null)
			        System.out.println(line);
			} catch (IOException e) {
			    e.printStackTrace();
			}
		    }
		}
	    });

	    sendMessage.start();
	    readMessage.start();

	    if(sendMessage.isAlive() == false && readMessage.isAlive() == false) {
		sc.close();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
