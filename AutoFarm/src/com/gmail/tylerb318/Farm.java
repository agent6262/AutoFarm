package com.gmail.tylerb318;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;

public class Farm {
	private int level;
	private boolean isStatic;
	private boolean customRegions;
	
	private Location farmLocation;
	private ArrayList<UUID> owners;
	
	private final int MAX_LEVEL = 5;
	private final int levelZeroRadius = 4;
	private final int levelOneRadius = 10;
	private final int levelTwoRadius = 16;
	private final int levelThreeRadius = 20;
	private final int levelFourRadius = 26;
	private final int levelFiveRadius = 30;
	
	public static AutoFarm mainClass = AutoFarm.getPlugin(AutoFarm.class);
	
	public Farm(){
		level = 0;
		isStatic = mainClass.getConfig().getBoolean("isFarmSizeStatic");
		customRegions = mainClass.getConfig().getBoolean("CustomRegions");
		farmLocation = new Location(mainClass.getServer().getWorld(mainClass.getServer().getWorlds().get(0).getUID()), 0d, 65d, 0d);
		owners = null;
	}

	public Farm(boolean isStatic, Location farmLocation, ArrayList<UUID> owners) {
		this.isStatic = isStatic;
		this.farmLocation = farmLocation;
		this.owners = owners;
	}

	public Farm(int level, boolean isStatic, boolean customRegions, Location farmLocation, ArrayList<UUID> owners) {
		this.level = level;
		this.isStatic = isStatic;
		this.customRegions = customRegions;
		this.farmLocation = farmLocation;
		this.owners = owners;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public int getLevelZeroRadius() {
		return levelZeroRadius;
	}

	public int getLevelOneRadius() {
		return levelOneRadius;
	}

	public int getLevelTwoRadius() {
		return levelTwoRadius;
	}

	public int getLevelThreeRadius() {
		return levelThreeRadius;
	}

	public int getLevelFourRadius() {
		return levelFourRadius;
	}

	public int getLevelFiveRadius() {
		return levelFiveRadius;
	}
}
