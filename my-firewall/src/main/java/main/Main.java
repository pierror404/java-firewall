package main;

import sniffer.PacketSniffer;

public class Main {

	public static void main(String[] args) {
        System.out.println("Firewall Java avviato");

        PacketSniffer sniffer =
                new PacketSniffer();

        try {
			sniffer.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
