package de.petropia.bauserver.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class MainGui {

	private Inventory inv = Bukkit.getServer().createInventory(null, 6*9, Component.text("Welten").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD));
	
	public MainGui() {
		update();
	}
	
	public void openForPlayer(Player player) {
		player.openInventory(inv);
	}
	
	public void update() {
		inv.clear();
		for(World world : Bukkit.getWorlds()) {
			inv.addItem(getRandomItem(world.getName()));
		}
		ItemStack item = new ItemStack(Material.GOLD_INGOT);
		item.addUnsafeEnchantment(Enchantment.LUCK, 1);
		item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		item.editMeta(meta -> {
			meta.displayName(Component.text("Welt erstellen").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
			final List<Component> lore = new ArrayList<>();
			lore.add(Component.empty());
			lore.add(Component.text("Erstelle eine neue Bauwelt nach deinen Vorstellungen").color(NamedTextColor.GRAY));
			lore.add(Component.empty());
			lore.add(Component.text("Linksklick").color(NamedTextColor.GRAY)
					.append(Component.text(" >> ").color(NamedTextColor.DARK_GRAY))
					.append(Component.text("Welt erstellen").color(NamedTextColor.GOLD)));
			lore.add(Component.empty());
			meta.lore(lore);
		});
		inv.setItem(49, item);
	}
	
	private ItemStack getRandomItem(String string) {
		double number = 0;
		for(char character : string.toCharArray()) {
			number += Character.getNumericValue(character);
		}
		int index = (int) (number % Material.values().length);
		ItemStack item = new ItemStack(Material.values()[index]);
		item.editMeta(meta -> {
			meta.displayName(Component.text(string).color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
			final List<Component> lore = new ArrayList<>();
			lore.add(Component.empty());
			lore.add(Component.text("Linksklick").color(NamedTextColor.GRAY)
					.append(Component.text(" >> ").color(NamedTextColor.DARK_GRAY))
					.append(Component.text("Teleportieren").color(NamedTextColor.GOLD)));
			lore.add(Component.empty());
			meta.lore(lore);
		});
		return item;
	}
	
	public Inventory getInv() {
		return inv;
	}
	
}
