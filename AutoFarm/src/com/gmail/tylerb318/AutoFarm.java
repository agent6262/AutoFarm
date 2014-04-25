package com.gmail.tylerb318;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.tylerb318.commands.FarmInformation;
import com.gmail.tylerb318.commands.GeneralInfoCommand;
import com.gmail.tylerb318.commands.ReloadCommand;
import com.gmail.tylerb318.events.BlockEventsListener;
import com.gmail.tylerb318.events.PlayerEventListener;
import com.gmail.tylerb318.util.FarmSerialization;

public class AutoFarm extends JavaPlugin
{
	public YamlConfiguration config;
	public YamlConfiguration playerConfig;
	public YamlConfiguration statusConfig;
	
	public ArrayList<Farm> farmList= new ArrayList<Farm>();
	
	@Override
	public void onEnable(){
		this.loadConfig();
		
		FarmSerialization.deSerializeFarms();
		
		this.getServer().getPluginManager().registerEvents(new BlockEventsListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
		
		this.getCommand("autofarm").setExecutor(new GeneralInfoCommand());
		this.getCommand("autofarminfo").setExecutor(new FarmInformation());
		this.getCommand("autofarmreload").setExecutor(new ReloadCommand());
		
		/*this.getServer().getScheduler().scheduleSyncRepeatingTask(this,
			new Runnable(){
				@Override
				public void run(){
					
				}
		}, 1200L, 2400L);*/
	}
	
	@Override
	public void onDisable(){
		try {
			playerConfig.save(new File(this.getDataFolder(), "players.yml"));
			statusConfig.save(new File(this.getDataFolder(), "FarmStatus.yml"));
		} catch (IOException e) {
			Bukkit.getLogger().severe("Could not save aome or all of the configuration files");
		}
		for(Farm farm : this.farmList){
			FarmSerialization.serializeFarm(farm);
		}
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
			//Make the [Serialized Farms] folder
			File farmDir = new File(this.getDataFolder(), "Serialized Farms");
			if(!farmDir.exists()){
				farmDir.mkdir();
			}
		}catch(IOException e){
			this.getLogger().severe("Some or all the configuartions could not be loaded");
		}
	}
}
