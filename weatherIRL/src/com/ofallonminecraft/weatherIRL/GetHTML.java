package com.ofallonminecraft.weatherIRL;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;

public class GetHTML {

	public static String getHTML(String urlString) throws IOException {
		URL url = new URL(urlString);
		URLConnection con = url.openConnection();
		Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
		Matcher m = p.matcher(con.getContentType());
		/* If Content-Type doesn't match this pre-conception, choose default and 
		 * hope for the best. */
		String charset = m.matches() ? m.group(1) : "ISO-8859-1";
		String str = IOUtils.toString(con.getInputStream(), charset);
		return str;
	}
}
