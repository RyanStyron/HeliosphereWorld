package mc.rysty.heliosphereworld.moshpit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import mc.rysty.heliosphereworld.HelioSphereWorld;
import mc.rysty.heliosphereworld.utils.MessageUtils;

public class CommandAutoEquip implements CommandExecutor {

    public CommandAutoEquip(HelioSphereWorld plugin) {
        plugin.getCommand("autoequip").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("autoequip")) {
            if (sender.hasPermission("hs.autoequip")) {
                Player target = null;

                if (args.length > 0)
                    target = Bukkit.getPlayer(args[0]);
                else if (sender instanceof Player)
                    target = (Player) sender;

                if (target == null)
                    MessageUtils.validPlayerError(sender);
                else {
                	String worldName = target.getWorld().getName();
                	
                    if (worldName.equalsIgnoreCase("Moshpit")) {
                        PlayerInventory inventory = target.getInventory();

                        ItemStack helmet = inventory.getItem(5);
                        ItemStack chestplate = inventory.getItem(4);
                        ItemStack leggings = inventory.getItem(3);
                        ItemStack boots = inventory.getItem(2);
                        ItemStack[] newArmorContents = { helmet, chestplate, leggings, boots };

                        inventory.setArmorContents(newArmorContents);
                        inventory.remove(helmet);
                        inventory.remove(chestplate);
                        inventory.remove(leggings);
                        inventory.remove(boots);
                        target.updateInventory();
                    } else
                        MessageUtils.configStringMessage(sender, "world_command_error", "<world>", worldName);
                }
            } else
                MessageUtils.noPermissionError(sender);
        }
        return false;
    }
}