package com.gmail.tylerb318;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

public class Farm {//FIXME serialize farms
	private int level;
	private boolean isStatic;
	private boolean customRegions;
	
	private Location farmLocation;
	private Chest chest;
	private ArrayList<UUID> owners;
	private int[][] bounds;
	
	private final int MAX_LEVEL = 5;
	private final int LEVEL_ZER0_RADIUS = 4;
	private final int LEVEL_ONE_RADIUS = 10;
	private final int LEVEL_TWO_RADIUS = 16;
	private final int LEVEL_THREE_RADIUS = 20;
	private final int LEVEL_FOUR_RADIUS = 26;
	private final int LEVEL_FIVE_RADIUS = 30;
	
	public static AutoFarm mainClass = AutoFarm.getPlugin(AutoFarm.class);
	
	public Farm(){
		isStatic = mainClass.getConfig().getBoolean("isFarmSizeStatic");
		customRegions = mainClass.getConfig().getBoolean("CustomRegions");
		farmLocation = new Location(mainClass.getServer().getWorld(mainClass.getServer().getWorlds().get(0).getUID()), 0d, 65d, 0d);
		owners = null;
		bounds = new int[2][4];
		setLevel(0);
	}

	public Farm(Location farmLocation, ArrayList<UUID> owners) {
		this.isStatic = mainClass.getConfig().getBoolean("isFarmSizeStatic");
		this.farmLocation = farmLocation;
		this.chest = (Chest)farmLocation.getBlock().getState();
		this.owners = owners;
		bounds = new int[2][4];
		setLevel(0);
	}

	public Farm(int level, boolean isStatic, boolean customRegions, Location farmLocation, ArrayList<UUID> owners) {
		bounds = new int[2][4];
		setLevel(level);
		this.isStatic = isStatic;
		this.customRegions = customRegions;
		this.farmLocation = farmLocation;
		this.owners = owners;
	}
	
	public boolean containsBlock(Block b){
		if(b.getX() <= bounds[0][0] && b.getX() >= bounds[0][2] &&
			b.getZ() <= bounds[0][1] && b.getZ() >= bounds[1][1] &&
			b.getY() == farmLocation.getY()) return true;
		return false;
	}

	public int getLevel() {
		return level;
	}
	
	public int getLevelValue(int level){
		switch(level){
		case 0:
			return this.LEVEL_ZER0_RADIUS;
		case 1:
			return this.LEVEL_ONE_RADIUS;
		case 2:
			return this.LEVEL_TWO_RADIUS;
		case 3:
			return this.LEVEL_THREE_RADIUS;
		case 4:
			return this.LEVEL_FOUR_RADIUS;
		case 5:
			return this.LEVEL_FIVE_RADIUS;
		}
		return -1;
	}

	public void setLevel(int level) {
		this.level = level;
		bounds[0][0] = this.farmLocation.getBlockX()-getLevelValue(getLevel()); bounds[0][1] = this.farmLocation.getBlockZ()+getLevelValue(getLevel());
		bounds[0][2] = this.farmLocation.getBlockX()+getLevelValue(getLevel()); bounds[0][3] = this.farmLocation.getBlockZ()+getLevelValue(getLevel());
		bounds[1][0] = this.farmLocation.getBlockX()-getLevelValue(getLevel()); bounds[1][0] = this.farmLocation.getBlockZ()-getLevelValue(getLevel());
		bounds[1][2] = this.farmLocation.getBlockX()+getLevelValue(getLevel()); bounds[1][3] = this.farmLocation.getBlockZ()-getLevelValue(getLevel());
		
	}

	public Location getFarmLocation() {
		return farmLocation;
	}

	public void setFarmLocation(Location farmLocation) {
		this.farmLocation = farmLocation;
	}

	public ArrayList<UUID> getOwners() {
		return owners;
	}

	public void setOwners(ArrayList<UUID> owners) {
		this.owners = owners;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public boolean isCustomRegions() {
		return customRegions;
	}

	public int getMAX_LEVEL() {
		return MAX_LEVEL;
	}

	public Chest getChest()
	{
		return chest;
	}

	public void setChest(Chest chest)
	{
		this.chest = chest;
	}
}
