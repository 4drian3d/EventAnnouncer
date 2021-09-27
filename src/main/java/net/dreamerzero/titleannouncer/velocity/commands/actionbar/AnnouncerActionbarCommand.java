package net.dreamerzero.titleannouncer.velocity.commands.actionbar;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.common.utils.SoundUtil;

public class AnnouncerActionbarCommand implements SimpleCommand {
    private ProxyServer server;
    public AnnouncerActionbarCommand(ProxyServer server) {
        this.server = server;
    }
    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        // Concatenate the arguments provided by the command sent.
        String actionbartext = GeneralUtils.getCommandString(args);

        // Send to all
        server.sendActionBar(MiniMessageUtil.parse(
            MiniMessageUtil.replaceLegacy(
                actionbartext),
                sender instanceof Player player ?
                    PlaceholderUtil.replaceProxyPlaceholders(player) :
                    PlaceholderUtil.replaceProxyPlaceholders()));
        SoundUtil.playToAllProxyActionbarSound();
        ConfigUtils.sendActionbarConfirmation(sender);
        return;
    }
}
