package de.petropia.bauserver;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.petropia.bauserver.commands.BauserverCommand;
import de.petropia.bauserver.listener.MainGuiListener;
import de.petropia.bauserver.listener.PlayerChatListener;
import de.petropia.bauserver.listener.WorldListener;

public class Bauserver extends JavaPlugin {
	
	private static Bauserver instance;
	
	@Override
	public void onEnable() {	
		instance = this;
		
		registerListener();
		
		this.getCommand("bauserver").setExecutor(new BauserverCommand());
	}
	
	private void registerListener() {
		PluginManager manager = this.getServer().getPluginManager();
		manager.registerEvents(new MainGuiListener(), this);
		manager.registerEvents(new PlayerChatListener(), this);
		manager.registerEvents(new WorldListener(), this);
	}
	
	public static Bauserver getInstance() {
		return instance;
	}
}
