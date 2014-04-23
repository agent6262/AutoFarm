package com.gmail.tylerb318;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.tylerb318.events.BlockEventsListener;
import com.gmail.tylerb318.events.PlayerEventListener;

public class AutoFarm extends JavaPlugin
{
	public YamlConfiguration config;
	public YamlConfiguration playerConfig;
	public YamlConfiguration statusConfig;
	
	public ArrayList<Farm> farmList= new ArrayList<Farm>();
	
	@Override
	public void onEnable(){
		this.loadConfig();
		
		this.getServer().getPluginManager().registerEvents(new BlockEventsListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
	}
	
	@Override
	public void onDisable(){
		
	}
	
	private void loadConfig(){
		try{
			//Loading default configuration
			File configFile = new File(this.getDataFolder(), "config.yml");
			config = YamlConfiguration.loadConfiguration(configFile);
			if(!configFile.exists()){
				this.saveDefaultConfig();
			}
			//Loading player configuration
			File playerFile = new File(this.getDataFolder(), "players.yml");
			playerConfig = YamlConfiguration.loadConfiguration(playerFile);
			if(!playerFile.exists()){
				playerConfig.save(playerFile);
			}
			//Loading status configuration
			File statusFile = new File(this.getDataFolder(), "FarmStatus.yml");
			statusConfig = YamlConfiguration.loadConfiguration(statusFile);
			if(!statusFile.exists()){
				statusConfig.save(statusFile);
			}
		}catch(IOException e){
			this.getLogger().severe("Some or all the configuartions could not be loaded");
		}
	}
}
