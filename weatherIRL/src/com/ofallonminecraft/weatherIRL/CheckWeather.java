package com.ofallonminecraft.weatherIRL;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Weather;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckWeather extends BukkitRunnable {

	private final JavaPlugin plugin;

	public CheckWeather(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public void run() {

		boolean syncing = false;
		String  woeid   = null;
		Weather w       = null;
		String forecast;
		try {
			syncing = SLAPI.load("plugins/weatherIRL/syncing.bin");
			woeid   = SLAPI.load("plugins/weatherIRL/woeid.bin");
			w       = SLAPI.load("plugins/weatherIRL/weather.bin");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (syncing) { // store syncing, w, and woeid in a file??
			String currentConditions = RSSReader.rssReader("Current Conditions:", woeid);
			Sync.sync(currentConditions);
			Bukkit.broadcast("Synced with current weather condition. Time is "+Bukkit.getServer().getWorlds().get(0).getTime(), Server.BROADCAST_CHANNEL_ADMINISTRATIVE);
		}
		
		if (woeid.length()==0) {
			forecast = "You must set a location first! Use /syncweather [location]";
		} else {
			forecast = RSSReader.rssReader("Forecast:", woeid);
		}
		
		try {
			SLAPI.save(syncing,  "plugins/weatherIRL/syncing.bin");
			SLAPI.save(woeid,    "plugins/weatherIRL/woeid.bin");
			SLAPI.save(w,        "plugins/weatherIRL/weather.bin");
			SLAPI.save(forecast, "plugins/weatherIRL/forecast.bin");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
