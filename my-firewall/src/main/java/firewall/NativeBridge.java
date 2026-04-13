package firewall;

import java.io.*;
import java.nio.file.*;
import java.util.List;

import elements.MyPacket;
import exceptions.IllegalIPv4Exception;
import exceptions.IllegalSubnetException;
import rules.Rule;
import utils.MyPacketParser;

public class NativeBridge {
	
	private static FirewallEngine firewall;
	
	public NativeBridge(List<Rule> rules) {
		firewall = new FirewallEngine(rules);
	}
	
    static {
        try {
            loadDll("firewall.dll");
            loadDll("WinDivert.dll");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadDll(String name) throws Exception {
        InputStream in = NativeBridge.class.getResourceAsStream("/native/" + name);
        File temp = File.createTempFile(name, "");
        temp.deleteOnExit();

        Files.copy(in, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.load(temp.getAbsolutePath());
    }

    public native void startFirewall();

    public static boolean isMalicious(byte[] packet, int len) {
    		try {
				MyPacket pack = MyPacketParser.fromRaw(packet);
				return firewall.evaluate(pack);
				
		} catch (NumberFormatException | IllegalSubnetException | IllegalIPv4Exception e) {
				return false;
		}
    }
}