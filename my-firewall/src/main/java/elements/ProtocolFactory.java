package elements;

public class ProtocolFactory {

    public static IProtocol fromNetworkNumber(int number) {
        return switch (number) {
            case 1 -> NetworkLayerProtocol.ICMP;
            case 2 -> NetworkLayerProtocol.IGMP;
            case 4 -> NetworkLayerProtocol.IPv4;
            case 41 -> NetworkLayerProtocol.IPv6;
            case 89 -> NetworkLayerProtocol.OSPF;
            default -> null;
        };
    }

    public static IProtocol fromTransportNumber(int number) {
        return switch (number) {
            case 6 -> TransportLayerProtocol.TCP;
            case 17 -> TransportLayerProtocol.UDP;
            case 132 -> TransportLayerProtocol.SCTP;
            default -> null;
        };
    }

    public static IProtocol fromPort(int port) {
        return switch (port) {
            case 80 -> ApplicationLayerProtocol.HTTP;
            case 443 -> ApplicationLayerProtocol.HTTPS;
            case 53 -> ApplicationLayerProtocol.DNS;
            case 25 -> ApplicationLayerProtocol.SMTP;
            case 110 -> ApplicationLayerProtocol.POP3;
            case 143 -> ApplicationLayerProtocol.IMAP;
            case 21 -> ApplicationLayerProtocol.FTP;
            case 22 -> ApplicationLayerProtocol.SSH;
            default -> null;
        };
    }
}