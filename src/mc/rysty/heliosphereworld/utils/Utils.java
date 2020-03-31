package mc.rysty.heliosphereworld.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class Utils {

	private static FileConfiguration config = HelioSphereWorld.getInstance().getConfig();

	public static String chat(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static void message(CommandSender sender, String message) {
		sender.sendMessage(chat(message));
	}

	public static void configStringMessage(CommandSender sender, String configString) {
		message(sender, config.getString(configString));
	}

	public static void consoleErrorMessage(CommandSender sender) {
		configStringMessage(sender, "console_error_message");
	}
}
