package net.dreamerzero.titleannouncer.paper.commands.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.util.TriState;

public class AnnouncerActionbarCommand implements CommandExecutor {
    public AnnouncerActionbarCommand() {}

    // The audience that will receive the actionbar will be all the players on the server.
    Audience audience = Bukkit.getServer();

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Permission Check TODO: Check if this really works
        if (sender.permissionValue("titleannouncer.actionbar.global") != TriState.TRUE) {
            ConfigUtils.sendNoMainPermission(sender);
            return true;
        }

        if(args.length == 0) {
            ConfigUtils.noActionbarArgumentProvided(sender);
            return false;
        }

        boolean placeholderAPISupport = PlaceholderUtil.placeholderAPIHook();

        // Concatenate the arguments provided by the command sent.
        String actionbartext = GeneralUtils.getCommandString(args);

        // Send to all
        if (sender instanceof Player player) {
            audience.sendActionBar(MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, actionbartext) : actionbartext),
                    PlaceholderUtil.replacePlaceholders(player)));
            ConfigUtils.playActionbarSound(audience);
            ConfigUtils.sendActionbarConfirmation(sender);
            return true;
        } else {
            audience.sendActionBar(MiniMessageUtil.parse(
                MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, actionbartext) : actionbartext),
                    PlaceholderUtil.replacePlaceholders()));
            ConfigUtils.playActionbarSound(audience);
            ConfigUtils.sendActionbarConfirmation(sender);
            return true;
        }
    }
}
