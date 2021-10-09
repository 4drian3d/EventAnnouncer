package net.dreamerzero.titleannouncer.velocity.commands.actionbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;
import net.dreamerzero.titleannouncer.velocity.utils.VPlaceholders;
import net.kyori.adventure.text.minimessage.MiniMessage;

public record ServerActionbarCommand(ProxyServer server, MiniMessage mm) implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        ConfigUtils config = new ConfigUtils();
        VPlaceholders vPlaceholders = new VPlaceholders(server);

        if(args.length == 0) {
            config.noActionbarArgumentProvided(sender);
            return;
        }else if (args.length < 2) {
            config.noServerArgumentProvided(sender);
            return;
        }

        Optional<RegisteredServer> optionalServerObjetive = server.getServer(args[0]);
        if(!optionalServerObjetive.isPresent()) {
            config.noServerFound(sender);
            return;
        }
        RegisteredServer serverObjetive = optionalServerObjetive.get();

        // Concatenate the arguments provided by the command sent.
        String actionbartext = new GeneralUtils().getCommandString(args, 1);

        serverObjetive.sendActionBar(
            mm.parse(
                MiniMessageUtil.replaceLegacy(
                    actionbartext),
                    vPlaceholders.replaceProxyPlaceholders()));
        new SoundUtils(server).playProxySound(serverObjetive, ComponentType.ACTIONBAR);
        config.sendConfirmation(ComponentType.ACTIONBAR, sender);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        if(invocation.arguments().length <= 1){
            ArrayList<String> servers = new ArrayList<>();
            server.getAllServers().forEach(server ->
                servers.add(server.getServerInfo().getName()));
            return servers;
        }
        return List.of("[message]");
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.actionbar.server");
    }
}
