package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import elements.MyPacket;

public class Logger {
	public static void writeOnLogFile(MyPacket packet, String filename, LogLevel level, String message) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String timestamp = LocalDateTime.now().format(formatter);
		try (FileWriter writer = new FileWriter(filename, true)) {
			writer.write("[" + level + "]  [" + timestamp + "] {" + packet.toString() + "}: " + message + "\n");
		} catch (IOException e) {
            System.err.println("Errore nel logging del pacchetto: " + e.getMessage());
        }
	}
}
