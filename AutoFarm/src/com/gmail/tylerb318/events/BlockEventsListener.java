package com.gmail.tylerb318.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.material.Sign;

import com.gmail.tylerb318.AutoFarm;
import com.gmail.tylerb318.Farm;

public class BlockEventsListener implements Listener{
	
	public static AutoFarm mainClass = AutoFarm.getPlugin(AutoFarm.class);

	@EventHandler
	public void onGrowEvent(BlockGrowEvent evt){//FIXME
		if(evt.getBlock().getType().equals(Material.CROPS)){
			if(((Crops)evt.getNewState().getData()).getState() == CropState.RIPE){
				for(Farm farm : mainClass.farmList){
					if(farm.containsBlock(evt.getBlock())){
						evt.getNewState().update();
						for(ItemStack e : evt.getNewState().getBlock().getDrops())
							farm.getChest().getInventory().addItem(e);
						farm.getChest().getInventory().addItem(new ItemStack(Material.SEEDS, new Random().nextInt(3)));
						evt.getNewState().setType(Material.AIR);
						return;
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent evt){
		if(evt.getLine(0).equals("[Farm]")){
			if(mainClass.playerConfig.getInt(evt.getPlayer().getUniqueId()+".TotalFarms") >= mainClass.getConfig().getInt("FarmsPerPlayer")){
				evt.setLine(0, ChatColor.RED+"[Farm]");
				evt.getPlayer().sendMessage(ChatColor.RED+"You have to may farms");
				return;
			}
			if(mainClass.getConfig().getInt("TotalFarms") != 0){
				if(!(mainClass.statusConfig.getInt("TotalFarms") < mainClass.getConfig().getInt("TotalFarms"))){
					evt.getPlayer().sendMessage(ChatColor.RED+"There are to many farms in the world");
					return;
				}
			}
			Sign sign = (Sign)evt.getBlock().getState().getData();
			Block tmpBlock = evt.getBlock().getRelative(sign.getAttachedFace());
			if(tmpBlock.getType() != Material.CHEST){
				evt.setLine(0, ChatColor.RED+"[Farm]");
				return;
			}
			evt.setLine(0, ChatColor.GREEN+"[Farm]");
			mainClass.playerConfig.set(evt.getPlayer().getUniqueId().toString()+".TotalFarms", mainClass.playerConfig.getInt(evt.getPlayer().getUniqueId().toString()+".TotalFarms")+1);
			mainClass.statusConfig.set("TotalFarms", mainClass.statusConfig.getInt("TotalFarms")+1);
			mainClass.farmList.add(new Farm(tmpBlock.getLocation(), new ArrayList<UUID>(Arrays.asList(evt.getPlayer().getUniqueId()))));
		}
	}
}
