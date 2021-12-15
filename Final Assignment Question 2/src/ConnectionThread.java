import java.io.*;
import java.net.*;
import java.util.*;
 
public class ConnectionThread extends Thread {
	
	private Socket socket;
	private int clientNumber;
	public ConnectionThread(Socket socket, int clientNumber)
	{
		this.socket = socket;
		this.clientNumber = clientNumber;
	}

	@Override
	public void run() {
		try {
			// Create data input and output streams
	       DataInputStream inputFromClient = new DataInputStream(
	         socket.getInputStream());
	       DataOutputStream outputToClient = new DataOutputStream(
	         socket.getOutputStream());
	 
	       while (true) {
	         // Receive weight and height from the client
	         double weight = inputFromClient.readDouble();
	         double height = inputFromClient.readDouble();
	         // Compute BMI
	         double bmi = weight / height / height;
	 
	         // Send BMI back to the client
	         outputToClient.writeDouble(bmi);
	       	}
		}
        catch(IOException ex) {
             System.err.println(ex);
        }
     }
}
	
