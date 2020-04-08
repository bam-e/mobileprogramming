import java.net.InetAddress;

public class ChatMember {
	private String name;
	private InetAddress address;
	private int port;
	private String hash;
	
	public ChatMember(String name, String hash, InetAddress address, int portNum){
		this.name = name;
		this.hash = hash;
		this.address = address;
		this.port = portNum;
	}
	public String getName() {
		return this.name;
	}
	
	public InetAddress getAddress() {
		return this.address;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public String getHash() {
		return this.hash;
	}
	
	public String toString(){
		return String.format("이름:" + name + " hash:" + hash +  " 주소:" + address + " 포트넘버:" + port);		
	}
}