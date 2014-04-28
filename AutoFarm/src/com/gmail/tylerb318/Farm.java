package com.gmail.tylerb318;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;

public class Farm implements Serializable{
	private static final long serialVersionUID = 3L;
	private final UUID uuid = UUID.randomUUID();
	
	private int level;
	private String name;
	
	private UUID world;
	private ArrayList<UUID> owners;
	private int[] chestLocation;
	private int[] signLocation;
	private int[][] bounds;
	
	private static final int MAX_LEVEL = 5;
	private static final int LEVEL_ZER0_RADIUS = 4;
	private static final int LEVEL_ONE_RADIUS = 10;
	private static final int LEVEL_TWO_RADIUS = 16;
	private static final int LEVEL_THREE_RADIUS = 20;
	private static final int LEVEL_FOUR_RADIUS = 26;
	private static final int LEVEL_FIVE_RADIUS = 30;
	
	public static transient AutoFarm mainClass = AutoFarm.getPlugin(AutoFarm.class);

	public Farm(UUID world, String name, Location farmLocation, Location signLocation, ArrayList<UUID> owners) {
		this.world = world;
		this.setName(name);
		this.setOwners(owners);
		
		this.chestLocation = new int[3];
		this.setFarmLocation(farmLocation.getBlockX(), farmLocation.getBlockY(), farmLocation.getBlockZ());
		
		this.signLocation = new int[3];
		this.setSignLocation(signLocation.getBlockX(), signLocation.getBlockY(), signLocation.getBlockZ());
		bounds = new int[2][4];
		setLevel(0);
	}
	
	public void addOwner(UUID owner){
		this.owners.add(owner);
	}
	
	public boolean containsBlock(Block b){
		if(b.getX() <= bounds[0][0] && b.getX() >= bounds[1][2] &&
			b.getZ() >= bounds[0][1] && b.getZ() <= bounds[1][3]) return true;
		return false;
	}

	public int getLevel() {
		return level;
	}
	
	private int getLevelValue(int level){
		switch(level){
		case 0:
			return Farm.LEVEL_ZER0_RADIUS;
		case 1:
			return Farm.LEVEL_ONE_RADIUS;
		case 2:
			return Farm.LEVEL_TWO_RADIUS;
		case 3:
			return Farm.LEVEL_THREE_RADIUS;
		case 4:
			return Farm.LEVEL_FOUR_RADIUS;
		case 5:
			return Farm.LEVEL_FIVE_RADIUS;
		}
		return -1;
	}

	public void setLevel(int level) {
		this.level = level;
		bounds[0][0] = this.chestLocation[0]+getLevelValue(getLevel()); bounds[0][1] = this.chestLocation[2]-getLevelValue(getLevel());
		bounds[0][2] = bounds[0][0]; bounds[0][3] = this.chestLocation[2]+getLevelValue(getLevel());
		bounds[1][0] = this.chestLocation[0]-getLevelValue(getLevel()); bounds[1][1] = this.chestLocation[2]-getLevelValue(getLevel());
		bounds[1][2] = bounds[1][0]; bounds[1][3] = this.chestLocation[2]+getLevelValue(getLevel());
		
	}

	public int[] getChestLocation()
	{
		return chestLocation;
	}

	public int[] getSignLocation()
	{
		return signLocation;
	}

	public void setFarmLocation(int x, int y, int z) {
		this.chestLocation[0] = x;
		this.chestLocation[1] = y;
		this.chestLocation[2] = z;
	}
	
	public void setSignLocation(int x, int y, int z){
		this.signLocation[0] = x;
		this.signLocation[1] = y;
		this.signLocation[2] = z;
	}

	public ArrayList<UUID> getOwners() {
		return owners;
	}

	public void setOwners(ArrayList<UUID> owners) {
		this.owners = owners;
	}

	public static int getMAX_LEVEL() {
		return MAX_LEVEL;
	}

	public Chest getChest(){
		return (Chest)Bukkit.getWorld(world).getBlockAt(this.chestLocation[0], this.chestLocation[1], this.chestLocation[2]).getState();
	}
	
	public Sign getSign(){
		return (Sign)Bukkit.getWorld(world).getBlockAt(this.signLocation[0], this.signLocation[1], this.signLocation[2]).getState();
	}

	public void setChest(Chest chest)
	{
		this.setFarmLocation(chest.getX(), chest.getY(), chest.getZ());
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public UUID getWorld()
	{
		return world;
	}

	public UUID getUuid()
	{
		return uuid;
	}
}
