package com.gmail.tylerb318.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gmail.tylerb318.AutoFarm;

public class PlayerEventListener implements Listener{
	public static AutoFarm mainClass = AutoFarm.getPlugin(AutoFarm.class);

	@EventHandler
	public void onePLayerLogin(PlayerJoinEvent evt){
		if(!mainClass.playerConfig.contains(evt.getPlayer().getUniqueId().toString())){
			mainClass.playerConfig.set(evt.getPlayer().getUniqueId().toString()+".Name", evt.getPlayer().getName());
			mainClass.playerConfig.set(evt.getPlayer().getUniqueId().toString()+".TotalFarms", 0);
		}else{
			if(!mainClass.playerConfig.getString(evt.getPlayer().getUniqueId().toString()+".Name").equals(evt.getPlayer().getName())){
				mainClass.playerConfig.set(evt.getPlayer().getUniqueId().toString()+".Name", evt.getPlayer().getName());
			}
		}
	}
}
