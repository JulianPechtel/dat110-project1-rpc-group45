package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;


public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	// Encapsulate a Message into a fixed-size segment (128 bytes)
	public static byte[] encapsulate(Message message) {

		if (message == null) {
			throw new IllegalArgumentException("Message cannot be null");
		}

		return encapsulate(message.getData());
	}

	// Encapsulate payload bytes into a fixed-size segment (128 bytes)
	public static byte[] encapsulate(byte[] payload) {

		if (payload == null) {
			throw new IllegalArgumentException("Payload cannot be null");
		}
		if (payload.length > SEGMENTSIZE - 1) { // 127
			throw new IllegalArgumentException("Payload too long (max 127 bytes)");
		}

		byte[] segment = new byte[SEGMENTSIZE];
		segment[0] = (byte) payload.length;
		System.arraycopy(payload, 0, segment, 1, payload.length);

		return segment;
	}

	// Decapsulate a fixed-size segment into a Message
	public static Message decapsulate(byte[] segment) {

		byte[] payload = decapsulatePayload(segment);
		return new Message(payload);
	}

	// Helper: decapsulate into raw payload bytes
	public static byte[] decapsulatePayload(byte[] segment) {

		if (segment == null) {
			throw new IllegalArgumentException("Segment cannot be null");
		}
		if (segment.length != SEGMENTSIZE) {
			throw new IllegalArgumentException("Segment must be exactly 128 bytes");
		}

		int len = segment[0] & 0xFF;

		if (len > SEGMENTSIZE - 1) { // 127
			throw new IllegalArgumentException("Invalid payload length in segment header: " + len);
		}

		byte[] payload = new byte[len];
		System.arraycopy(segment, 1, payload, 0, len);

		return payload;
	}
}
