package com.mcmiddleearth.thehelper;


import static com.mcmiddleearth.thehelper.TheHelper.THccl;
import static com.mcmiddleearth.thehelper.TheHelper.THccm;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Commands implements TabExecutor{
    
    public static String auth = "-1";
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
           Player player = (Player) sender;
           if(command.getName().equalsIgnoreCase("helper")){
               if(args.length > 0){
                   if(args[0].equalsIgnoreCase("teamspeak")||args[0].equalsIgnoreCase("ts")){
                       player.sendMessage(THccm+"TeamSpeak is how mcme communicates on projects. Download TeamSpeak 3 here: " + TheHelper.pluginInstance.urls.get(args[0].toLowerCase()));
                       player.sendMessage(THccm+"To connect to TeamSpeak, click here: " + THccl + "http://tinyurl.com/nuclz3f/?nickname="+player.getName());
                       return true;
                   }
                   else if(args[0].equalsIgnoreCase("forums")){
                       player.sendMessage(THccm+"The forums are where discussions take place and where you can find help. Visit: " + THccl + TheHelper.pluginInstance.urls.get(args[0].toLowerCase()));
                       return true;
                   }
                   else if(args[0].equalsIgnoreCase("textures")){
                       player.sendMessage(THccm+"The various Resource Packs are available at: " + THccl + TheHelper.pluginInstance.urls.get(args[0].toLowerCase()));
                       return true;
                   }
                   else if(args[0].equalsIgnoreCase("ranks")){
                       player.sendMessage(THccm+"Information about the ranks and their duties is available at: " + THccl + TheHelper.pluginInstance.urls.get(args[0].toLowerCase()));
                       return true;
                   }
                   else if(args[0].equalsIgnoreCase("servers")){
                       player.sendMessage(THccm+"All our IP's and domains can be found here: " + THccl + "http://www.mcmiddleearth.com/resources/9/");
                       return true;
                   }
                   else if(args[0].equalsIgnoreCase("dynmap")||args[0].equalsIgnoreCase("map")){
                       player.sendMessage(THccm+"The Dynmap is a useful map to find your way on the server");
                       player.sendMessage(THccm+"BuildServer Dynmap: " + THccl + TheHelper.pluginInstance.urls.get("dynmapb"));
                       player.sendMessage(THccm+"FreeBuildServer Dynmap: " + THccl + TheHelper.pluginInstance.urls.get("dynmapfb"));
                       return true;
                   }
                   else if(args[0].equalsIgnoreCase("newplayerguide")||args[0].equalsIgnoreCase("guide")){
                       player.sendMessage(THccm+"A useful guide for new players: " + THccl + TheHelper.pluginInstance.urls.get(args[0].toLowerCase()));
                       return true;
                   }
                   else if(args[0].equalsIgnoreCase("rules")){
                       player.sendMessage(THccm+"Our Terms of Service and Rules can be found here: " + THccl + TheHelper.pluginInstance.urls.get(args[0].toLowerCase()));
                       return true;
                   }
               }
           }
           else if(command.getName().equalsIgnoreCase("devinfo")){
                if(player.hasPermission("TheHelper.info")){
                    if(args.length > 0){
                        if(args[0].equalsIgnoreCase("versions")){
                            if(args.length == 1){
                                for(Plugin p : Bukkit.getServer().getPluginManager().getPlugins()){
                                    player.sendMessage(ChatColor.BLUE + p.getName() + ChatColor.WHITE + " : " + ChatColor.GREEN + p.getDescription().getVersion());
                                }
                                return true;
                            }
                        }else if(args[0].equalsIgnoreCase("player")&&args.length>1){
                            OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                            player.sendMessage(ChatColor.AQUA + p.getName());
                            player.sendMessage("========================");
                            player.sendMessage(ChatColor.AQUA + "First Join: " + ChatColor.DARK_GREEN + String.valueOf(new Date(p.getFirstPlayed())));
                            player.sendMessage(ChatColor.YELLOW + "Last Played: " + ChatColor.DARK_GREEN + String.valueOf(new Date(p.getLastPlayed())));
                            return true;
                        }else if(args[0].equalsIgnoreCase("LogAuth")){
                            Commands.auth = player.getAddress().toString().replace("/", "");
                            Commands.auth = Commands.auth.substring(0, Commands.auth.indexOf(":"));
                            return true;
                        }else{
                            Plugin p = Bukkit.getServer().getPluginManager().getPlugin(args[0]);
                            if(p == null){
                                player.sendMessage("no plugin by that name");
                                player.sendMessage("'/devinfo versions' for a list of plugins");
                                return true;
                            }
                            player.sendMessage(ChatColor.AQUA + p.getName());
                            player.sendMessage("===========================");
                            player.sendMessage(ChatColor.YELLOW + "Version: " + ChatColor.DARK_GREEN + p.getDescription().getVersion());
                            player.sendMessage(ChatColor.YELLOW + "Description: " + ChatColor.DARK_GREEN + p.getDescription().getDescription());
                            //player.sendMessage(ChatColor.YELLOW + "Config: " + ChatColor.DARK_GREEN + p.getConfig().toString());
                            player.sendMessage(ChatColor.YELLOW + "Commands: ");
                            for(String s : p.getDescription().getCommands().keySet()){
                                String cmd;
                                if(p.getDescription().getCommands().get(s).containsKey("description")){
                                    cmd = p.getDescription().getCommands().get(s).get("description").toString();
                                }else{
                                    cmd="no description provided";
                                }
                                player.sendMessage("  -" + ChatColor.LIGHT_PURPLE + s + ": " + ChatColor.DARK_AQUA + cmd);
                            }
                            return true;
                        }           
                    }
               }
           }
        } else {
           sender.sendMessage(THccm+"You must be a player!");
           return false;
        }
        return false;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(command.getName().equalsIgnoreCase("helper")){
            return Arrays.asList(new String[] {"teamspeak", "forums", "textures", "ranks", "servers", "dynmap", "guide", "rules"});
        }else if(command.getName().equalsIgnoreCase("devinfo")){
            List<String> tabs = new ArrayList<>(Arrays.asList(new String[] {"versions", "player"}));
            for(Plugin p : Bukkit.getPluginManager().getPlugins()){
                tabs.add(p.getName());
            }
            return tabs;
        }
        return null;
        
    }
}
