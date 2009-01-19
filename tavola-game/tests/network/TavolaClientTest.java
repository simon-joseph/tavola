package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import junit.framework.Assert;
import junit.framework.TestCase;
import client.application.Client;
import client.protocol.CreateGameRequest;
import client.protocol.HelloRequest;
import data.network.ConnectionLostException;
import data.network.MessagesPipe;
import data.network.Pipe;
import data.network.RequestSendingException;

public class TavolaClientTest extends TestCase {

    private MessagesPipe pipe;

    private Socket socket = null;

    private PrintWriter out = null;

    private BufferedReader in = null;

    @Override
    protected void setUp() throws Exception {

	try {
	    socket = new Socket(Client.DEFAULT_HOST, Client.DEFAULT_PORT);
	    out = new PrintWriter(socket.getOutputStream(), true);
	    in = new BufferedReader(new InputStreamReader(socket
		    .getInputStream()));
	    // TavolaClient.setConnected(true);
	} catch (UnknownHostException e) {
	    System.err.println("Unknown host " + Client.DEFAULT_HOST);
	    System.exit(1);
	} catch (IOException e) {
	    System.err.println("Couldn't get I/O for the connection to: "
		    + Client.DEFAULT_HOST);
	    System.exit(1);
	}

	pipe = new MessagesPipe(new Pipe(in, out));
	pipe.readMessage(null); // VERSION xxx
    }

    @Override
    protected void tearDown() throws IOException {
	in.close();
	out.close();
	socket.close();
    }

    public void testHello() throws IOException, RequestSendingException,
	    ConnectionLostException {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String ticket = br.readLine();
	Assert.assertTrue(new HelloRequest(ticket).send(pipe));// kopytko*/
    }

    public void testCreateGame() throws IOException, RequestSendingException,
	    ConnectionLostException {
	testHello();
	Assert.assertTrue(new CreateGameRequest("testLevel", 4, 5,
		"emptyTheme", "snake").send(pipe) != null);
    }

    public void testJoinGame() throws IOException, RequestSendingException,
	    ConnectionLostException {
	testHello();
	// BufferedReader br = new BufferedReader(new
	// InputStreamReader(System.in));
	// new JoinGameMessage(br.readLine()).send(pipe);
    }

    public void testListGames() throws IOException, RequestSendingException,
	    ConnectionLostException {
	testHello();
	// new ListGameMessage().send(pipe);
    }

}
