package de.petropia.bauserver.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.petropia.bauserver.Bauserver;
import de.petropia.bauserver.gui.Guis;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Guis.getMainGui().openForPlayer(event.getPlayer());
		Bukkit.getScheduler().runTaskLater(Bauserver.getInstance(), () -> {
			if(!event.getPlayer().isOnline()) {
				return;
			}
			event.getPlayer().sendMessage(Bauserver.PREFIX.append(Component.text("Du kannst die Weltenliste jeder Zeit mit /bauserver öffnen").color(NamedTextColor.GRAY).decorate(TextDecoration.ITALIC)
					.hoverEvent(Component.text("Klicke zum öffnen")).color(NamedTextColor.GREEN)
					.clickEvent(ClickEvent.runCommand("/bauserver"))));
		}, 20*3);
	}
}
