package com.ofallonminecraft.weatherIRL;

import java.io.IOException;

public class SetLocation {
	public void setLocation(String loc) throws IOException {
		String woeid = FindWOEID.findWOEID(loc);
		try {
			SLAPI.save(woeid, "plugins/weatherIRL/woeid.bin");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
