
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jpcap.JpcapCaptor;
import jpcap.JpcapWriter;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.Packet;

public class Test {

	static NetworkInterface[] interfaces;
	private static JpcapCaptor captor;

	public static void main(String[] args) {

		// /****************************************/
		// lista dostepnych interfejsow
		interfaces = JpcapCaptor.getDeviceList();

		System.out.println("Available Interfaces: ");
		for (int i = 0; i < interfaces.length; i++) {
			System.out.println(i + " -> " + interfaces[i].datalink_name + " ("
					+ interfaces[i].description + ")");

			// print out its datalink name and description
			System.out.println(" datalink: " + interfaces[i].datalink_name
					+ "(" + interfaces[i].datalink_description + ")");

			// print MAC address
			System.out.print(" MAC addres: ");
			for (byte b : interfaces[i].mac_address) {
				System.out.print(Integer.toHexString(b & 0xff) + ":");
			}
			System.out.println();

			// print out its IP address, subnet mask and broadcast address
			for (NetworkInterfaceAddress a : interfaces[i].addresses) {
				System.out.println(" address:" + a.address + " " + a.subnet
						+ " " + a.broadcast);
			}
		}
		// /******************************************/

		int currentChoice = 1;

		try {
			// chose which network interface to captuer packets from, open the
			// inteface
			// Arguments: 1) NetworkInterface interface - network interface that
			// you whant to open,
			// 2) int snaplen - max number of bytes to capture at once,
			// 3) boolean promics - true if you whant to open the interface in
			// promiscuous mode, otherwise false,
			// in promiscuous mode, you can capture packes every packet from
			// wire, in non-promicuous mode, you can only capture packets send
			// and recived by your host
			// 4) int to_ms - set a capture timeoutvalue in miliseconds
			captor = JpcapCaptor.openDevice(interfaces[currentChoice], 65535,
					false, 20);
			
			//set a filter to only capture TCP/IPv4 packets
			captor.setFilter("ip and tcp", true);
			
			//call processPacket() to let Jpcap call PacketPrinter.receivePacket() for every packet capture.
			captor.processPacket(10,new PacketPrinter());
			
			//open a file to save captured packets
			JpcapWriter writer=JpcapWriter.openDumpFile(captor,"CapturedPacket");

			for(int i=0;i<10;i++){
				  //capture a single packet
				  Packet packet=captor.getPacket();
				  //save it into the opened file
				  writer.writePacket(packet);
				}
				writer.close();
				
			captor.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

//		/* start listeninf for packets */
//		while (true) {
//			Packet info = captor.getPacket();
//			if (info != null) {
//				System.out.println(info);
//			}
//
//		}
//		captor.close();
	}

	/* get user input */
	public static String getInput(String g) {

		String input = "";
		System.out.println(g);
		BufferedReader bufferedreader = new BufferedReader(
				new InputStreamReader(System.in));
		try {
			input = bufferedreader.readLine();
		} catch (IOException e) {
			System.err.println(e);
		}
		return input;
	}

	/* return packet data in true text */
	String getPacketText(Packet pack) {
		int i = 0, j = 0;
		byte[] bytes = new byte[pack.header.length + pack.data.length];

		System.arraycopy(pack.header, 0, bytes, 0, pack.header.length);
		System.arraycopy(pack.data, 0, bytes, pack.header.length,
				pack.data.length);
		StringBuffer buffer = new StringBuffer();

		for (i = 0; i < bytes.length;) {
			for (j = 0; j < 8 && i < bytes.length; j++, i++) {
				String d = Integer.toHexString((int) (bytes[i] & 0xff));
				buffer.append((d.length() == 1 ? "0" + d : d) + " ");

				if (bytes[i] < 32 || bytes[i] > 126)
					bytes[i] = 46;
			}
		}
		return new String(bytes, i - j, j);
	}
}
