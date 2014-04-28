package com.gmail.tylerb318.commands;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.tylerb318.AutoFarm;
import com.gmail.tylerb318.Farm;

public class GeneralInfoCommand implements CommandExecutor
{
	public static AutoFarm mainClass = AutoFarm.getPlugin(AutoFarm.class);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("autofarm")){
			if(args.length == 0){
				sender.sendMessage(ChatColor.GOLD+"AutoFarm Commands:\n"+ChatColor.WHITE+
						"/farm: displays global AutoFarm commands\n"+
						"/farm <farm name> <caoomand>: performs the command specified on that farm\n"+
						"/farminfo: displays global farm info\n"+
						"/farminfo <farm name>: displays info about that farm");
				return true;
			}
			else if(args.length >= 2){
				if(sender instanceof Player){
					Player player = (Player)sender;
					for(Farm farm : mainClass.farmList){
						if(farm.getName().equals(args[0])){
							if(farm.getOwners().contains(player.getUniqueId())){
								switch(args[1]){
								case "setname"://FIXME
									if(sender.hasPermission("autofarm.commands.user.setname")){
										if(args.length == 3){
											farm.setName(args[2]);
											farm.getSign().setLine(1, farm.getName());
											farm.getSign().update(true);
											sender.sendMessage(ChatColor.GREEN+"Farm name changed");
											return true;
										}
										sender.sendMessage(ChatColor.RED+"To many or to few arguments");
										return true;
									}
									break;
								case "addowner"://FIXME
									if(sender.hasPermission("autofarm.commands.user.addowner")){
										if(args.length == 3){
											for(OfflinePlayer offPlayer : Bukkit.getOfflinePlayers()){
												if(offPlayer.getName().equals(args[2])){
													if(mainClass.playerConfig.getInt(offPlayer.getUniqueId()+".TotalFarms") < mainClass.config.getInt("FarmsPerPlayer")){
														farm.addOwner(offPlayer.getUniqueId());
														mainClass.playerConfig.set(offPlayer.getUniqueId().toString()+".TotalFarms", mainClass.playerConfig.getInt(offPlayer.getUniqueId().toString()+".TotalFarms")+1);
														sender.sendMessage(ChatColor.GREEN+"The player ["+args[2]+"] was added to farm ["+args[0]+"]");
														return true;
													}
													sender.sendMessage(ChatColor.RED+"The player ["+args[2]+"] is in to many farms");
													return true;
												}
											}
											sender.sendMessage(ChatColor.RED+"The player ["+args[2]+"] does not exist");
											return true;
										}
										sender.sendMessage(ChatColor.RED+"To many or to few arguments");
										return true;
									}
									break;
								case "upgrade"://FIXME
									if(sender.hasPermission("autofarm.commands.user.upgrade")){
										if(farm.getLevel() < Farm.getMAX_LEVEL()){
											if(Bukkit.getWorld(Bukkit.getWorlds().get(0).getUID()).getBlockAt(farm.getChestLocation()[0],
													farm.getChestLocation()[1]-1,
													farm.getChestLocation()[2]).getType().equals(Material.getMaterial(mainClass.config.getString("FarmUpgardes."+String.valueOf(farm.getLevel()+1))))){
												farm.setLevel(farm.getLevel()+1);
												sender.sendMessage(ChatColor.GREEN+"Farm upgraded to level: "+farm.getLevel());
												return true;
											}
											sender.sendMessage(ChatColor.RED+"You need an ["+mainClass.config.getString("FarmUpgardes."+String.valueOf(farm.getLevel()+1))+"] block under the chest to upgrade the farm");
											return true;
										}
										sender.sendMessage(ChatColor.RED+"This farm is already at max level");
										return true;
									}
									break;
								case "disband":
									if(sender.hasPermission("autofarm.commands.user.disband")){
										for(UUID uuid : farm.getOwners()){
											mainClass.playerConfig.set(uuid.toString()+".TotalFarms", mainClass.playerConfig.getInt(uuid.toString()+".TotalFarms")-1);
										}
										new File(mainClass.getDataFolder()+"Serialized Farms", farm.getUuid().toString()+".farm.ser").delete();
										mainClass.farmList.remove(farm);
										sender.sendMessage(ChatColor.GREEN+"Farm deleted");
										return true;
									}
									break;
								case "leave":
									if(sender.hasPermission("autofarm.commands.user.leave")){
										mainClass.playerConfig.set(player.getUniqueId().toString()+".TotalFarms", mainClass.playerConfig.getInt(player.getUniqueId().toString()+".TotalFarms")-1);
										farm.getOwners().remove(player.getUniqueId());
										sender.sendMessage(ChatColor.GREEN+"You have left the farm ["+farm.getName()+"]");
										return true;
									}
									break;
								default:
									sender.sendMessage(ChatColor.RED+"That is not a valid command");
									return true;
								}
							}
							sender.sendMessage(ChatColor.RED+"You are not an owner of this farm");
							return true;
						}
					}
					sender.sendMessage(ChatColor.RED+"The farm ["+args[0]+"] does not exist");
					return true;
				}
				sender.sendMessage("You have to be a player to use this command");
				return true;
			}
			sender.sendMessage(ChatColor.RED+"To few arguments");
			return true;
		}
		return false;
	}

}
