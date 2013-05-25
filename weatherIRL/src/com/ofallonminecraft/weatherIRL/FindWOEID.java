package com.ofallonminecraft.weatherIRL;

import java.io.IOException;

public class FindWOEID {
	public static String findWOEID(String location) throws IOException {
		location = location.replaceAll(" ", "%20");
		location = location.replaceAll(",", "");
		String urlString = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20geo.places%20where%20text%3D%22"+location+"%22&format=xml";
		String html = GetHTML.getHTML(urlString);
		int index1 = html.indexOf("<woeid>") + 7;
		int index2 = html.indexOf("</woeid>");
		return html.substring(index1, index2);
	}

}