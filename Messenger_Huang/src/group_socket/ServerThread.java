package group_socket;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Server implements Runnable {

	    Socket socket;
	    String socketName;
	 
	    public ServerThread(Socket socket){
	        this.socket = socket;
	    }
	    @Override
	    public void run() {
	        try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            
	            //Legen Sie die Endpunktadresse des Clients fest
	            socketName = socket.getRemoteSocketAddress().toString();
	            System.out.println("Client@"+socketName+"join the chatroom");
	            print("Client@"+socketName+"join the chatroom");
	            
	            if(!message.isEmpty()) {
		            for(String i : message) {
			            printSocket(socket,i);

		            }
	            }
	            boolean flag = true;
	            while (flag)
	            {
	                //Blockieren, warten auf den Ausgabestrom des Clients
	                String line = reader.readLine();
	                //Wenn der Client beendet wird, beendet er die Verbindung.
	                if (line == null){
	                    flag = false;
	                    continue;
	                }else {
	                String msg = "Client@"+socketName+":"+line;
	                message.add(msg);
	                System.out.println(msg);
	                //Informationen an Online-Client ausgeben
	                print(msg);
	                }
	            }
	            closeConnect();
	        } catch (IOException e) {
	            try {
	                closeConnect();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }
	    }
	    /**
	     * Nachrichten an alle Online-Client-Sockets weiterleiten
	     * @param msg,Messange 
	     * @throws IOException
	     */
	    private void print(String msg) throws IOException {
	        PrintWriter out = null;
	        synchronized (sockets){
	        for (Socket sc : sockets){
	            out = new PrintWriter(sc.getOutputStream());
	            out.println(msg);
	            out.flush();
	        }
	    }
	    }
	    /**
	     * Nachrichten an einen separaten Client-Socket weiterleiten
	     * @param s, msg s bedeutet Socket,die Server will allein etwas sagen. 
	     * @throws IOException
	     */
	    private void printSocket(Socket s,String msg) throws IOException {//Asynchronize Methode.Wenn Server verbinden,kann alle Informationen,in dieser Server,wissen.
	        PrintWriter out = null;
	            out = new PrintWriter(s.getOutputStream());
	            out.println(msg);
	            out.flush();

	    
	    }
	    /**
	     * Schließen Sie die Steckdosenverbindung
	     * @throws IOException
	     */
	    public void closeConnect() throws IOException {
	        System.out.println("Client@"+socketName+"exit the chatroom");
	        print("Client@"+socketName+"exit the chatroom");
	        synchronized (sockets){
	            sockets.remove(socket);
	        }
	        socket.close();
	    }

}
