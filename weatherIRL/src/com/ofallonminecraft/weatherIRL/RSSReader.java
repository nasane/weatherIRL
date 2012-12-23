package com.ofallonminecraft.weatherIRL;

import java.net.URL;
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
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}
	
}
