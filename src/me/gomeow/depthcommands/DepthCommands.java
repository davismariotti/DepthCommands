package me.gomeow.depthcommands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DepthCommands extends JavaPlugin implements Listener {
	
	private ArrayList<String> forbidden;
	private Integer height;
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		forbidden = ((ArrayList<String>) getConfig().getStringList("Forbidden-Commands"));
		height = getConfig().getInt("Height");
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPreprocess(PlayerCommandPreprocessEvent event) {
		String command = event.getMessage().substring(1);
		if(event.getPlayer().getLocation().getBlockY() > height) return;
		if(event.getPlayer().hasPermission("depthcommands.bypass")) return;
		for(String forbiddenCommand:forbidden) {
			if(command.startsWith(forbiddenCommand)) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED+
						"That command is not allowed below Y"
						+height.toString()+"!");
				break;
			}
		}
	}
}