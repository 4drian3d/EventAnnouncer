package net.dreamerzero.titleannouncer.utils;

import org.bukkit.configuration.file.FileConfiguration;

import net.dreamerzero.titleannouncer.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public class ConfigUtils {
    private static final Announcer plugin = Announcer.getInstance();
    private static FileConfiguration config = plugin.getConfig();

    private static Component getPrefix(){
        if (config.getBoolean("messages.prefix.enabled", true)) {
            return MiniMessageUtil.parse(config.getString(
                "messages.prefix.line",
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray> "));
        } else {
            return Component.empty();
        }
    }

    /*-----------------------------
    TITLE CONFIGURATION
    -----------------------------*/

    public static void sendTitleError(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.title.error",
                "<dark_red>An error occurred while sending the title. Be sure to use the ';' to separate the title and the subtitle.</dark_red>"))));
    }

    public static void sendTitleConfirmation(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.title.successfully",
                "<green>Title succesfully sended</green>"))));
    }

    public static void sendNoTitlePermission(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.title.no-permission",
                "<red>You do not have permission to execute this command</red>"))));
    }

    public static void sendNoArgumentMessage(Audience sender) {
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.title.without-argument",
                "<red>You need to enter the title and subtitle arguments.</red>"))));
    }

    public static void noTitlePlayerArgumentProvided(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.title.only-player",
                "<gray>You must enter the title and subtitle after the player's name to send the message correctly.</gray>"))));
    }

    public static void titlePlayerNotFoundMessage(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.title.player-not-found",
                "<red>Player not found</red>"))));
    }

    public static String getTitleSound(){
        return config.getString(
            "sounds.title.sound-id",
            "entity.experience_orb.pickup");
    }

    public static boolean isTitleSoundEnabled(){
        return config.getBoolean("sounds.title.enabled", true);
    }

    static float getTitleSoundVolume(){
        return config.getInt("sounds.title.volume", 10);
    }

    static float getTitleSoundPitch(){
        return config.getInt("sounds.title.pitch", 2);
    }

    public static void playTitleSound(Audience audience){
        if(isTitleSoundEnabled()){
            SoundUtil.playSound(
                getTitleSound(),
                audience,
                getTitleSoundVolume(),
                getTitleSoundPitch());
        }
    }

    /*-----------------------------
    ACTIONBAR CONFIGURATION
    -----------------------------*/
    public static void sendNoActionbarPermission(Audience sender){
        sender.sendMessage(
            getPrefix().append(MiniMessageUtil.parse(
                config.getString(
                    "messages.actionbar.no-permission",
                    "<red>You do not have permission to execute this command</red>"))));
    }

    public static void sendActionbarConfirmation(Audience sender){
        sender.sendMessage(
            getPrefix().append(MiniMessageUtil.parse(
                config.getString(
                    "messages.actionbar.successfully",
                    "<green>Actionbar succesfully sended</green>"))));
    }

    public static void noActionbarPlayerArgumentProvided(Audience sender){
        sender.sendMessage(
            getPrefix().append(MiniMessageUtil.parse(
                config.getString(
                    "messages.actionbar.only-player",
                    "<gray>You must enter the message to be sent after the player's name.</gray>"))));
    }

    public static void actionbarPlayerNotFoundMessage(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.actionbar.player-not-found",
                "<red>Player not found</red>"))));
    }

    public static String getActionbarSound(){
        return config.getString(
            "sounds.actionbar.sound-id",
            "entity.experience_orb.pickup");
    }

    public static boolean isActionbarSoundEnabled(){
        return config.getBoolean("sounds.actionbar.enabled", true);
    }

    static float getActionbarSoundVolume(){
        return config.getInt("sounds.actionbar.volume", 10);
    }

    static float getActionbarSoundPitch(){
        return config.getInt("sounds.actionbar.pitch", 2);
    }

    public static void playActionbarSound(Audience audience){
        if(isActionbarSoundEnabled()){
            SoundUtil.playSound(
                getActionbarSound(),
                audience,
                getActionbarSoundVolume(),
                getActionbarSoundPitch());
        }
    }

}
