package mc.rysty.heliosphereworld.moshpit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class CommandMoshpitSoup implements CommandExecutor {

    public CommandMoshpitSoup(HelioSphereWorld plugin) {
        plugin.getCommand("moshpitsoup").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("moshpitsoup")) {
            if (sender.hasPermission("hs.moshpitsoup")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    World world = player.getWorld();

                    if (world.equals(Bukkit.getWorld("Moshpit"))) {
                        PlayerInventory inventory = player.getInventory();
                        ItemStack stew = new ItemStack(Material.MUSHROOM_STEW, 1);
                        ItemMeta itemMeta = stew.getItemMeta();

                        itemMeta.setDisplayName(MessageUtils.convertChatColors("&bMushroom Stew"));
                        stew.setItemMeta(itemMeta);

                        inventory.setItem(2, stew);
                        inventory.setItem(3, stew);
                        inventory.setItem(4, stew);
                        inventory.setItem(5, stew);
                        inventory.setItem(6, stew);
                        inventory.setItem(7, stew);
                        inventory.setItem(8, stew);
                    } else
                        MessageUtils.configStringMessage(sender, "world_command_error", "<world>", world.getName());
                } else
                    MessageUtils.consoleError();
            } else
                MessageUtils.noPermissionError(sender);
        }
        return false;
    }
}