package com.gmail.tylerb318;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.material.Crops;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoFarm extends JavaPlugin implements Listener
{
	@Override
	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable(){
		
	}
	
	@EventHandler
	public void onGrowEvent(BlockGrowEvent evt){
		Bukkit.getPlayer(UUID.fromString("685742b7-a893-47ca-815e-5f3106d16bab")).sendMessage("grow");
		if(evt.getBlock().getType().equals(Material.CROPS)){
			Bukkit.getPlayer(UUID.fromString("685742b7-a893-47ca-815e-5f3106d16bab")).sendMessage("crop");
			
			if(((Crops)evt.getNewState().getData()).getState() == CropState.RIPE){
				Bukkit.getPlayer(UUID.fromString("685742b7-a893-47ca-815e-5f3106d16bab")).sendMessage("work");
			}
		}
	}
}
