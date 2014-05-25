import jpcap.*;
import jpcap.packet.*;
import java.io.*;
import jpcap.packet.TCPPacket.*;
import java.io.*;
import jpcap.JpcapCaptor.*;


class Network implements  jpcap.PacketReceiver {
    String sp=null;
    String dp=null;
    String window=null;
    String sequence=null;
    String acknowledge=null;
    FileWriter ff=null;
    String da=null;

    public void receivePacket(Packet pt) {
        try {
            if(pt instanceof TCPPacket) {
                TCPPacket tp=(TCPPacket)pt;
                ff=new FileWriter("da.txt",true);
                sp=new Integer(tp.src_port).toString();
                dp=new Integer(tp.dst_port).toString();
                window=new Integer(tp.window).toString();
                sequence=new Long(tp.sequence).toString();
                acknowledge=new Long(tp.ack_num).toString();
                byte[]dat=tp.data;
                da=new String(dat);
                ff.write("\r\n Source port is :-"+sp);    
                ff.write("\r\n Desination port is:-"+dp);
                ff.write("\r\n Sequence no is:-"+sequence);
                ff.write("\r\n Acknowledgement no  is:-"+acknowledge);
                ff.write("\r\n Status of rsv1 flag is:-"+tp.rsv1);
                ff.write("\r\n Status of rsv2 flag is:-"+tp.rsv2);
                ff.write("\r\n Status of Syn flag is:-"+tp.syn);
                ff.write("\r\n Status of Urg flag is:-"+tp.urg);
                ff.write("\r\n Status of Fin flag is:-"+tp.fin);
                ff.write("\r\n Data :-"+da);
                ff.write("\r\n");
                ff.write("\r\n");
                ff.close();
            }                 
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
   }  

   public static void main(String args[])throws IOException {
        int i;        
        try {
            NetworkInterface[] devices = JpcapCaptor.getDeviceList();

            //for each network interface
            for (i = 0; i < devices.length; i++) {
                //print out its name and description
                System.out.println(i+": "+devices[i].name + "(" +    
                        devices[i].description+")"+devices[i].loopback);
                //print out its datalink name and description
                System.out.println(" datalink: "+devices[i].datalink_name + "(" 
                        + devices[i].datalink_description+")");

                //print out its MAC address
                System.out.print(" MAC address:");
                for (byte b : devices[i].mac_address)
                    System.out.print(Integer.toHexString(b&0xff) + ":");
                System.out.println();

                //print out its IP address, subnet mask and broadcast address
                for (NetworkInterfaceAddress a : devices[i].addresses)
                    System.out.println(" address:"+a.address + " " + a.subnet + " "+ 
                            a.broadcast);
            }
            JpcapCaptor captor=JpcapCaptor.openDevice(devices[0], 65535, true, 20);
            captor.loopPacket(-1,new Network());
            captor.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}