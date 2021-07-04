package group_socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Server {
	 private static   List<Socket> sockets = new Vector<>();
	 protected static   ArrayList<String> message = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
	        ServerSocket server = new ServerSocket(5200);
	        boolean flag = true;
	        while (flag){
	            try {
	             //Blockieren, warten auf die Verbindung des Clients
	            Socket accept = server.accept();
	            synchronized (getSockets()){
	                getSockets().add(accept);
	            }
	            //Mehrere Server-Threads antworten dem Client
	            Thread thread = new Thread(new ServerThread(accept));
	            
	            thread.start();
	            }catch (Exception e){
	                flag = false;
	                e.printStackTrace();
	            }
	        }
	        server.close();
	    }

	public static List<Socket> getSockets() {
		return sockets;
	}

	public static void setSockets(List<Socket> sockets) {
		Server.sockets = sockets;
	}
}
