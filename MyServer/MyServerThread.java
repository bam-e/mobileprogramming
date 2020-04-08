import java.io.*;

class MyServerThread implements Runnable {
	private DatagramMessage request;
	MyServerThread(DatagramMessage request) {
		this.request = request;
	}

	public void run( ) {
		try {
			Service.initializeVector();			
			String[] reqsplit = request.getMessage().trim().split(",");
			
			if (reqsplit[0].equals ("add")){
				Service.addMember(reqsplit, request);		
			}
		
			if (reqsplit[0].equals ("del")){
				Service.delMember(reqsplit, request);
			}
			
			if (reqsplit[0].equals ("find")){
				Service.findMember(reqsplit[1], request);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
