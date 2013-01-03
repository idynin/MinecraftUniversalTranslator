package com.idynin.MinecraftUniversalTranslator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MinecraftUniversalTranslatorCommandExecutor implements CommandExecutor {

    private MinecraftUniversalTranslator plugin;
    
    private final String[] commandList = {"tr", "tran", "translate"};
    
    public String[] getCommandList(){
    	return commandList;
    }

    public MinecraftUniversalTranslatorCommandExecutor(MinecraftUniversalTranslator plugin) {
    	
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	
        plugin.getLogger().info("onCommand Reached in MinecraftUniversalTranslator");

        if (command.getName().equalsIgnoreCase("command")) {
        	plugin.getLogger().info("command used");
            //do something
            return true;
        }
        return false;
    }
}
