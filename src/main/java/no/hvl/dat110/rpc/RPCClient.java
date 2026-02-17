package no.hvl.dat110.rpc;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.*;

public class RPCClient {

	// underlying messaging client used for RPC communication
	private MessagingClient msgclient;

	// underlying messaging connection used for RPC communication
	private MessageConnection connection;
	
	public RPCClient(String server, int port) {
	
		msgclient = new MessagingClient(server,port);
	}
	
	public void connect() {

		connection = msgclient.connect();
	
		if (connection == null) {
			throw new RuntimeException("RPCClient.connect: could not establish messaging connection");
		}
	}
	
	
	public void disconnect() {

		if (connection != null) {
			connection.close();
			connection = null;
		}
	}
	

	/*
	 Make a remote call om the method on the RPC server by sending an RPC request message and receive an RPC reply message

	 rpcid is the identifier on the server side of the method to be called
	 param is the marshalled parameter of the method to be called
	 */

	 public byte[] call(byte rpcid, byte[] param) {

		if (connection == null) {
			throw new IllegalStateException("RPCClient.call: not connected");
		}
	
		// build request payload: [rpcid][param...]
		byte[] request = RPCUtils.encapsulate(rpcid, param);
	
		// send request
		connection.send(new Message(request));
	
		// receive reply
		Message replymsg = connection.receive();
		byte[] reply = replymsg.getData();
	
		// extract return value bytes from reply
		return RPCUtils.decapsulate(rpcid, reply);
	}
	

}
