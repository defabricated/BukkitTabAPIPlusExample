package pl.defabricated;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import pl.defabricated.bukkittabapiplus.api.TabAPI;
import pl.defabricated.bukkittabapiplus.api.TabList;
import pl.defabricated.bukkittabapiplus.api.TabSlot;

import java.text.SimpleDateFormat;

public class TabListPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String time = df.format(System.currentTimeMillis());
        for(Player player : Bukkit.getOnlinePlayers()) {
            TabList list = TabAPI.createTabListForPlayer(player); //Create TabList for specified player

            list.setSlot(1, "", ChatColor.GOLD + "Time: ", ChatColor.GRAY + time);
            list.setSlot(4, "", ChatColor.BLUE + "Online: ", ChatColor.GRAY + String.valueOf(Bukkit.getOnlinePlayers().length));

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
                            timeSlot.updatePrefixAndSuffix("", ChatColor.GRAY + String.valueOf(time)); //Update suffix with formatted time string
                        }

                        TabSlot playersSlot = list.getSlot(3);
                        if(playersSlot != null) {
                            playersSlot.updatePrefixAndSuffix("", ChatColor.GRAY + String.valueOf(Bukkit.getOnlinePlayers().length));
                        }
                    }
                }
            }
        }, 20L, 20L);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String time = df.format(System.currentTimeMillis());

        TabList list = TabAPI.createTabListForPlayer(player);

        list.setSlot(1, "", ChatColor.GOLD + "Time: ", ChatColor.GRAY + time);
        list.setSlot(4, "", ChatColor.BLUE + "Online: ", ChatColor.GRAY + String.valueOf(Bukkit.getOnlinePlayers().length));

        list.send();
    }

}
