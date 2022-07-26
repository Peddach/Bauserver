package de.petropia.bauserver;

import org.bukkit.plugin.java.JavaPlugin;

public class Bauserver extends JavaPlugin {
	
	private static Bauserver instance;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		saveConfig();
		reloadConfig();
		
		instance = this;
	}

	public static Bauserver getInstance() {
		return instance;
	}
}
