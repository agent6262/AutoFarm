package com.gmail.tylerb318.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.tylerb318.AutoFarm;
import com.gmail.tylerb318.Farm;

public class FarmInformation implements CommandExecutor
{
	public static AutoFarm mainClass = AutoFarm.getPlugin(AutoFarm.class);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		//Global farm info/statistics command
		if(cmd.getName().equalsIgnoreCase("farminfo") && args.length == 0){
			switch(args.length){
			case 0:
				sender.sendMessage(ChatColor.GREEN+"Total Farms: "+mainClass.statusConfig.getInt("TotalFarms"));
				return true;
			case 1:
				for(Farm farm: mainClass.farmList){
					if(farm.getName().equals(args[0])){
						ArrayList<String> tmpOwners = new ArrayList<String>();
						for(UUID id : farm.getOwners()){
							tmpOwners.add(Bukkit.getPlayer(id).getName());
						}
						sender.sendMessage(ChatColor.GOLD+"===== "+args[0]+" ====="+"\n"+
												ChatColor.GREEN+"Owners: "+ChatColor.WHITE+tmpOwners.toString()+"\n"+
												ChatColor.GREEN+"Level: "+ChatColor.WHITE+farm.getLevel()+"\n");
						return true;
					}
				}
				sender.sendMessage(ChatColor.RED+"The farm ["+args[0]+"] does not exist");
				return true;
			}
		}
		return false;
	}

}
