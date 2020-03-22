package mc.rysty.heliosphereworld.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerDeathRespawn implements Listener {

	@EventHandler
	public void onDeathRespawn(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player) e.getEntity();
		World world = player.getWorld();

		if (!world.getGameRuleValue(GameRule.KEEP_INVENTORY)) {
			if (player.getHealth() - e.getFinalDamage() <= 0) {
				e.setCancelled(true);
				player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_HURT, 1F, 1F);
				for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
					if (onlinePlayers.getWorld() == world) {
						onlinePlayers.sendMessage(player.getDisplayName() + ChatColor.DARK_RED + " died");
					}
				}
				if (Bukkit.getPluginCommand("spawncorpse") != null) {
					Bukkit.dispatchCommand(player, "spawncorpse");
				}
				playerDeathReset(player);
				if (player.getBedSpawnLocation() != null) {
					player.teleport(player.getBedSpawnLocation());
				} else {
					player.teleport(world.getSpawnLocation());
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void playerDeathReset(Player player) {
		if (player != null && Bukkit.getPlayer(player.getUniqueId()) != null) {
			if (!player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY)) {
				player.getInventory().clear();
				player.getInventory().setHelmet(null);
				player.getInventory().setChestplate(null);
				player.getInventory().setLeggings(null);
				player.getInventory().setBoots(null);
			}
			player.setFoodLevel(20);
			player.setHealth(player.getMaxHealth());
			for (PotionEffect potionEffect : player.getActivePotionEffects()) {
				player.removePotionEffect(potionEffect.getType());
			}
		}
	}
}
