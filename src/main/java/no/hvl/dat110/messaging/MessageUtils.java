package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	public static byte[] encapsulate(byte[] payload) {

		if (payload == null) {
			throw new IllegalArgumentException("Payload cannot be null");
		}
		if (payload.length > 127) {
			throw new IllegalArgumentException("Payload too long (max 127 bytes)");
		}
	
		byte[] segment = new byte[SEGMENTSIZE];

	
		// header = payload length (0..127)
		segment[0] = (byte) payload.length;
	
		// copy payload into bytes 1..payload.length
		System.arraycopy(payload, 0, segment, 1, payload.length);
	
		// remaining bytes are already 0 (padding)
		return segment;
	}
	
	public static byte[] decapsulate(byte[] segment) {
	
		if (segment == null) {
			throw new IllegalArgumentException("Segment cannot be null");
		}
		if (segment.length != SEGMENTSIZE)
			{
			throw new IllegalArgumentException("Segment must be exactly 128 bytes");
		}
	
		// interpret first byte as unsigned 0..255, but valid should be 0..127
		int len = segment[0] & 0xFF;
	
		if (len > 127) {
			throw new IllegalArgumentException("Invalid payload length in segment header: " + len);
		}
	
		byte[] payload = new byte[len];
		System.arraycopy(segment, 1, payload, 0, len);
	
		return payload;
	}
	
	
}
