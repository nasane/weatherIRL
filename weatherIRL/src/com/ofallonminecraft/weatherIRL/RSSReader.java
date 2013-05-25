package com.ofallonminecraft.weatherIRL;

import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import org.bukkit.Bukkit;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RSSReader {

	public static String rssReader(String lookingFor, String woeid) {
		String ret = "An error occurred.";
		try {
			String address = "http://weather.yahooapis.com/forecastrss?w="+woeid;
			URL feedUrl = new URL(address);

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedUrl));

			String feedString = feed.toString();
			int index = feedString.indexOf(lookingFor);
			if (lookingFor.equalsIgnoreCase("Forecast:")) {
				ret = feedString.substring(index+20, feedString.indexOf("<br />\n<a", index+20));
				ret = ret.replaceAll("<br />", "");
			} else if (lookingFor.equalsIgnoreCase("Current Conditions:")) {
				ret = feedString.substring(index+30, feedString.indexOf("<BR />\n", index+30));
			}

			// TODO: adjust day/night cycle appropriately
			/*
			int timeIndex = feedString.indexOf("titleEx.value=Conditions for"); 
			String timeString = feedString.substring(feedString.indexOf(" at", timeIndex+30)+3, feedString.indexOf('\n', timeIndex+1)-4); 
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
			Date time = timeFormat.parse(timeString);
			long timeInt = ((time.getTime()/1000 - 8*3600)*(15/54))-6000;
			if (timeInt<0) timeInt+=24000;
			Bukkit.getServer().getWorlds().get(0).setTime(timeInt);
			 */

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

}
