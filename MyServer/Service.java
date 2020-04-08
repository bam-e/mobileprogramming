import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Vector;

public class Service{
	private static Vector<ChatMember>  members = new Vector<ChatMember>();

	public static void initializeVector() throws NumberFormatException, UnknownHostException {
		ChatMember findmember = Find("multicast");
		if(findmember == null) {
			ChatMember member = new ChatMember("multicast","cannotchange",
					InetAddress.getByName("232.13.8.23"), Integer.parseInt("3456"));
			members.add(member);
		}
	}
	public static void addMember(String[] reqsplit, DatagramMessage request) throws IOException {
		MyServerDatagramSocket mySocket = new MyServerDatagramSocket(request);
		ChatMember findmember = Find(reqsplit[1]);
		if(findmember != null) {									//만약에 있으면 지우고 다시 설정
			if(pwHash(reqsplit[2]).equals(findmember.getHash())){	//비밀번호 확인
				if(findPort(reqsplit[3]) != null ) {
					System.out.println("port already in use=");
					mySocket.sendMessage("Port already in use=");
				}else if(Integer.parseInt(reqsplit[3]) > 65535){
					System.out.println("port number must be lower than 65536=");
					mySocket.sendMessage("Port # must be lower than 65536=");
				}else{
					members.remove(findmember);
					ChatMember member = new ChatMember(reqsplit[1],pwHash(reqsplit[2]),
							request.getAddress(), Integer.parseInt(reqsplit[3]));
					members.add(member);
					for(int i = 0; i < members.size(); i ++) {
						System.out.println(members.get(i));
					}
					mySocket.sendMessage("Member added=");
				}
			}else{
				System.out.println("pw incorrect=");
				mySocket.sendMessage("Password Incorrect=");
			}
		}else{
			if(findPort(reqsplit[3]) != null ) {
				System.out.println("port already in use=");
				mySocket.sendMessage("Port already in use=");
			}else{
				if(Integer.parseInt(reqsplit[3]) > 65535){
					System.out.println("port number must be lower than 65536=");
					mySocket.sendMessage("Port # must be lower than 65536=");
				}else {
					ChatMember member = new ChatMember(reqsplit[1],pwHash(reqsplit[2]),
							request.getAddress(), Integer.parseInt(reqsplit[3]));
					members.add(member);
					for(int i = 0; i < members.size(); i ++) {
						System.out.println(members.get(i));
					}
					mySocket.sendMessage("Member added=");
				}
			}
		}
	}

	public static void delMember(String[] reqsplit, DatagramMessage request) throws IOException {
		MyServerDatagramSocket mySocket = new MyServerDatagramSocket(request);
		ChatMember member = Find(reqsplit[1]);
		if(member != null) {
			if(pwHash(reqsplit[2]).equals(member.getHash())){
				members.remove(member);
				mySocket.sendMessage("Member deleted=");
				for(int i = 0; i < members.size(); i ++) {
					System.out.println(members.get(i));
				}
			}else{
				System.out.println("pw incorrect=");
				mySocket.sendMessage("Password Incorrect=");
			}
		}else{
			mySocket.sendMessage("Does not exist=");
			System.out.println("does not exist=");
		}
	}

	public static void findMember(String name, DatagramMessage request) throws IOException {
		MyServerDatagramSocket mySocket = new MyServerDatagramSocket(request);
		ChatMember member = Find(name);
		if(member != null) {
			mySocket.sendMessage(member.getAddress() + "&" + member.getPort() + "&" + member.getName() + "=");
			System.out.println("found");
		}else {
			mySocket.sendMessage("Does not exist=");
			System.out.println("does not exist=");
		}
	}

	public static ChatMember Find(String name){	
		for(ChatMember member : members){
			if(member.getName().equals(name)){
				return member;
			}
		}
		return null;
	}

	public static ChatMember findPort(String name){	
		for(ChatMember member : members){
			if(Integer.toString(member.getPort()).equals(name)){
				return member;
			}
		}
		return null;
	}

	public static String pwHash(String pw) {
		String sha1 = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(pw.getBytes("utf8"));
			sha1 = String.format("%064x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}
		return sha1;
	}
}

