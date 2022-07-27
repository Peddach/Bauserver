package de.petropia.bauserver.listener;

import java.util.HashMap;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.petropia.bauserver.Bauserver;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class PlayerChatListener implements Listener {
	
	private static final HashMap<Player, Consumer<String>> PLAYER_CONSUMER = new HashMap<>();
	
	@EventHandler
	public void onPlayerChat(AsyncChatEvent event) {
		if(!PLAYER_CONSUMER.containsKey(event.getPlayer())) {
			return;
		}
		event.setCancelled(true);
		Consumer<String> consumer = PLAYER_CONSUMER.get(event.getPlayer());
		Component message = event.message();
		String plainText = PlainTextComponentSerializer.plainText().serialize(message);
		if(plainText.contains(" ") || plainText.contains("!") || plainText.contains("-")) {
			event.getPlayer().sendMessage(Component.text("Bitte nutze keine Sonderzeichen außer unterstriche!").color(NamedTextColor.RED));
			return;
		}
		if(plainText.equalsIgnoreCase("abbrechen")) {
			PLAYER_CONSUMER.remove(event.getPlayer());
			Bukkit.getScheduler().runTask(Bauserver.getInstance(), () -> {
				consumer.accept(null);
			});
			event.getPlayer().sendMessage(Component.text("Abgebrochen").color(NamedTextColor.RED));
			return;
		}
		for(World world : Bukkit.getWorlds()) {
			if(world.getName().equalsIgnoreCase(plainText)) {
				event.getPlayer().sendMessage(Component.text("Diese Welt existiert bereits").color(NamedTextColor.RED));
				return;
			}
		}
		Bukkit.getScheduler().runTask(Bauserver.getInstance(), () -> {
			consumer.accept(plainText);
		});
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		PLAYER_CONSUMER.remove(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		if(!PLAYER_CONSUMER.containsKey(event.getPlayer())) {
			return;
		}
		event.setCancelled(true);
		event.getPlayer().sendMessage(Component.text("Du musst noch einen Namen für die Welt angeben!").color(NamedTextColor.GRAY));
	}
	
	public static void add(Player player, Consumer<String> consumer) {
		player.sendMessage(Component.text("Bitte gib jetzt einen Namen ein oder abbrechen:").color(NamedTextColor.GREEN));
		player.closeInventory();
		PLAYER_CONSUMER.putIfAbsent(player, consumer);
	}
	
	public static void remove(Player player) {
		PLAYER_CONSUMER.remove(player);
	}
	
}
