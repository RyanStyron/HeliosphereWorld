package mc.rysty.heliosphereworld.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import mc.rysty.heliosphereworld.HelioSphereWorld;

public class MessageUtils {

	private static FileConfiguration config = HelioSphereWorld.getInstance().getConfig();

	public static String convertChatColors(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static void message(CommandSender sender, String message) {
		sender.sendMessage(convertChatColors(message));
	}

	public static void configStringMessage(CommandSender sender, String configString) {
		message(sender, config.getString(configString));
	}

	public static void configStringMessage(CommandSender sender, String configString, String regex, String replacement) {
		message(sender, config.getString(configString).replaceAll(regex, replacement));
	}

	public static void consoleError() {
		configStringMessage(Bukkit.getConsoleSender(), "console_error_message");
	}

	public static void noPermissionError(CommandSender sender) {
		configStringMessage(sender, "no_perm_error");
	}
}
