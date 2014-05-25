import jpcap.packet.DatalinkPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;
import jpcap.PacketReceiver;

public class PacketPrinter implements PacketReceiver {

	static int i = 0;
	//http://en.wikipedia.org/wiki/List_of_IP_protocol_numbers
	String protocoll[] = { "HOPOPT", "ICMP", "IGMP", "GGP", "IPV4", "ST",
			"TCP", "CBT", "EGP", "IGP", "BBN", "NV2", "PUP", "ARGUS", "EMCON",
			"XNET", "CHAOS", "UDP", "mux" };

	public void receivePacket(Packet packet) {

//		System.out.println(packet + "\n");
//		System.out
//				.println("******************************************************************");
//		System.out.println("this is packet " + i + " :" + "\n");
//		i++;
		IPPacket tpt = (IPPacket) packet;
		if (packet != null) {
			// number of protocol
			int ppp = tpt.protocol;
			
			// find name of packet, using nr of protocol
			String proto = protocoll[ppp];

			// //check if it is fragmented or not
			// if (tpt.dont_frag) {
			// System.out
			// .println("dft bi is set. packet will not be fragmented \n");
			//
			// } else {
			// System.out
			// .println("dft bi is not set. packet will  be fragmented \n");
			// }

			// System.out.println(" \n destination ip is :" + tpt.dst_ip);
			// System.out.println("\n this is source ip :" + tpt.src_ip);
			// System.out.println("\n this is hop limit :" + tpt.hop_limit);
			// System.out.println(" \n this is identification field  :"
			// + tpt.ident);
			// System.out.println(" \npacket length :" + tpt.length);
			// System.out.println("\n packet priority  :" + (int) tpt.priority);
			// System.out.println("type of service field" + tpt.rsv_tos);
			// if (tpt.r_flag) {
			// System.out.println("releiable transmission");
			// } else {
			// System.out.println("not reliable");
			// }
			// System.out.println("protocol version is : " + (int) tpt.version);
			// System.out.println("flow label field" + tpt.flow_label);
			//
			// System.out
			// .println("**********************************************************************");
			//
			// System.out.println("datalink lavel analysis");
			// System.out
			// .println("********************************************************************");
			// DatalinkPacket dp = packet.datalink;
			//
			// EthernetPacket ept = (EthernetPacket) dp;
			// System.out.println("this is destination mac address :"
			// + ept.getDestinationAddress());
			// System.out.println("\n this is source mac address"
			// + ept.getSourceAddress());
			//
			// System.out
			// .println("*********************************************************************");
			// System.out.println("this is about type of packet");
			// System.out
			// .println("******************************************************************************");

			if (proto.equals(("TCP"))) {
				System.out.println("TCP packet (packet nr " + i + ")" + "\n");
				
				TCPPacket tp = (TCPPacket) packet;
				System.out.println("sequence number: "+tp.sequence);
				System.out.println("destination port of tcp: "
						+ tp.dst_port);
				System.out.println("destination ip: " + tp.dst_ip);
				System.out.println("source ip: " + tp.src_ip);
				
				/***             flagi - TCP              **/
				System.out.print("Flags: ");
				// URG - urgent, important data
				if(tp.urg){
					System.out.print("URG, ");
				}
				//ACK = 1 - acknowledgment of received of packages with specific sequential numbers
				if (tp.ack) {
					System.out.print("ACK, ");
				}
				//PSH - push data, empty buffer and sent segment
				if(tp.psh){
					System.out.print("PSH, ");
				}
				//RST = 1 -  terminate(reset, break) connection
				if(tp.rst){
					System.out.print("RST, ");
				}
				//SYN = 1 - then this is the initial sequence number, used during originating connection,  request for connection
				if(tp.syn){
					System.out.print("SYN, ");
				}
				//FIN = 1 - sender does not have more data to transfer
				if(tp.fin){
					System.out.print("FIN, ");
				}
				/*******************************************/
				//TODO lagi ip tpt.r_flag
				
				
				
					System.out
							.println("\n******************************************************");

				
			}
			// } else if (proto.equals("ICMP")) {
			// ICMPPacket ipc = (ICMPPacket) packet;
			// // java.net.InetAddress[] routers=ipc.router_ip;
			// // for(int t=0;t
			// // System.out.println("\n"+routers[t]);
			// // }
			// System.out.println(" \n this is alive time :" + ipc.alive_time);
			// System.out.println("\n number of advertised address :"
			// + (int) ipc.addr_num);
			// System.out.println("mtu of the packet is :" + (int) ipc.mtu);
			// System.out.println("subnet mask :" + ipc.subnetmask);
			// System.out.println("\n source ip :" + ipc.src_ip);
			// System.out.println("\n destination ip:" + ipc.dst_ip);
			// System.out.println("\n check sum :" + ipc.checksum);
			// System.out.println("\n icmp type :" + ipc.type);
			// System.out.println("");
			//
			// } else if (proto.equals("UDP")) {
			// UDPPacket pac = (UDPPacket) packet;
			// System.out.println("this is udp packet \n");
			// System.out.println("this is source port :" + pac.src_port);
			// System.out.println("this is destination port :" + pac.dst_port);
			//
			// }
//
//			System.out
//					.println("******************************************************");

		}
		i++;

	}

}
