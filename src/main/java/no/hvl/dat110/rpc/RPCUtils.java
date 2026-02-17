package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RPCUtils {

	public static byte[] encapsulate(byte rpcid, byte[] payload) {

		if (payload == null) {
			payload = new byte[0];
		}

		byte[] rpcmsg = new byte[1 + payload.length];
		rpcmsg[0] = rpcid;
		System.arraycopy(payload, 0, rpcmsg, 1, payload.length);

		return rpcmsg;
	}

	// decapsulate payload (remove rpcid header)
	public static byte[] decapsulate(byte[] rpcmsg) {

		if (rpcmsg == null || rpcmsg.length < 1) {
			throw new IllegalArgumentException("RPC message is null/too short");
		}

		return Arrays.copyOfRange(rpcmsg, 1, rpcmsg.length);
	}

	// decapsulate payload AND verify rpcid
	public static byte[] decapsulate(byte rpcid, byte[] rpcmsg) {

		if (rpcmsg == null || rpcmsg.length < 1) {
			throw new IllegalArgumentException("RPC message is null/too short");
		}

		if (rpcmsg[0] != rpcid) {
			throw new IllegalArgumentException("Unexpected rpcid in reply. Expected " + rpcid + " but got " + rpcmsg[0]);
		}

		return Arrays.copyOfRange(rpcmsg, 1, rpcmsg.length);
	}

	// convert String to byte array
	public static byte[] marshallString(String str) {

		if (str == null) {
			throw new IllegalArgumentException("String cannot be null");
		}

		return str.getBytes(StandardCharsets.UTF_8);
	}

	// convert byte array to a String
	public static String unmarshallString(byte[] data) {

		if (data == null) {
			throw new IllegalArgumentException("Data cannot be null");
		}

		return new String(data, StandardCharsets.UTF_8);
	}

	public static byte[] marshallVoid() {
		return new byte[0];
	}

	public static void unmarshallVoid(byte[] data) {
		// for void there is nothing to decode; optionally verify empty
		if (data == null) {
			throw new IllegalArgumentException("Data cannot be null");
		}
		// allow empty array; ignore otherwise
	}

	// integer to byte array representation
	public static byte[] marshallInteger(int x) {

		return ByteBuffer.allocate(4).putInt(x).array();
	}

	// byte array representation to integer
	public static int unmarshallInteger(byte[] data) {

		if (data == null || data.length != 4) {
			throw new IllegalArgumentException("Integer must be 4 bytes");
		}

		return ByteBuffer.wrap(data).getInt();
	}

	// convert boolean to a byte array representation
	public static byte[] marshallBoolean(boolean b) {

    	byte[] encoded = new byte[1];
    	encoded[0] = (byte) (b ? 1 : 0);
    	return encoded;
	}

	// convert byte array to a boolean representation
	public static boolean unmarshallBoolean(byte[] data) {

	    if (data == null || data.length < 1) {
    	    throw new IllegalArgumentException("Boolean must be at least 1 byte");
    	}
	    return data[0] > 0;
	}

}
