package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import rules.ITriggeringRule;

public class RuleUtils {
	public static ITriggeringRule getLogFunction(String filename) {
		ITriggeringRule logfunction = (packet) -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String timestamp = LocalDateTime.now().format(formatter);
			try (FileWriter writer = new FileWriter(filename, true)) {
				writer.write("[" + timestamp + "] " + packet.toString() + "\n");
			} catch (IOException e) {
	            System.err.println("Errore nel logging del pacchetto: " + e.getMessage());
	        }
			System.out.println(filename);
		};
		return logfunction;
	}
	
	public static ITriggeringRule getDefaultDenyFunction() {
		ITriggeringRule function = (packet) -> {
			// TODO: drop the packet
		};
		return function;
	}
	
	public static ITriggeringRule getDefaultAllowFunction() {
		ITriggeringRule function = (packet) -> {
			// TODO: let the packet pass
		};
		return function;
	}
}
