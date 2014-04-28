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
import com.gmail.tylerb318.util.FarmSerialization;

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
						farm.getChest().getInventory().addItem(new ItemStack(Material.SEEDS, new Random().nextInt(2)+1));
						if(farm.getChest().getInventory().contains(Material.SEEDS)){
							farm.getChest().getInventory().getItem(farm.getChest().getInventory().first(Material.SEEDS)).setAmount(
								farm.getChest().getInventory().getItem(farm.getChest().getInventory().first(Material.SEEDS)).getAmount()-1);
							((Crops)evt.getNewState().getData()).setState(CropState.GERMINATED);
						}else
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
			if(evt.getPlayer().hasPermission("autofarm.create")){
				if(evt.getLine(1).equals("")){
					evt.setLine(0, ChatColor.RED+"[Farm]");
					evt.setLine(1, ChatColor.RED+"<Farm Name>");
					evt.getPlayer().sendMessage(ChatColor.RED+"You must have a farm name");
					return;
				}
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
				for(Farm farm : mainClass.farmList){
					if(farm.getName().equals(evt.getLine(1))){
						evt.getPlayer().sendMessage(ChatColor.RED+"The farm ["+evt.getLine(1)+"] already exists");
						return;
					}
				}
				evt.setLine(0, ChatColor.GREEN+"[Farm]");
				mainClass.playerConfig.set(evt.getPlayer().getUniqueId().toString()+".TotalFarms", mainClass.playerConfig.getInt(evt.getPlayer().getUniqueId().toString()+".TotalFarms")+1);
				mainClass.statusConfig.set("TotalFarms", mainClass.statusConfig.getInt("TotalFarms")+1);
				Farm tmpFarm = new Farm(evt.getBlock().getWorld().getUID(), evt.getLine(1), tmpBlock.getLocation(), evt.getBlock().getLocation(), new ArrayList<UUID>(Arrays.asList(evt.getPlayer().getUniqueId())));
				mainClass.farmList.add(tmpFarm);
				FarmSerialization.serializeFarm(tmpFarm);
			}
		}
	}
}
