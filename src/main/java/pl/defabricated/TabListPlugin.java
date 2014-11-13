package pl.defabricated;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.defabricated.bukkittabapiplus.api.TabAPI;
import pl.defabricated.bukkittabapiplus.api.TabList;
import pl.defabricated.bukkittabapiplus.api.TabSlot;

import java.text.SimpleDateFormat;

public class TabListPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String time = df.format(System.currentTimeMillis());
        for(Player player : Bukkit.getOnlinePlayers()) {
            TabList list = TabAPI.createTabListForPlayer(player); //Create TabList for specified player

            list.setSlot(1, null, ChatColor.GOLD + "Time: ", ChatColor.GRAY + time);
            list.setSlot(3, null, ChatColor.BLUE + "Online: ", ChatColor.GRAY + Bukkit.getOnlinePlayers().lenght);

            list.send(); //Send TabList to player
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() { //Time task
            @Override
            public void run() {
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                String time = df.format(System.currentTimeMillis());
                for(Player player : Bukkit.getOnlinePlayers()) {
                    TabList list = TabAPI.getPlayerTabList(player); //Get player's TabList
                    if(list != null) {
                        TabSlot timeSlot = list.getSlot(1); //Get slot data from TabList
                        if(timeSlot != null) {
                            timeSlot.updatePrefixAndSuffix(null, ChatColor.GRAY + time); //Update suffix with formatted time string
                        }

                        TabSlot playersSlot = list.getSlot(3);
                        if(playersSlot != null) {
                            playersSlot.updatePrefixAndSuffix(null, ChatColor.GRAY + Bukkit.getOnlinePlayers().lenght);
                        }
                    }
                }
            }
        }, 20L, 20L);
    }



}
