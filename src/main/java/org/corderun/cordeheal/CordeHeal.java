package org.corderun.cordeheal;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.corderun.cordeheal.commands.CordeHealCommands;
import org.corderun.cordeheal.commands.Feed;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class CordeHeal extends JavaPlugin {

    private static CordeHeal instance;
    @Override
    public void onEnable() {
        instance = this;
        new CordeHealCommands();
        new Feed();
        saveDefaultConfig();
        getCommand("cordeheal").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
                if(args.length == 0 && !sender.hasPermission("cordeheal.admin")){
                    sender.sendMessage(ChatColor.GREEN + "CordeHeal 1.0");
                    sender.sendMessage("Автор: " + ChatColor.RED + "CorderuN");
                    sender.sendMessage("Сделано специально для MineLandy");
                    return true;
                }
                if(args.length == 0 && sender.hasPermission("cordeheal.admin")){
                    sender.sendMessage("/cordeheal reload");
                    return true;
                }
                if(args[0].equalsIgnoreCase("reload") && !sender.hasPermission("cordeheal.admin")){
                    sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.no-perms").replace("&", "§"));
                }
                if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("cordeheal.admin")){
                    reloadConfig();
                    sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.config").replace("&", "§"));
                }
                return true;
            }
        });
    }

    public static CordeHeal getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
