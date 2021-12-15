
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

 public class Server extends JFrame {
   // Text area for displaying contents
   private JTextArea jta = new JTextArea();
 
   public static void main(String[] args) {
     new Server();
   }
 
   public Server() {
     // Place text area on the frame
     getContentPane().setLayout(new BorderLayout());
     getContentPane().add(new JScrollPane(jta), BorderLayout.CENTER);
 
     setTitle("Server");
     setSize(500, 300);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setVisible(true); // It is necessary to show the frame here!
 
     try {
       // Create a server socket
       ServerSocket serverSocket = new ServerSocket(8000);
       jta.append("MultiThreadServer started at " + new Date() + '\n');
       int i = 1;	//it hurt me to type this but saying client 0 sounds weird.
       while (true) {
    	   Socket socket = serverSocket.accept();
    	   Thread thread = new ConnectionThread(socket, i);
    	   thread.start();
    	   jta.append("Thread for client " + i + " started at " + new Date() + '\n');
    	   i++;
       } 
      
     }
     catch(IOException ex) {
       System.err.println(ex);
     }
   }
}