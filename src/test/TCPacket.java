package test;
import java.io.BufferedOutputStream;
import net.sourceforge.jpcap.net.Packet;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import net.sourceforge.jpcap.capture.PacketCapture;
import net.sourceforge.jpcap.capture.PacketListener;
import jpcap.packet.*;

public class TCPacket {
    public static void main(String[] args) {
        try {
        	 TCPacket test = new  TCPacket(PacketCapture.lookupDevices()[5].trim().split("\\s")[0]);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public TCPacket(String device) throws Exception {
        // Initialize jpcap
        PacketCapture pcap = new PacketCapture();
        System.out.println("Using device '" + device + "'");
        pcap.open(device, true);
        pcap.setFilter("port 4444", true);
        pcap.addPacketListener(new PacketHandler());

        System.out.println("Capturing packets...");
        pcap.capture(-1); // -1 is infinite capturing
    }
}


class PacketHandler implements PacketListener {
    BufferedOutputStream stream;

    public PacketHandler() throws IOException {
        Path path = Paths.get("out.txt");
        stream = new BufferedOutputStream(
                Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND));
    }

    public void packetArrived(Packet packet) {
//    	 try {
//             // only handle TCP packets
//
//             if(packet instanceof TCPPacket) {
//                 TCPPacket tcpPacket = (TCPPacket)packet;
//                 byte[] data;
//                 data = tcpPacket.getTCPData();
//                 stream.write(data);
//                 stream.write("\r\n----------\r\n".getBytes());
//                 stream.flush();
//             }
//         } catch( Exception e ) {
//             e.printStackTrace(System.out);
//         }
    }

	
}