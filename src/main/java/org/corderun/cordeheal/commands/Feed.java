package org.corderun.cordeheal.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.corderun.cordeheal.CordeHeal;

import java.util.HashMap;
import java.util.UUID;

public class Feed extends Commands{
    private final HashMap<UUID, Long> cooldown;

    public Feed() {
        super("feed");
        this.cooldown = new HashMap<>();
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        if(!this.cooldown.containsKey(player.getUniqueId())) {

            this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());

            if(args.length == 0 && !sender.hasPermission("cordeheal.feed.self")){
                sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.no-perms").replace("&", "§"));
                cooldown.clear();
                return;
            }
            if (args.length == 0 && sender.hasPermission("cordeheal.feed.self") && sender.hasPermission("cordeheal.bypass")) {
                player.setFoodLevel(20);
                sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.self").replace("&", "§"));
                cooldown.clear();
                return;
            }
            if (args.length == 0 && sender.hasPermission("cordeheal.feed.self")) {
                player.setFoodLevel(20);
                sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.self").replace("&", "§"));
                return;
            }
            if (args[0].length() != 0) {
                if (sender.hasPermission("cordeheal.feed.other") && sender.hasPermission("cordeheal.bypass")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.setFoodLevel(20);
                        sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.other.sender").replace("%player%", target.getName()).replace("&", "§"));
                        target.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.other.target").replace("%player%", sender.getName()).replace("&", "§"));
                    } else{
                        sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.offline").replace("&", "§"));
                    }
                    cooldown.clear();
                    return;
                }
                if (!sender.hasPermission("cordeheal.feed.other")) {
                    player.setFoodLevel(20);
                    sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.self").replace("&", "§"));
                    return;
                }
                if (sender.hasPermission("cordeheal.feed.other")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.setFoodLevel(20);
                        sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.other.sender").replace("%player%", target.getName()).replace("&", "§"));
                        target.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.other.target").replace("%player%", sender.getName()).replace("&", "§"));
                    } else{
                        sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.offline").replace("&", "§"));
                        cooldown.clear();
                    }
                }
            }
        }else{
            long timeElapsed = System.currentTimeMillis() - cooldown.get(player.getUniqueId());

            if(timeElapsed >= (CordeHeal.getInstance().getConfig().getInt("cooldown"))){

                this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());

                if(args.length == 0 && !sender.hasPermission("cordeheal.feed.self")){
                    sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.no-perms").replace("&", "§"));
                    cooldown.clear();
                    return;
                }
                if (args.length == 0 && sender.hasPermission("cordeheal.feed.self") && sender.hasPermission("cordeheal.bypass")) {
                    player.setFoodLevel(20);
                    sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.self").replace("&", "§"));
                    cooldown.clear();
                    return;
                }
                if (args.length == 0 && sender.hasPermission("cordeheal.feed.self")) {
                    player.setFoodLevel(20);
                    sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.self").replace("&", "§"));
                    return;
                }
                if (args[0].length() != 0) {
                    if (!sender.hasPermission("cordeheal.feed.other")) {
                        player.setFoodLevel(20);
                        sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.self").replace("&", "§"));
                        return;
                    }
                    if (sender.hasPermission("cordeheal.feed.other") && sender.hasPermission("cordeheal.bypass")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            target.setFoodLevel(20);
                            sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.other.sender").replace("%player%", target.getName()).replace("&", "§"));
                            target.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.other.target").replace("%player%", sender.getName()).replace("&", "§"));
                        } else{
                            sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.offline").replace("&", "§"));
                        }
                        cooldown.clear();
                        return;
                    }
                    if (sender.hasPermission("cordeheal.feed.other")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            target.setFoodLevel(20);
                            sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.other.sender").replace("%player%", target.getName()).replace("&", "§"));
                            target.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.feed.other.target").replace("%player%", sender.getName()).replace("&", "§"));
                        } else{
                            sender.sendMessage(CordeHeal.getInstance().getConfig().getString("messages.offline").replace("&", "§"));
                            cooldown.clear();
                        }
                    }
                }

            }else {
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
