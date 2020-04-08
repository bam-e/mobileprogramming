import java.net.*;
import java.io.*;


public class MyServerDatagramSocket extends DatagramSocket {
	private static final int MAX_LEN = 100;
	private DatagramMessage request;
	
	MyServerDatagramSocket(int portNo) throws SocketException{
		super(portNo);
	}
	
	MyServerDatagramSocket(DatagramMessage request) throws SocketException{
		this.request = request;
	}
	
	public void sendMessage(String message) throws IOException {
		byte[ ] sendBuffer = message.getBytes( );
		//System.out.println(request.getAddress( ) + " " + request.getPort());
		DatagramPacket datagram = 
				new DatagramPacket(sendBuffer, sendBuffer.length, 
						request.getAddress( ),
						request.getPort( ));
		this.send(datagram);
	} // end sendMessage

	public String receiveMessage( )
			throws IOException {		
		byte[ ] receiveBuffer = new byte[MAX_LEN];
		DatagramPacket datagram =
				new DatagramPacket(receiveBuffer, MAX_LEN);
		this.receive(datagram);
		String message = new String(receiveBuffer);
		return message;
	} //end receiveMessage

	public DatagramMessage receiveMessageAndSender( )
			throws IOException {		
	         byte[ ] receiveBuffer = new byte[MAX_LEN];
	         DatagramPacket datagram =
	            new DatagramPacket(receiveBuffer, MAX_LEN);
	         this.receive(datagram);
	         DatagramMessage returnVal = new DatagramMessage( );
	         returnVal.putVal(new String(receiveBuffer),
	                          datagram.getAddress( ),
	                          datagram.getPort( ));
	         return returnVal;
	}
} //end class
