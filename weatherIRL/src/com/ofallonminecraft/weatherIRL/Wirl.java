package com.ofallonminecraft.weatherIRL;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.net.URL;
import java.net.URLClassLoader;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Weather;
import org.bukkit.plugin.java.JavaPlugin;


public class Wirl extends JavaPlugin
{



	// ---------- INITIALIZE VARIABLES TO STORE INFORMATION ---------- //
	public static String            woeid      = "";
	public static ArrayList<String> attributes = new ArrayList<String>();
	public static boolean           syncing    = false;
	// ---------- END INITIALIZE VARIALBES TO STORE INFORMATION ---------- //



	Weather w;  // needs a weather object?



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
				if (!(new File("plugins/weatherIRL/syncing.bin").exists())) {
					new File("plugins/weatherIRL/syncing.bin").createNewFile();
					SLAPI.save(syncing, "plugins/weatherIRL/syncing.bin");
				} else {
					syncing = SLAPI.load("plugins/weatherIRL/syncing.bin");
					if (syncing) {
						// TODO: do a single round of syncing
					}
				}
			} else {
				new File("plugins/weatherIRL").mkdir();
				new File("plugins/weatherIRL/woeid.bin").createNewFile();
				new File("plugins/weatherIRL/attributes.bin").createNewFile();
				new File("plugins/weatherIRL/syncing.bin").createNewFile();
				SLAPI.save(woeid, "plugins/weatherIRL/woeid.bin");
				SLAPI.save(attributes, "plugins/weatherIRL/attributes.bin");
				SLAPI.save(syncing, "plugins/weatherIRL/syncing.bin");
			}
			getLogger().info("weatherIRL has been enabled");
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// adding needed external libraries -- stolen from fletch_to_99 (give him more credit)
		try {
			final File[] libs = new File[] {
					new File(getDataFolder(), "commons-io-2.4.jar"),
					new File(getDataFolder(), "jdom-1.1.3.jar"),
					new File(getDataFolder(), "rome-1.0RC2.jar")};
			for (final File lib : libs) {
				if (!lib.exists()) {
					JarUtils.extractFromJar(lib.getName(),
							lib.getAbsolutePath());
				}
			}
			for (final File lib : libs) {
				if (!lib.exists()) {
					getLogger().warning(
							"There was a critical error loading My plugin! Could not find lib: "
									+ lib.getName());
					Bukkit.getServer().getPluginManager().disablePlugin(this);
					return;
				}
				addClassPath(JarUtils.getJarUrl(lib));
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		// Weather Listener and Synchronous Repeating Task
		int taskID = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				if (syncing) {
					String currentConditions = RSSReader.rssReader("Current Conditions:", woeid);
					int conditionCode = ConditionToCode.conditionToCode(currentConditions);
					Sync.sync(conditionCode, w);
				}
			}
		}, 1L, 60000L);
	}

	// related to adding external libraries -- stolen from fletch_to_99 (give him more credit)
	private void addClassPath(final URL url) throws IOException {
		final URLClassLoader sysloader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		final Class<URLClassLoader> sysclass = URLClassLoader.class;
		try {
			final Method method = sysclass.getDeclaredMethod("addURL",
					new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] { url });
		} catch (final Throwable t) {
			t.printStackTrace();
			throw new IOException("Error adding " + url
					+ " to system classloader");
		}
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


