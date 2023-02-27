package org.corderun.cordeheal.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.corderun.cordeheal.CordeHeal;

import java.util.HashMap;
import java.util.UUID;

public class CordeHealCommands extends Commands {

    private final HashMap<UUID, Long> cooldown;

    public CordeHealCommands() {
        super("heal");
        this.cooldown = new HashMap<>();
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        if (!this.cooldown.containsKey(player.getUniqueId())) {

            this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());

            if(args.length == 0 && !sender.hasPermission("cordeheal.heal.self")){
                sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.no-perms").replace("&", "§"));
                cooldown.clear();
                return;
            }
            if (args.length == 0 && sender.hasPermission("cordeheal.heal.self") && sender.hasPermission("cordeheal.bypass")) {
                player.setHealth(20.0);
                player.setFoodLevel(20);
                sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.heal.self").replace("&", "§"));
                cooldown.clear();
                return;
            }
            if (args.length == 0 && sender.hasPermission("cordeheal.heal.self")) {
                player.setHealth(20.0);
                player.setFoodLevel(20);
                sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.heal.self").replace("&", "§"));
                return;
            }
            if (args[0].length() != 0) {
                if (sender.hasPermission("cordeheal.heal.other") && sender.hasPermission("cordeheal.bypass")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.setHealth(20.0);
                        target.setFoodLevel(20);
                        sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.heal.other.sender").replace("%player%", target.getName()).replace("&", "§"));
                        target.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.heal.other.target").replace("%player%", sender.getName()).replace("&", "§"));
                    } else {
                        sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.offline").replace("&", "§"));
                    }
                    cooldown.clear();
                    return;
                }
                if (!sender.hasPermission("cordeheal.heal.other")) {
                    player.setHealth(20.0);
                    player.setFoodLevel(20);
                    sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.heal.self").replace("&", "§"));
                    return;
                }
                if (sender.hasPermission("cordeheal.heal.other")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.setHealth(20.0);
                        target.setFoodLevel(20);
                        sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.heal.other.sender").replace("%player%", target.getName()).replace("&", "§"));
                        target.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.heal.other.target").replace("%player%", sender.getName()).replace("&", "§"));
                    } else {
                        sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.offline").replace("&", "§"));
                        cooldown.clear();
                    }
                }
            }
        } else {
            long timeElapsed = System.currentTimeMillis() - cooldown.get(player.getUniqueId());

            if (timeElapsed >= (CordeHeal.getInstance().getConfig().getInt("cooldown"))) {

                this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());

                if(args.length == 0 && !sender.hasPermission("cordeheal.heal.self")){
                    sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.no-perms").replace("&", "§"));
                    cooldown.clear();
                    return;
                }
                if (args.length == 0 && sender.hasPermission("cordeheal.heal.self") && sender.hasPermission("cordeheal.bypass")) {
                    player.setHealth(20.0);
                    player.setFoodLevel(20);
                    sender.sendMessage(CordeHeal.getInstance().getConfig().getString("heal.self").replace("&", "§"));
                    cooldown.clear();
                    return;
                }
                if (args.length == 0 && sender.hasPermission("cordeheal.heal.self")) {
                    player.setHealth(20.0);
                    player.setFoodLevel(20);
                    sender.sendMessage(CordeHeal.getInstance().getConfig().getString("heal.self").replace("&", "§"));
                    return;
                }
                if (args[0].length() != 0) {
                    if (!sender.hasPermission("cordeheal.heal.other")) {
                        player.setHealth(20.0);
                        player.setFoodLevel(20);
                        sender.sendMessage(CordeHeal.getInstance().getConfig().getString("heal.self").replace("&", "§"));
                        return;
                    }
                    if (sender.hasPermission("cordeheal.heal.other") && sender.hasPermission("cordeheal.bypass")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            target.setHealth(20.0);
                            target.setFoodLevel(20);
                            sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.heal.other.sender").replace("%player%", target.getName()).replace("&", "§"));
                            target.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.heal.other.target").replace("%player%", sender.getName()).replace("&", "§"));
                        } else {
                            sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.offline").replace("&", "§"));
                        }
                        cooldown.clear();
                        return;
                    }
                    if (sender.hasPermission("cordeheal.heal.other")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            target.setHealth(20.0);
                            target.setFoodLevel(20);
                            sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.heal.other.sender").replace("%player%", target.getName()).replace("&", "§"));
                            target.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.heal.other.target").replace("%player%", sender.getName()).replace("&", "§"));
                        } else {
                            sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.offline").replace("&", "§"));
                            cooldown.clear();
                        }
                    }
                }

            } else {
                long cd = ((CordeHeal.getInstance().getConfig().getInt("cooldown")) - timeElapsed) / 1000;
                long cdmin =(int)(cd / 60);
                long cdsec = cd - (cdmin*60);
                if(cdmin != 0 && cdsec != 0) {
                    sender.sendMessage("До следующего использования осталось: " + ChatColor.RED + cdmin + ChatColor.WHITE + " минут " + ChatColor.RED + cdsec + ChatColor.WHITE + " секунд.");
                    return;
                }
                if(cdmin == 0){
                    sender.sendMessage("До следующего использования осталось: " + ChatColor.RED + cdsec + ChatColor.WHITE + " секунд.");
                    return;
                }
                if(cdsec == 0){
                    sender.sendMessage("До следующего использования осталось: " + ChatColor.RED + cdmin + ChatColor.WHITE + " минут.");
                }
            }
        }
    }
}
