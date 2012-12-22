package com.ofallonminecraft.weatherIRL;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class Wirl extends JavaPlugin
{
	boolean syncing = false; // enable syncing on reload and startup?
	// base this on previous condition of this variable?



	// ---------- INITIALIZE HASMAPS TO STORE LOCATION AND ATTRIBUTES ---------- //
	public static String woeid = "";
	public static ArrayList<String> attributes = new ArrayList<String>();
	// ---------- END INITIALIZE HASMAPS TO STORE LOCATION AND ATTRIBUTES ---------- //






	// ---------- MANAGE FILES WHEN ENABLING/DISABLING THE PLUGIN ---------- //
	public void onEnable() // on enable, load files
	{
		try
		{
			if (new File("plugins/weatherIRL/").exists()) {
				if (!(new File("plugins/weatherIRL/woeid.bin").exists())) {
					new File("plugins/weatherIRL/woeid.bin").createNewFile();
					SLAPI.save(woeid, "plugins/weatherIRL/woeid.bin");
				} else {
					woeid = SLAPI.load("plugins/weatherIRL/woeid.bin");
				}
				if (!(new File("plugins/weatherIRL/attributes.bin").exists())) {
					new File("plugins/weatherIRL/attributes.bin").createNewFile();
					SLAPI.save(attributes, "plugins/weatherIRL/attributes.bin");
				} else {
					attributes = SLAPI.load("plugins/weatherIRL/attributes.bin");
				}
			} else {
				new File("plugins/weatherIRL").mkdir();
				new File("plugins/weatherIRL/woeid.bin").createNewFile();
				new File("plugins/weatherIRL/attributes.bin").createNewFile();
				SLAPI.save(woeid, "plugins/weatherIRL/woeid.bin");
				SLAPI.save(attributes, "plugins/weatherIRL/attributes.bin");
			}
			getLogger().info("weatherIRL has been enabled");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// Weather Listener and Synchronous Repeating Task
		int taskID = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				if (syncing) {
					String currentConditions = RSSReader.rssReader("Current Conditions:", woeid);
					
				}
			}
		}, 1L, 60000L);
	}

	public void onDisable() // on disable, save the files
	{
		getLogger().info("weatherIRL has been disabled.");
	}
	// ---------- END MANAGE FILES WHEN ENABLING/DISABLING THE PLUGIN ---------- //






	// ---------- HANDLE THE COMMANDS ---------- //
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{

		// Forecast
		if (cmd.getName().equalsIgnoreCase("forecast")) {
			if (woeid.length()==0) {
				sender.sendMessage("You must set a location first! Use /syncweather [location]");
			} else {
				sender.sendMessage(RSSReader.rssReader("Forecast:", woeid));
			}
			return true;
		}

		// SyncWeather
		if (cmd.getName().equalsIgnoreCase("syncweather")) {
			if (args.length==0 && woeid=="") {
				sender.sendMessage("You must choose a location! Use /syncweather [location]");
				return false;
			}
			if (args.length!=0) {
				String location = "";
				for (int i=0; i<args.length; ++i) {
					// append all arguments into one string (the location)
					location+=args[i]+" ";
				}
				try {
					woeid = FindWOEID.findWOEID(location);
					try {
						SLAPI.save(woeid, "plugins/weatherIRL/woeid.bin");
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			sender.sendMessage("Starting weather syncing for "+woeid+".");
			syncing = true;
			// PERFORM ONE INITIAL SYNC
			return true;
		}

		// StopWeatherSync
		if (cmd.getName().equalsIgnoreCase("stopweathersync")) {
			if (!syncing) {
				sender.sendMessage("No weather syncing is currently taking place!");
				return false;
			}
			sender.sendMessage("Stopping weather syncing.");
			syncing = false;
			return true;
		}

		// SyncAttributes
		if (cmd.getName().equalsIgnoreCase("syncattributes")) {
			if (args.length==0) {
				sender.sendMessage("Must choose attributes!");
				return false;
			}
			attributes = new ArrayList<String>();
			for (int i=0; i<args.length; ++i) {   // append all chosen attributes (if they are valid)
				if (args[i].equalsIgnoreCase("rainandsnow")) {
					attributes.add("rainandsnow");
					sender.sendMessage("Precipitation will sync if/when syncing is enabled.");
				} else if (args[i].equalsIgnoreCase("storms")) {
					attributes.add("storms");
					sender.sendMessage("Storms will sync if/when syncing is enabled.");
				} else if (args[i].equalsIgnoreCase("clouds")) {
					attributes.add("clouds");
					sender.sendMessage("Clouds will sync if/when syncing is enabled.");
				} else if (args[i].equalsIgnoreCase("daycycle")) {
					attributes.add("daycycle");
					sender.sendMessage("DayCycle will sync if/when syncing is enabled.");
				} else {
					sender.sendMessage("Attribute "+args[i]+" not found.  Available syncing options are rainandsnow, storms, clouds, and daycycle.");
				}
				return true;
			}
			return true;
		}

		return false;
	}
	// --------- END HANDLE THE COMMANDS ---------- //
	
}


