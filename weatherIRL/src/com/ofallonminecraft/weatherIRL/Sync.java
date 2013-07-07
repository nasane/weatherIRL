package com.ofallonminecraft.weatherIRL;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

public class Sync {

	public static void sync(String cc, CommandSender sender) {

		cc = cc.substring(0, cc.indexOf(',')); // current conditions

		boolean precipitating = false;
		boolean thundering    = false;

		if (cc.equalsIgnoreCase("tornado")) {
			precipitating = true;
			thundering    = true;
		} else if (cc.equalsIgnoreCase("tropical storm")) {
			precipitating = true;
			thundering    = true;
		} else if (cc.equalsIgnoreCase("hurricane")) {
			precipitating = true;
			thundering    = true;
		} else if (cc.equalsIgnoreCase("severe thunderstorms")) {
			precipitating = true;
			thundering    = true;
		} else if (cc.equalsIgnoreCase("thunderstorms")) {
			precipitating = true;
			thundering    = true;
		} else if (cc.equalsIgnoreCase("mixed rain and snow")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("mixed rain and sleet")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("mixed snow and sleet")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("freezing drizzle")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("drizzle")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("freezing rain")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("showers")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("snow flurries")) {
		} else if (cc.equalsIgnoreCase("light snow showers")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("blowing snow")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("snow")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("hail")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("sleet")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("dust")) {
		} else if (cc.equalsIgnoreCase("foggy")) {
		} else if (cc.equalsIgnoreCase("haze")) {
		} else if (cc.equalsIgnoreCase("smoky")) {
		} else if (cc.equalsIgnoreCase("blustery")) {
		} else if (cc.equalsIgnoreCase("windy")) {
		} else if (cc.equalsIgnoreCase("cold")) {
		} else if (cc.equalsIgnoreCase("cloudy")) {
		} else if (cc.equalsIgnoreCase("mostly cloudy")) {
		} else if (cc.equalsIgnoreCase("partly cloudy")) {
		} else if (cc.equalsIgnoreCase("clear")) {
		} else if (cc.equalsIgnoreCase("sunny")) {
		} else if (cc.equalsIgnoreCase("fair")) {
		} else if (cc.equalsIgnoreCase("mixed rain and hail")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("hot")) {
		} else if (cc.equalsIgnoreCase("isolated thunderstorms")) {
			precipitating = true;
			thundering    = true;
		} else if (cc.equalsIgnoreCase("scattered thunderstorms")) {
			precipitating = true;
			thundering    = true;
		} else if (cc.equalsIgnoreCase("scattered showers")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("heavy snow")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("scattered snow showers")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("thundershowers")) {
			precipitating = true;
			thundering    = true;
		} else if (cc.equalsIgnoreCase("snow showers")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("light rain")) {
			precipitating = true;
		} else if (cc.contains("Thunderstorm")) {
			precipitating = true;
			thundering    = true;
		} else if (cc.equalsIgnoreCase("mist")) {
			precipitating = true;
		} else if (cc.equalsIgnoreCase("rain")) {
			precipitating = true;
		} else if (cc.contains("drizzle") || cc.contains("Drizzle")) {
			precipitating = true;
		} else {
			if (sender!=null) {
				sender.sendMessage("Oops, we haven't programmed in the conditions for "
						+cc+". Let us know!");
			} else {
				Bukkit.broadcast("Oops, we haven't programmed in the conditions for "
						+cc+". Let us know!", Server.BROADCAST_CHANNEL_USERS);
			}
		}

		// based on variables defined above, change weather accordingly
		Bukkit.getServer().getWorlds().get(0).setStorm(precipitating);
		Bukkit.getServer().getWorlds().get(0).setThundering(thundering);

	}
}
