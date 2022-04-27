import java.io.*;
import java.net.*;

class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
	int port = 2300;

	// Scanner sc = new Scanner(System.in);

	try {
	    Socket socket = new Socket("cs1.utdallas.edu", port);
		

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 

	    DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

	    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//enter name
		String response = inFromServer.readLine();
		System.out.println(response);
		//client enters name
		String sentence = inFromUser.readLine();
		System.out.println("SENT: " + sentence);
		//send name to server
		outToServer.writeBytes(sentence + "\r\n");
		System.out.println("SENT: " + sentence);
		//connection message from server
		response = inFromServer.readLine();
		System.out.println(sentence);
		//prompt for expression
		System.out.println("Enter an expression (or enter quit to exit): ");
		//read expression from client
		sentence = inFromUser.readLine();
		//send expression to server
		outToServer.writeBytes(sentence + "\r\n");
		while (!sentence.equals("quit")) {

		    response = inFromServer.readLine();

		    System.out.println(sentence);

			System.out.println("Enter an expression (or enter quit to exit): ");
			//read expression from client
			sentence = inFromUser.readLine();
			//send expression to server
			outToServer.writeBytes(sentence + "\r\n");
		}

		socket.close();
	    // Thread sendMessage = new Thread(new Runnable() {
		// @Override
		// public void run() {
		//     while (true) {
		// 	String line = sc.nextLine();
		// 	if(line != null) {
		// 	    out.println(line);
		// 	    out.flush();

		// 	    if(line.equals("stop"))
		// 		return;
		// 	}
		//     }
		// }
	    // });

	    // Thread readMessage = new Thread(new Runnable() {
		// @Override
		// public void run() {
		//     while(true) {
		// 	try {
		// 	    String line = in.readLine();
			    
		// 	    if(line.equals("quit"))
		// 		return;
		// 	    else if(line != null)
		// 	        System.out.println(line);
		// 	} catch (IOException e) {
		// 	    e.printStackTrace();
		// 	}
		//     }
		// }
	    // });

	    // sendMessage.start();
	    // readMessage.start();

	    // if(sendMessage.isAlive() == false && readMessage.isAlive() == false) {
		// sc.close();
	    // }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
