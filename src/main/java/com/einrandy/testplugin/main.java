package com.einrandy.testplugin;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Arrays;
import org.bukkit.event.Event;
import java.io.Console;
import java.util.Collection;
import java.util.Date;


/**
 * Created by EinRandy on 05.07.2017.
 */
public class main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        Bukkit.getConsoleSender().sendMessage("\n\n______   ___   _   _ ______ __   __ ______  _   _  _      _____  ______\n| ___ \\ / _ \\ | \\ | ||  _  \\\\ \\ / / | ___ \\| | | || |    |  ___||___  /\n| |_/ // /_\\ \\|  \\| || | | | \\ V /  | |_/ /| | | || |    | |__     / / \n|    / |  _  || . ` || | | |  \\ /   |    / | | | || |    |  __|   / /  \n| |\\ \\ | | | || |\\  || |/ /   | |   | |\\ \\ | |_| || |____| |___ ./ /___\n\\_| \\_|\\_| |_/\\_| \\_/|___/    \\_/   \\_| \\_| \\___/ \\_____/\\____/ \\_____/\n                                                                       \n                                                                       ");

        Bukkit.getConsoleSender().sendMessage("§3[§6RandyPlugin§3] §6Das §4Registriere Events...§6!");

        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        Bukkit.getConsoleSender().sendMessage("§3[§6RandyPlugin§3] §6Das §4Plugin wurde erfolgreich geladen§6!");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§3[§6RandyPlugin§3] §6Das §4Plugin wurde gestoppt§6!");
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {

        getLogger().info("TEST-Command:" + cmd.getName());

        if (cmd.getName().equalsIgnoreCase("randy")) {
            getLogger().info("RANDY RUNS:" + Arrays.toString(args));
            for (String a:args) {

                switch (a.toLowerCase())
                {
                    case "help":
                        getLogger().info("RANDY HELP EXECUTED!");

                        if (cs instanceof Player) {
                            final Player p = (Player)cs;
                            p.sendMessage("/randy start, /randy team add (teamname) (Spieler1) (Spieler2)");
                        }
                        return true;
                    case "start":
                        getLogger().info("RANDY START EXECUTED!");
                        startTimer();
                        return true;
                    default:
                         getLogger().info("UNKNOWN RANDY COMMAND:" + a);
                         return false;
                }
            }

        }
        return true;
    }

    boolean blockActionDisabled=false;

    void startTimer()
    {
        blockActionDisabled=true;

        Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
            public void run() {
                int seconds=15;
                Date dt = new Date();
                long startseconds = dt.getTime() / 1000;
                long actualseconds = startseconds;

                Collection<? extends Player> allPlayers = Bukkit.getOnlinePlayers();

                while(actualseconds<(startseconds+seconds)) {
                //getLogger().info("RANDY TIMER:"+Long.toString(actualseconds));
                    Date dtTemp = new Date();
                    if (dtTemp.getTime() / 1000 > actualseconds) {
                        for (Player p : allPlayers) {
                            long current = seconds - (actualseconds - startseconds);
                            p.sendMessage("Noch " + Long.toString(current) + " Sekunden bis zum Start");
                        }
                        actualseconds = dtTemp.getTime() / 1000;
                    }
                }
                for(Player p:allPlayers) {
                    long current = actualseconds - startseconds;
                    p.sendMessage("Auf in den Kampf!!");
                }
                blockActionDisabled=false;
            }
        });
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event)
    {
        event.setCancelled(blockActionDisabled);
       /* if(blockActionDisabled)
            getLogger().info("RANDY onBlockPlaceEvent blockActionDisabled!");*/
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        e.setCancelled(blockActionDisabled);
        Player player = e.getPlayer();
        Block block = e.getBlock();
        //player.sendMessage("onBlockBreak");
        if(blockActionDisabled) {
            restoreBlock(block,5);
            //player.sendMessage("nicht erlaubt!!!");
        }

    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent e) {
        e.setCancelled(blockActionDisabled);
        Player player = e.getPlayer();
        Block block = e.getBlock();
        //player.sendMessage("onBlockDamage");
        /*if(blockActionDisabled) {
            restoreBlock(block,1);
            player.sendMessage("nicht erlaubt!!!");
        }*/
    }

    public void restoreBlock(final Block block, long timeInSecs) {

        final int b4id = block.getTypeId();
        final byte b4data = block.getData();

        //block.setType(Material.AIR);

        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            public void run() {
                block.setTypeId(b4id);
                block.setData(b4data);
            }
        }, timeInSecs *20);
    }
}