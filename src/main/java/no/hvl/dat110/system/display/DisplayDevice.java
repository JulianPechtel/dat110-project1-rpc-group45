package no.hvl.dat110.system.display;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.system.controller.Common;


public class DisplayDevice {
	
	public static void main(String[] args) {
		
		System.out.println("Display server starting ...");
		
		// TODO - START
		// implement the operation of the display RPC server
		// see how this is done for the sensor RPC server in SensorDevice
				
		RPCServer rpcServer= new RPCServer(8081);

		DisplayImpl displayImpl = new DisplayImpl((byte)1, rpcServer);

		rpcServer.register((byte)1, displayImpl);
		rpcServer.run();


		
		System.out.println("Display server stopping ...");
		
	}
}
