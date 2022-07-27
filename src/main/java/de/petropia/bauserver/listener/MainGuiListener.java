package de.petropia.bauserver.listener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;

import de.petropia.bauserver.Bauserver;
import de.petropia.bauserver.gui.Guis;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.Sound.Source;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class MainGuiListener implements Listener {

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		if(!event.getInventory().equals(Guis.getMainGui().getInv())) {
			return;	
		}
		event.setCancelled(true);
		if(event.getRawSlot() <= 5*9 && event.getRawSlot() >= 0) {
			tpToWorld((Player) event.getWhoClicked(), event.getCurrentItem());
		}
		if(event.getRawSlot() == 49) {
			Player player = (Player) event.getWhoClicked();
			PlayerChatListener.add(player, input -> {
				if(input == null) {
					PlayerChatListener.remove(player);
					return;
				}
				player.sendMessage(Component.text("Welt wird erstellt. Du wirst gleich Teleportiert").color(NamedTextColor.GREEN));
				player.sendMessage(Component.text("Es könnnen hierbei lags auftreten!").color(NamedTextColor.RED));
				MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
				MVWorldManager worldManager = core.getMVWorldManager();
				worldManager.addWorld(input, Environment.NORMAL, null, WorldType.NORMAL, false, "VoidGen");
				Bukkit.getScheduler().runTaskLater(Bauserver.getInstance(), () -> {
					player.teleportAsync(Bukkit.getWorld(input).getSpawnLocation()).thenAccept(bool -> {
						if(bool) {
							player.sendMessage(Component.text("Welt erfolgreich erstellt").color(NamedTextColor.GREEN));
						}
					});
				}, 20);
				PlayerChatListener.remove(player);
			});
		}
	}
	
	private void tpToWorld(Player player, ItemStack item) {
		if(item == null) {
			System.out.println("Current item null");
			return;
		}
		String plainText = PlainTextComponentSerializer.plainText().serialize(item.displayName());
		plainText = plainText.replace("[", "");
		plainText = plainText.replace("]", "");
		World world = Bukkit.getWorld(plainText);
		if(world == null) {
			System.out.println("world null: " + plainText);
			return;
		}
		player.teleportAsync(world.getSpawnLocation()).thenAccept(bool -> {
			if(bool) {
				player.playSound(Sound.sound(org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, Source.NEUTRAL, 1, 1));
				return;
			}
			player.sendMessage(Component.text("Es ist ein unerwarteter Fehler aufgetreten!").color(NamedTextColor.RED));
		});
		player.sendMessage(Component.text("Du wirst teleportiert").color(NamedTextColor.DARK_GREEN));
	}
	
	@EventHandler
	public void onInventoryInteractListener(InventoryInteractEvent event) {
		if(!event.getInventory().equals(Guis.getMainGui().getInv())) {
			return;
		}
		event.setCancelled(true);
	}
}
