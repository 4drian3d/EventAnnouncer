package net.dreamerzero.titleannouncer.paper.commands.title;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.TitleUtil;
import net.dreamerzero.titleannouncer.paper.Announcer;
import net.dreamerzero.titleannouncer.paper.utils.PPlaceholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class AnnouncerTitleCommand implements CommandExecutor {
    private MiniMessage mm;
    public AnnouncerTitleCommand(MiniMessage mm) {
        this.mm = mm;
    }

    //The audience that will receive the title will be all the players on the server.
    Audience audience = Bukkit.getServer();

    // Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            ConfigUtils.sendNoArgumentMessage(sender);
            return true;
        }

        boolean placeholderAPISupport = Announcer.placeholderAPIHook();

        // Concatenate the arguments provided by the command sent.
        String titleandsubtitle = GeneralUtils.getCommandString(args);

        if(!TitleUtil.containsComma(args)){
            if(sender instanceof Player player){
                TitleUtil.sendOnlySubtitle(
                    mm.deserialize(MiniMessageUtil.replaceLegacy(
                        placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, titleandsubtitle) : titleandsubtitle), 
                        PPlaceholders.replacePlaceholders(player)),
                        audience, 1000, 3000, 1000);
                ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
                ConfigUtils.playPaperSound(ComponentType.TITLE, audience);
                return true;
            } else {
                TitleUtil.sendOnlySubtitle(
                    mm.deserialize(MiniMessageUtil.replaceLegacy(
                        placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, titleandsubtitle) : titleandsubtitle), 
                        PPlaceholders.replacePlaceholders()),
                        audience, 1000, 3000, 1000);
                ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
                ConfigUtils.playPaperSound(ComponentType.TITLE, audience);
                return true;
            }
        }

        String titleandsubtitlefinal[] = TitleUtil.getTitleAndSubtitle(titleandsubtitle, sender);

        if(titleandsubtitlefinal == null) return false;

        if (sender instanceof Player player) {
            // Send the title
            TitleUtil.sendTitle(
                mm.deserialize(MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[0]) : titleandsubtitlefinal[0]),
                    PPlaceholders.replacePlaceholders(player)),
                mm.deserialize(MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(player, titleandsubtitlefinal[1]) : titleandsubtitlefinal[1]),
                    PPlaceholders.replacePlaceholders(player)),
                audience,
                1000,
                3000,
                1000);
            ConfigUtils.playPaperSound(ComponentType.TITLE, audience);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
            return true;
        } else {
            // Send the title
            TitleUtil.sendTitle(
                mm.deserialize(MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, titleandsubtitlefinal[0]) : titleandsubtitlefinal[0]),
                    PPlaceholders.replacePlaceholders()),
                mm.deserialize(MiniMessageUtil.replaceLegacy(
                    placeholderAPISupport ? PlaceholderAPI.setPlaceholders(null, titleandsubtitlefinal[1]) : titleandsubtitlefinal[1]),
                    PPlaceholders.replacePlaceholders()),
                audience,
                1000,
                3000,
                1000);
            ConfigUtils.playPaperSound(ComponentType.TITLE, audience);
            ConfigUtils.sendConfirmation(ComponentType.TITLE, sender);
            return true;
        }
    }
}
