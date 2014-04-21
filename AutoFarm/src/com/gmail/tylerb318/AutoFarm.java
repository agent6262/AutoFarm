package com.gmail.tylerb318;

import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.tylerb318.events.BlockEventsListener;

public class AutoFarm extends JavaPlugin implements Listener
{
	public YamlConfiguration playerConfig;
	public YamlConfiguration statusConfig;
	
	public ArrayList<Farm> farmList= new ArrayList<Farm>();
	
	private BlockEventsListener blockEventListener = new BlockEventsListener();
	
	@Override
	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(blockEventListener, this);
	}
	
	@Override
	public void onDisable(){
		
	}
}
