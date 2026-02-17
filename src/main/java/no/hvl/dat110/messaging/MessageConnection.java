package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import no.hvl.dat110.TODO;


public class MessageConnection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection
	
	public MessageConnection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream (socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {

		byte[] data;
	
		data = message.getData();
		byte[] segment = MessageUtils.encapsulate(data);
	
		try {
			outStream.write(segment);
			outStream.flush();
		} catch (IOException ex) {
			System.out.println("MessageConnection.send: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public Message receive() {
	
		Message message = null;
		byte[] data;
	
		data = new byte[MessageUtils.SEGMENTSIZE];
	
		try {
			inStream.readFully(data);
			byte[] payload = MessageUtils.decapsulate(data);
			message = new Message(payload);
		} catch (IOException ex) {
			System.out.println("MessageConnection.receive: " + ex.getMessage());
			ex.printStackTrace();
		}
	
		return message;
	}
	

	// close the connection by closing streams and the underlying socket	
	public void close() {

		try {
			
			outStream.close();
			inStream.close();

			socket.close();
			
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}