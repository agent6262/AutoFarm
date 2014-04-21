package com.gmail.tylerb318.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.material.Crops;

import com.gmail.tylerb318.AutoFarm;
import com.gmail.tylerb318.Farm;

public class BlockEventsListener implements Listener{
	
	public static AutoFarm mainClass = AutoFarm.getPlugin(AutoFarm.class);

	@EventHandler
	public void onGrowEvent(BlockGrowEvent evt){//FIXME
		Bukkit.getPlayer(UUID.fromString("685742b7-a893-47ca-815e-5f3106d16bab")).sendMessage("grow");
		if(evt.getBlock().getType().equals(Material.CROPS)){
			Bukkit.getPlayer(UUID.fromString("685742b7-a893-47ca-815e-5f3106d16bab")).sendMessage("crop");
			
			if(((Crops)evt.getNewState().getData()).getState() == CropState.RIPE){
				Bukkit.getPlayer(UUID.fromString("685742b7-a893-47ca-815e-5f3106d16bab")).sendMessage("work");
			}
		}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent evt){
		if(evt.getLine(0).equals("[Farm]") && mainClass.playerConfig.getInt(evt.getPlayer().getUniqueId()+".TotalFarms") < mainClass.getConfig().getInt("FarmsPerPlayer")){
			if(mainClass.getConfig().getInt("TotalFarms") != 0){
				if(!(mainClass.statusConfig.getInt("TotalFarms") < mainClass.getConfig().getInt("TotalFarms"))){
					evt.getPlayer().sendMessage(ChatColor.RED+"There are to many farms in the world");
					return;
				}
			}
			mainClass.farmList.add(new Farm());
		}
	}
}
