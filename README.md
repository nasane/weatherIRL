# weatherIRL v0.10

Author: Nathan Bossart  
Website: <http://ofallonminecraft.com/>  
Contact: <info@ofallonminecraft.com>  


# Description:
The weatherIRL plugin allows you to sync the weather in your Bukkit server with any location in real life. Choose which attributes to sync, including precipitation (various forms), cloudiness, and the day/night cycle.  

The commands added by this plugin are:

    /syncweather      [location]                              : choose a location and begin syncing weather
    /syncattributes   [attribute1(,attribute2,attribute3...)] : choose which weather attributes to sync* (will be added in a later release)
    /stopweathersync                                          : stop syncing weather with the location
    /forecast                                                 : view predicted weather for the location

    *Attributes that can be included: rainandsnow, storms, clouds, daycycle.  By default, all attributes are synced.

This plugin is still in its early stages, as a number of new features and cleaned-up interfaces will soon be added.  Visit <http://github.com/ofallonminecraft/weatherIRL> for the lastest code!


# Instructions for use:

Download the plugin from http://dev.bukkit.org/server-mods/weatherIRL.  Place in your plugins folder for your Bukkit server, and reload the server.  Use the commands listed above to select a location and attributes to sync.  Likewise, see the forecast for the location using /forecast.

# Changelog
### v0.10  
  -First alpha release.  
  -Can sync precipitation and lightning with any location.  
  -Can stop weather syncing.  
  -Can view forecast.  
  -Notes: /syncattributes is not yet ready, and the day/night cycle cannot yet be synced.  

### v0.01  
  -Initial implementation.  
