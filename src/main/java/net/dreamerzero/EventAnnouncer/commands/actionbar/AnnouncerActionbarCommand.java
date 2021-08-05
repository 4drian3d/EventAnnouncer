package net.dreamerzero.EventAnnouncer.commands.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dreamerzero.EventAnnouncer.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class AnnouncerActionbarCommand implements CommandExecutor {
    @SuppressWarnings("unused")
    private Announcer plugin;
	public AnnouncerActionbarCommand(Announcer plugin) {
		this.plugin = plugin;
	}

    //Component that parses the title with the MiniMessage format.
    private static Component miniMessageParse(final String message) {
        return MiniMessage.get().parse(message);
    }

    //The audience that will receive the title will be all the players on the server.
    public Audience audience = Bukkit.getServer();

    //Command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        StringBuilder actionbartext = new StringBuilder();
        for (byte i = 0; i < args.length; i++){
            actionbartext = actionbartext.append(" ");
            actionbartext = actionbartext.append(args[i]); 
        }
        //Convert StringBuilder to String, Component is not compatible :nimodo:
        String actionbartoparse = actionbartext.toString();
        //Send to all
        audience.sendActionBar(miniMessageParse(actionbartoparse));
        return true;
    }

}
