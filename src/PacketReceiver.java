import jpcap.*;
import jpcap.NetworkInterface;

import jpcap.packet.DatalinkPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

class PacketReceiver  {

	static int i = 0;
	String protocoll[] = { "HOPOPT", "ICMP", "IGMP", "GGP", "IPV4", "ST",
			"TCP", "CBT", "EGP", "IGP", "BBN", "NV2", "PUP", "ARGUS", "EMCON",
			"XNET", "CHAOS", "UDP", "mux" };

	public static void main(String str[]) throws Exception {
		// lista dostepnych interfejsow
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();

		for (int i = 0; i < devices.length; i++) {
			// print out its name and description
			System.out.println(i + " :" + devices[i].name + "("
					+ devices[i].description + ")");

			// print out its datalink name and description
			System.out.println("    data link:" + devices[i].datalink_name
					+ "(" + devices[i].datalink_description + ")");

			// print out its MAC address
			System.out.print("    MAC address: ");
			for (byte b : devices[i].mac_address) {
				System.out.print(Integer.toHexString(b & 0xff) + ":");
			}
			System.out.println();

			// print out its IP address, subnet mask and broadcast address
			for (NetworkInterfaceAddress a : devices[i].addresses) {
				System.out.println("    address:" + a.address + " " + a.subnet
						+ " " + a.broadcast);
			}
		}

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
		JpcapCaptor jpcap = JpcapCaptor.openDevice(devices[1], 2000, true, 20);
		
		// -1 -continue capturing packets infinitely
				jpcap.loopPacket(-1, new PacketPrinter());   
				
//		//open a file to save captured packets
//		JpcapWriter writer=JpcapWriter.openDumpFile(jpcap,"d:\\Programy\\eclipseEE\\wokspace\\Sniffer\\packets.txt");
//		
//		for(int i=0;i<10;i++){
//			  //capture a single packet
//			  Packet packet=jpcap.getPacket();
//			  //save it into the opened file
//			  writer.writePacket(packet);
//			}
//			writer.close();
	}

//	public void receivePacket(Packet packet) {
//		System.out.println(packet + "\n");
//		System.out.println("this is packet " + i + " :" + "\n");
//		i++;
//
//		IPPacket tpt = (IPPacket) packet;
//		if (packet != null) {
//
//			int ppp = tpt.protocol;
//			String proto = protocoll[ppp];
//			System.out.println("about the ip packet in network layer : \n");
//			System.out
//					.println("******************************************************************");
//			if (tpt.dont_frag) {
//				System.out
//						.println("dft bi is set. packet will not be fragmented \n");
//
//			} else {
//				System.out
//						.println("dft bi is not set. packet will  be fragmented \n");
//			}
//			System.out.println(" \n destination ip is :" + tpt.dst_ip);
//			System.out.println("\n this is source ip :" + tpt.src_ip);
//			System.out.println("\n this is hop limit :" + tpt.hop_limit);
//			System.out.println(" \n this is identification field  :"
//					+ tpt.ident);
//			System.out.println(" \npacket length :" + tpt.length);
//			System.out.println("\n packet priority  :" + (int) tpt.priority);
//			System.out.println("type of service field" + tpt.rsv_tos);
//			if (tpt.r_flag) {
//				System.out.println("releiable transmission");
//			} else {
//				System.out.println("not reliable");
//			}
//			System.out.println("protocol version is : " + (int) tpt.version);
//			System.out.println("flow label field" + tpt.flow_label);
//
//			System.out
//					.println("**********************************************************************");
//
//			System.out.println("datalink lavel analysis");
//			System.out
//					.println("********************************************************************");
//			DatalinkPacket dp = packet.datalink;
//
//			EthernetPacket ept = (EthernetPacket) dp;
//			System.out.println("this is destination mac address :"
//					+ ept.getDestinationAddress());
//			System.out.println("\n this is source mac address"
//					+ ept.getSourceAddress());
//
//			System.out
//					.println("*********************************************************************");
//			System.out.println("this is about type of packet");
//			System.out
//					.println("******************************************************************************");
//
//			if (proto.equals(("TCP"))) {
//				System.out.println(" /n this is TCP packet");
//				TCPPacket tp = (TCPPacket) packet;
//				System.out.println("this is destination port of tcp :"
//						+ tp.dst_port);
//				if (tp.ack) {
//					System.out.println("\n" + "this is an acknowledgement");
//				} else {
//					System.out.println("this is not an acknowledgment packet");
//				}
//
//				if (tp.rst) {
//					System.out.println("reset connection ");
//				}
//				System.out.println(" \n protocol version is :" + tp.version);
//				System.out.println("\n this is destination ip " + tp.dst_ip);
//				System.out.println("this is source ip" + tp.src_ip);
//				if (tp.fin) {
//					System.out
//							.println("sender does not have more data to transfer");
//				}
//				if (tp.syn) {
//					System.out.println("\n request for connection");
//				}
//
//			} else if (proto.equals("ICMP")) {
//				ICMPPacket ipc = (ICMPPacket) packet;
//				// java.net.InetAddress[] routers=ipc.router_ip;
//				// for(int t=0;t
//				// System.out.println("\n"+routers[t]);
//				// }
//				System.out.println(" \n this is alive time :" + ipc.alive_time);
//				System.out.println("\n number of advertised address :"
//						+ (int) ipc.addr_num);
//				System.out.println("mtu of the packet is :" + (int) ipc.mtu);
//				System.out.println("subnet mask :" + ipc.subnetmask);
//				System.out.println("\n source ip :" + ipc.src_ip);
//				System.out.println("\n destination ip:" + ipc.dst_ip);
//				System.out.println("\n check sum :" + ipc.checksum);
//				System.out.println("\n icmp type :" + ipc.type);
//				System.out.println("");
//
//			} else if (proto.equals("UDP")) {
//				UDPPacket pac = (UDPPacket) packet;
//				System.out.println("this is udp packet \n");
//				System.out.println("this is source port :" + pac.src_port);
//				System.out.println("this is destination port :" + pac.dst_port);
//
//			}
//
//			System.out
//					.println("******************************************************");
//
//		}
//
//	}
}