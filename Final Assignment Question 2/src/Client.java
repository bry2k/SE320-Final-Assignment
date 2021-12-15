import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame implements ActionListener {
  // Text field for receiving weight and height (separated by a space).
  private JTextField jtf = new JTextField();

  // Text area to display contents
  private JTextArea jta = new JTextArea();

  // IO streams
  private DataOutputStream outputToServer;
  private DataInputStream inputFromServer;

  public static void main(String[] args) {
    new Client();
  }

  public Client() {
    // Panel p to hold the label and text field
    JPanel p = new JPanel();
    p.setLayout(new BorderLayout());
    p.add(new JLabel("Enter weight in kilograms followed by height in meters"), BorderLayout.WEST);
    p.add(jtf, BorderLayout.CENTER);
    jtf.setHorizontalAlignment(JTextField.RIGHT);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(p, BorderLayout.NORTH);
    getContentPane().add(new JScrollPane(jta), BorderLayout.CENTER);

    jtf.addActionListener(this); // Register listener

    setTitle("Client");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!

    try {
      // Create a socket to connect to the server
      Socket socket = new Socket("localhost", 8000);
      // Socket socket = new Socket("130.254.204.36", 8000);
      // Socket socket = new Socket("drake.Armstrong.edu", 8000);

      // Create an input stream to receive data from the server
      inputFromServer = new DataInputStream(
        socket.getInputStream());

      // Create an output stream to send data to the server
      outputToServer =
        new DataOutputStream(socket.getOutputStream());
    }
    catch (IOException ex) {
      jta.append(ex.toString() + '\n');
    }
  }

  public void actionPerformed(ActionEvent e) {
     String actionCommand = e.getActionCommand();
    if (e.getSource() instanceof JTextField) {
      try {
        // Get the radius from the text field
        String weightAndHeightString = (jtf.getText().trim());
        String weightString = weightAndHeightString.substring(0, weightAndHeightString.indexOf(' '));
        String heightString = weightAndHeightString.substring(weightAndHeightString.indexOf(' '));
        double weight = Double.parseDouble(weightString);
        double height = Double.parseDouble(heightString);
        // Send the weight and height to the server
        outputToServer.writeDouble(weight);
        outputToServer.writeDouble(height);
        outputToServer.flush();

        // Get BMI from the server
        double area = inputFromServer.readDouble();

        // Display to the text area
        jta.append("Weight is " + weight + ", Height is " + height + "\n");
        jta.append("BMI received from the server is "
          + area + '\n');
      }
      catch (IOException ex) {
        System.err.println(ex);
      }
    }
  }
}