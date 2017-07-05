package com.einrandy.testplugin;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by EinRandy on 05.07.2017.
 */
public class main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§3[§6TestPlugin§3] §6Das §4Plugin wurde erfolgreich geladen§6!");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§3[§6TestPlugin§3] §6Das §4Plugin wurde gestoppt§6!");
    }

}