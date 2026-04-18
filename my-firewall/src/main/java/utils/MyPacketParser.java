package utils;

import java.util.Arrays;
import java.util.Optional;

import elements.IPv4;
import elements.IPv6;
import elements.MyPacket;
import elements.NetPort;
import elements.NetworkLayerProtocol;
import elements.ProtocolFactory;
import elements.TransportLayerProtocol;
import exceptions.IllegalIPv4Exception;
import exceptions.IllegalSubnetException;
import elements.ApplicationLayerProtocol;
import elements.IP;
import elements.IProtocol;

public class MyPacketParser {
	public static MyPacket fromRaw(byte[] raw) throws NumberFormatException, IllegalSubnetException, IllegalIPv4Exception {

        if (raw == null || raw.length < 20) {
            return emptyPacket();
        }

        // =========================
        // IPv4 / IPv6 detection
        // =========================
        int version = (raw[0] >> 4) & 0xF;

        // =========================
        // IPv4
        // =========================
        if (version == 4) {
        		return parseIPv4(raw);
        }

        // =========================
        // IPv6 (base version)
        // =========================
        if (version == 6) {
        		return parseIPv6(raw);
        }

        return emptyPacket();
    }

    // =========================
    // Empty packet helper
    // =========================
    private static MyPacket emptyPacket() {
        return new MyPacket(
            Optional.empty(), Optional.empty(),
            Optional.empty(), Optional.empty(),
            Optional.empty(), Optional.empty(), Optional.empty(),
            Optional.empty(), Optional.empty(), Optional.empty()
        );
    }

    // =========================
    // IPv6 formatter
    // =========================
    private static String ipv6ToString(byte[] raw, int start) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 16; i += 2) {
            int part = ((raw[start + i] & 0xFF) << 8) |
                       (raw[start + i + 1] & 0xFF);

            sb.append(Integer.toHexString(part));

            if (i < 14) sb.append(":");
        }

        return sb.toString();
    }
    
    private static MyPacket parseIPv4(byte[] raw) throws IllegalIPv4Exception, IllegalSubnetException {

        int ihl = (raw[0] & 0x0F) * 4;
        if (raw.length < ihl) return emptyPacket();

        int protocolNumber = raw[9] & 0xFF;

        // IP addresses
        IP srcIp = IPv4.fromString(
            (raw[12] & 0xFF) + "." +
            (raw[13] & 0xFF) + "." +
            (raw[14] & 0xFF) + "." +
            (raw[15] & 0xFF)
        );

        IP dstIp = IPv4.fromString(
            (raw[16] & 0xFF) + "." +
            (raw[17] & 0xFF) + "." +
            (raw[18] & 0xFF) + "." +
            (raw[19] & 0xFF)
        );

        byte[] ipHeader = Arrays.copyOfRange(raw, 0, ihl);

        // =========================
        // NETWORK PROTOCOL (ICMP, IGMP, OSPF…)
        // =========================
        IProtocol nextHeader = ProtocolFactory.fromNetworkNumber(protocolNumber);

        NetworkLayerProtocol networkProto;
        IProtocol transportProto = null;

        if (nextHeader instanceof NetworkLayerProtocol nl) {
            // ICMP, IGMP, OSPF…
            networkProto = nl;
        } else {
            // TCP, UDP, SCTP…
            networkProto = NetworkLayerProtocol.IPv4;
            transportProto = ProtocolFactory.fromTransportNumber(protocolNumber);
        }

        // =========================
        // TRANSPORT HEADER (solo TCP/UDP/SCTP)
        // =========================
        Optional<NetPort> srcPort = Optional.empty();
        Optional<NetPort> dstPort = Optional.empty();
        Optional<byte[]> transportHeader = Optional.empty();

        int payloadOffset = ihl;

        if (transportProto == TransportLayerProtocol.TCP ||
            transportProto == TransportLayerProtocol.UDP ||
            transportProto == TransportLayerProtocol.SCTP) {

            if (raw.length < ihl + 8) return emptyPacket();

            int sPort = ((raw[payloadOffset] & 0xFF) << 8) | (raw[payloadOffset + 1] & 0xFF);
            int dPort = ((raw[payloadOffset + 2] & 0xFF) << 8) | (raw[payloadOffset + 3] & 0xFF);

            srcPort = Optional.of(new NetPort(sPort));
            dstPort = Optional.of(new NetPort(dPort));

            int transportHeaderLen = (transportProto == TransportLayerProtocol.TCP)
                    ? ((raw[payloadOffset + 12] >> 4) & 0xF) * 4
                    : 8;

            transportHeader = Optional.of(
                Arrays.copyOfRange(raw, payloadOffset, payloadOffset + transportHeaderLen)
            );

            payloadOffset += transportHeaderLen;
        }

        // =========================
        // PAYLOAD
        // =========================
        Optional<byte[]> payload = Optional.empty();
        if (payloadOffset < raw.length) {
            payload = Optional.of(Arrays.copyOfRange(raw, payloadOffset, raw.length));
        }

        // =========================
        // APPLICATION PROTOCOL
        // =========================
        IProtocol appProto = null;

        if (dstPort.isPresent()) {
            appProto = ProtocolFactory.fromPort(dstPort.get().getPort());
        }

        if (payload.isPresent() && appProto == null) {
            String data = new String(payload.get());
            if (data.startsWith("GET") || data.startsWith("POST") || data.startsWith("HTTP"))
                appProto = ApplicationLayerProtocol.HTTP;
            else if (data.contains("DNS"))
                appProto = ApplicationLayerProtocol.DNS;
        }

        return new MyPacket(
            Optional.of(srcIp),
            srcPort,
            Optional.of(dstIp),
            dstPort,
            Optional.of(networkProto),
            Optional.ofNullable(transportProto),
            Optional.ofNullable(appProto),
            Optional.of(ipHeader),
            transportHeader,
            payload
        );
    }
    
    private static MyPacket parseIPv6(byte[] raw) throws IllegalIPv4Exception, IllegalSubnetException {

        if (raw.length < 40) return emptyPacket();

        IP srcIp = IPv6.fromString(ipv6ToString(raw, 8));
        IP dstIp = IPv6.fromString(ipv6ToString(raw, 24));

        int nextHeader = raw[6] & 0xFF;

        // =========================
        // NETWORK PROTOCOL (ICMPv6)
        // =========================
        IProtocol netCandidate = ProtocolFactory.fromNetworkNumber(nextHeader);

        NetworkLayerProtocol networkProto;
        IProtocol transportProto = null;

        if (netCandidate instanceof NetworkLayerProtocol nl) {
            networkProto = nl; // ICMPv6, OSPF, ecc.
        } else {
            networkProto = NetworkLayerProtocol.IPv6;
            transportProto = ProtocolFactory.fromTransportNumber(nextHeader);
        }

        return new MyPacket(
            Optional.of(srcIp),
            Optional.empty(),
            Optional.of(dstIp),
            Optional.empty(),
            Optional.of(networkProto),
            Optional.ofNullable(transportProto),
            Optional.empty(),
            Optional.of(Arrays.copyOfRange(raw, 0, 40)),
            Optional.empty(),
            Optional.empty()
        );
    }
}
