package org.corderun.cordeheal.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.corderun.cordeheal.CordeHeal;
import org.jetbrains.annotations.NotNull;

public abstract class Commands implements CommandExecutor {

    public Commands(String command){
        PluginCommand pluginCommand = CordeHeal.getInstance().getCommand(command);
        if(pluginCommand != null){
            pluginCommand.setExecutor(this);
        }
    }

    public abstract void execute(CommandSender sender, String label, String[] args);
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        execute(sender, label, args);
        return true;
    }
}
