import java.io.*;
import java.util.Vector;

public class MyServer {
	public static void main(String[] args) throws IOException {
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);
		System.out.println("What is the port number of the server");
        String port = br.readLine();
		int serverPort = Integer.parseInt(port);
		try {
			MyServerDatagramSocket myConnectionSocket = new MyServerDatagramSocket(serverPort); 
			System.out.println("Server ready.");  
			while (true) {
				DatagramMessage request = myConnectionSocket.receiveMessageAndSender();
				Thread theThread = new Thread(new MyServerThread(request));
				theThread.start();
			} //end while
		} // end try
		catch (Exception ex) {
			ex.printStackTrace( );
		} // end catch
	} //end main
} // end class      
