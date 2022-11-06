import java.util.Scanner;

import common.ChatIF;

public class ServerConsole implements ChatIF {
	final public static int DEFAULT_PORT = 5555;
	EchoServer server;
	
	Scanner console;
	
	public ServerConsole(int port) {
		server = new EchoServer(port,this);
		console = new Scanner(System.in);
	}
	 public void accept() {
		    try{
		    	String message;
		    	
		    	while(true) {
		    		message = console.nextLine();
		    		server.handleMessageFromServerUI(message);
		    	}
		    }
		    catch(Exception ex){
		    	System.out.println
		        ("Unexpected error while reading from console!");
		    }
		  }
	
	
	





	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		 System.out.println("> " + message);
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
