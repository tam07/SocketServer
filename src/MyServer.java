import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;




public class MyServer {
	public static final String hostname = "localhost";
	public static final int portNum = 4444;
	
	ServerSocket serverSocket;
	BufferedReader serverReader;
	
	File serverLog;
	FileWriter fw;
	BufferedWriter serverWriter;
	
    Socket clientSocket;
    
    public static void main(String[] args) {
		MyServer server = new MyServer(portNum);
		// start the server so it can listen to client messages
		server.start();
		
		
	}
    
    
    public MyServer(int portNum) {
    	
    	try {
    		// endpt for server side, used to listen for client socket
    	    serverSocket = new ServerSocket(portNum);
    	    
    	    /* have server socket listen for connection, return client socket.
    	     * serverSocket can now talk to clientSocket
    	     */
    	    clientSocket = serverSocket.accept();
    	    
    	    // server writes messages to this log
    	    serverLog = new File("ServerLog.txt");
    	    if(!serverLog.exists())
    	    	serverLog.createNewFile();
    	    fw = new FileWriter(serverLog.getAbsoluteFile());
    	    serverWriter = new BufferedWriter(fw);
    	    
    	    /* server reads from this stream that is populated by the client's
    	     * OUTPUT stream/client socket's INPUT stream
    	     */
    	    serverReader = new BufferedReader(
    	        new InputStreamReader(clientSocket.getInputStream())
    	                                          );
        }    
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void start() {
    	String clientMsg;
    	try {
			while((clientMsg = serverReader.readLine()) != null) {
				if(clientMsg.startsWith("exit")) {
					break;
				}
				serverWriter.append(clientMsg);
				System.out.println(clientMsg);
			}
			serverWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
