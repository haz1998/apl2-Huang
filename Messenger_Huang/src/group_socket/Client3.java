package group_socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client3 {

		  public static void main(String[] args) throws IOException {
		        //Erstellen Sie einen Socket, der mit der angegebenen IP und dem angegebenen Port verbunden ist
		        Socket socket = new Socket("127.0.0.1",5200);
		        //Eingabedatenstrom abrufen
		        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		        PrintWriter out = new PrintWriter(socket.getOutputStream());
		        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		        //Erstellen Sie einen Thread, um Informationen vom Server zu lesen
		        new Thread(new Runnable() {
		            @Override
		            public void run() {
		                try {
		                	if(!Server.message.isEmpty()) {
		    		            for(String i : Server.message) {
		    		            	System.out.println(i);

		    		            }
		    	            }
		                    while (true){
		                        System.out.println(in.readLine());
		                    }
		                } catch (IOException e) {
		                    e.printStackTrace();
		                }
		            }
		        }).start();
		        //Schreiben Sie Informationen an den Kunden
		        String  line = reader.readLine();
		        if(line !=null) {
		        while (line!=null&&!"end".equalsIgnoreCase(line)){
		            out.println(line);
		            out.flush();
		            line = reader.readLine();
		        }
		        }
		        out.close();
		        in.close();
		        socket.close();
		 
		    }

}
