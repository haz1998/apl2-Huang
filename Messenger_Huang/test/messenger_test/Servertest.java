package messenger_test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import group_socket.Client;
import group_socket.Server;

public class Servertest {
	
    @Test
	public void Servertest() throws IOException{
		final Client client = new Client();
		client.startClient();
		assertTrue(Server.getSockets().contains(client.getSocket()));
		 
	}

}
