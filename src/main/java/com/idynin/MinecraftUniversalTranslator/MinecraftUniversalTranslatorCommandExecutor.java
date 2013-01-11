package com.idynin.MinecraftUniversalTranslator;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.idynin.TranslateAPI.Language;

public class MinecraftUniversalTranslatorCommandExecutor implements
		CommandExecutor {

	private MinecraftUniversalTranslator plugin;

	private final String[] commandList = { "mut" };

	public MinecraftUniversalTranslatorCommandExecutor(
			MinecraftUniversalTranslator plugin) {

		this.plugin = plugin;
	}

	public String[] getCommandList() {
		return commandList;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (command.getName().equalsIgnoreCase("mut")) {
			String outputMessage = sender.getName() + " executed " + command
					+ " <" + Arrays.toString(args) + ">. ";

			// 2 arg commands
			if (args.length == 2) {

				if (args[0].equalsIgnoreCase("tl")) {
					// change target language

					Language lang = Language.getBestMatch(args[1]);
					plugin.getTranslator().setTargetLanguage(lang);

					outputMessage += "Set target language to: " + lang;

				}
			}

			// 1 arg commands
			else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("storecache")) {
					//plugin.getTranslator().storeCache();
					outputMessage += "not Stored cache";
				} else if (args[0].equalsIgnoreCase("clearcache")) {
					//plugin.getTranslator().clearCache();
					outputMessage += "not Cleared cache";
				}
			}
			plugin.getServer().broadcastMessage(outputMessage);
			plugin.getLogger().info(outputMessage);
			return true;
		}
		return false;
	}
}
