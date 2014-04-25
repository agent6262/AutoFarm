package com.gmail.tylerb318.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.tylerb318.AutoFarm;
import com.gmail.tylerb318.Farm;
import com.gmail.tylerb318.util.FarmSerialization;

public class ReloadCommand implements CommandExecutor {
	public static AutoFarm mainClass = AutoFarm.getPlugin(AutoFarm.class);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("autofarm.commands.reload")){
			try {
				mainClass.playerConfig.save(new File(mainClass.getDataFolder(), "players.yml"));
				mainClass.statusConfig.save(new File(mainClass.getDataFolder(), "FarmStatus.yml"));
			} catch (IOException e) {
				Bukkit.getLogger().severe("Could not save aome or all of the configuration files");
			}
			for(Farm farm : mainClass.farmList){
				FarmSerialization.serializeFarm(farm);
			}
			mainClass.playerConfig = YamlConfiguration.loadConfiguration(new File(mainClass.getDataFolder(), "players.yml"));
			mainClass.statusConfig = YamlConfiguration.loadConfiguration(new File(mainClass.getDataFolder(), "FarmStatus.yml"));
			mainClass.config = YamlConfiguration.loadConfiguration(new File(mainClass.getDataFolder(), "config.yml"));
			
			mainClass.farmList = FarmSerialization.deSerializeFarms();
			sender.sendMessage(ChatColor.GREEN+"Reload complete");
			return true;
		}
		return false;
	}

}
