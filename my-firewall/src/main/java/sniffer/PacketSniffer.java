package sniffer;

import org.pcap4j.core.*;
import org.pcap4j.packet.*;

public class PacketSniffer {

    public void start() throws Exception {

        PcapNetworkInterface nif =
                Pcaps.findAllDevs().get(0);

        PcapHandle handle =
                nif.openLive(65536,
                PcapNetworkInterface.PromiscuousMode.PROMISCUOUS,
                10);

        PacketListener listener = packet -> {
        		packet.getHeader();
            if(packet.contains(IpV4Packet.class)) {

                IpV4Packet ipPacket =
                        packet.get(IpV4Packet.class);

                String source =
                        ipPacket.getHeader().getSrcAddr().getHostAddress();
                
                

                /*if(Blacklist.isBlocked(source))
                    return;

                DDoSDetector.check(source);

                if(packet.contains(TcpPacket.class)) {

                    TcpPacket tcp =
                            packet.get(TcpPacket.class);

                    int port =
                            tcp.getHeader().getDstPort().valueAsInt();

                    PortScanDetector.check(source, port);

                }*/

                System.out.println("Pacchetto OK da " + source);

            }

        };

        handle.loop(-1, listener);
    }
}