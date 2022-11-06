// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import java.io.IOException;

import  OCSF.src.ocsf.server.*;
import common.ChatIF;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  ChatIF serverUI;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port, ChatIF serverUI) 
  {
    super(port);
    this.serverUI = serverUI;
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    serverUI.display("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }
  
  public void handleMessageFromServerUI(String message) {
	  try {
		  if(message.startsWith("#")) {
			  handleServerCommands(message);
		  }
		  else {
			  serverUI.display("SERVER MSG> "+message);
			  sendToAllClients("SERVER MSG> "+message);
		  }
	  }
	  catch(Exception e) {
		  serverUI.display("Could not send message to clients.");
	  }
  }
  
  private void handleServerCommands(String cmd) {
	  if(cmd.equals("#quit")) {
		  try {
			close();
		} catch (IOException e) {}
		  System.exit(0);
	  }
	  else if(cmd.equals("#stop")) {
		  stopListening();
	  }
	  else if(cmd.equals("#close")) {
		  try {
			  close();
		  }
		  catch(IOException e) {
			  System.exit(0);
		  }
	  }
	  else if(cmd.startsWith("#setport")) {
		  if(!isListening()) {
			  try {
				  int newPort = Integer.parseInt(cmd.substring(9,(cmd.length()-1)));
				  setPort(newPort);
			  }
			  catch(NumberFormatException e) {
				  serverUI.display("Invalid port number");
			  }
		  }
		  else {
			  serverUI.display("ERROR: cannot change port number while server is open");
		  }
	  }
	  else if(cmd.equals("#start")) {
		  if(!isListening()) {
			  try {
			      listen(); //Start listening for connections
			    } 
			    catch (Exception ex) {
			      serverUI.display("ERROR - Could not listen for clients!");;
			    }
		  }
		  else {
			  serverUI.display("Server is already listening for clients");
		  }
	  }
	  else if(cmd.equals("#getport")) {
		  serverUI.display(String.valueOf(getPort()));
	  }
	  else {
		  serverUI.display("Unknown command. Try again!");
	  }
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    serverUI.display("Server listening for connections on port " + getPort());
      
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
	  serverUI.display("Server has stopped listening for connections.");
  }
  protected void clientConnected(ConnectionToClient client) {
	  serverUI.display("Client is connected");
  }
  
  synchronized protected void clientDisconnected(
		    ConnectionToClient client) {
	  serverUI.display("Client is disconnected");
	  
  }

  
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  
}
//End of EchoServer class
