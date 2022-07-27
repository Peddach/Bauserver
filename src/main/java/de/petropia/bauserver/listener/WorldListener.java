package de.petropia.bauserver.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import de.petropia.bauserver.gui.Guis;

public class WorldListener implements Listener{
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent event) {
		Guis.getMainGui().update();
	}
	
	@EventHandler
	public void onWorldUnload(WorldUnloadEvent event) {
		Guis.getMainGui().update();
	}
	
	@EventHandler
	public void onWorldUnload(WorldSaveEvent event) {
		Guis.getMainGui().update();
	}
}
