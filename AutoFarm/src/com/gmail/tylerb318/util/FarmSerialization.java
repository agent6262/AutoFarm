package com.gmail.tylerb318.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.bukkit.Bukkit;

import com.gmail.tylerb318.AutoFarm;
import com.gmail.tylerb318.Farm;

public class FarmSerialization {
	public static AutoFarm mainClass = AutoFarm.getPlugin(AutoFarm.class);

	public static void serializeFarm(Farm farm){
		try{
			OutputStream file = new FileOutputStream(new File(mainClass.getDataFolder()+"Serialized Farms", farm.getName()+".farm.ser"));
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try{
				output.writeObject(farm);
		    }
			finally{
				output.close();
			}
		}  
		catch(IOException ex){
		    	Bukkit.getLogger().severe("Failed to serialize farm ["+farm.getName()+"] froe reason: "+ex.getCause());
		}
	}
	
	public static Farm deSerializeFarm(String farmName){
		try{
			InputStream file = new FileInputStream(new File(mainClass.getDataFolder()+"Serialized Farms", farmName));
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);
			try{
				return (Farm)input.readObject();
			}
			finally{
				input.close();
			}
		}
		catch(ClassNotFoundException | IOException ex){
			mainClass.getLogger().severe("Failed to load some or all Farms for reason: "+ex.getCause());
		}
		return null;
	}
	
	public static ArrayList<Farm> deSerializeFarms(){
		ArrayList<Farm> tmpFarmList = new ArrayList<Farm>();
		for(String fileName : mainClass.getDataFolder().list())
		{
			try{
				InputStream file = new FileInputStream(new File(mainClass.getDataFolder()+"Serialized Farms", fileName));
				InputStream buffer = new BufferedInputStream(file);
				ObjectInput input = new ObjectInputStream (buffer);
				try{
					tmpFarmList.add((Farm)input.readObject());
				}
				finally{
					input.close();
				}
			}
			catch(ClassNotFoundException | IOException ex){
				mainClass.getLogger().severe("Failed to load some or all Farms for reason: "+ex.getCause());
			}
		}
		return tmpFarmList;
	}
}
