package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import rules.ITriggeringRule;

public class RuleUtils {
	public static ITriggeringRule getLogDenyFunction(String filename) {
		ITriggeringRule logfunction = (packet) -> {
			System.out.println("Scrivo su: " + new File(filename).getAbsolutePath());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String timestamp = LocalDateTime.now().format(formatter);
			try (FileWriter writer = new FileWriter(filename, true)) {
				writer.write("[" + timestamp + "] " + packet.toString() + "\n");
			} catch (IOException e) {
	            System.err.println("Errore nel logging del pacchetto: " + e.getMessage());
	        }
			return true;
		};
		return logfunction;
	}
	
	public static ITriggeringRule getLogAllowFunction(String filename) {
		ITriggeringRule logfunction = (packet) -> {
			System.out.println("Scrivo su: " + new File(filename).getAbsolutePath());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String timestamp = LocalDateTime.now().format(formatter);
			try (FileWriter writer = new FileWriter(filename, true)) {
				writer.write("[" + timestamp + "] " + packet.toString() + "\n");
			} catch (IOException e) {
	            System.err.println("Errore nel logging del pacchetto: " + e.getMessage());
	        }
			return false;
		};
		return logfunction;
	}
	
	public static ITriggeringRule getDefaultDenyFunction() {
		ITriggeringRule function = (_) -> {
			return true;
		};
		return function;
	}
	
	public static ITriggeringRule getDefaultAllowFunction() {
		ITriggeringRule function = (_) -> {
			return false;
		};
		return function;
	}
}
