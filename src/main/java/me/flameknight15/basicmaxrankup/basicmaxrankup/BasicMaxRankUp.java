package me.flameknight15.basicmaxrankup.basicmaxrankup;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class BasicMaxRankUp extends JavaPlugin {
    Economy econ = null;
    Permission perms = null;
    Retrieval ret = new Retrieval(this);
    RankUp rankup = new RankUp(this);
    FileConfiguration rankUps = YamlConfiguration.loadConfiguration(new File("plugins/Rankup/config.yml"));
    List localSet = rankUps.getStringList("ranks");
    ArrayList<String> ranks = new ArrayList<>();
    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!setupPermissions()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        for (Object configObject = localSet.iterator(); ((Iterator) configObject).hasNext(); ) {
            String rank = (String) ((Iterator) configObject).next();
            ranks.add(rank);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    /**
     * Handles the use of commands
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mru") || cmd.getName().equalsIgnoreCase("maxrankup")) {
            if(args.length == 0){
                if (sender instanceof Player)
                    //if (sender.hasPermission("maxrankup.rankup")) {
                        rankup.rankup((Player) sender);
                        return true;
                    //}
            }
        }
        return true;
    }
}
