package net.dreamerzero.titleannouncer.velocity.commands.actionbar;

import java.util.List;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.GeneralUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;
import net.dreamerzero.titleannouncer.velocity.utils.SoundUtils;

public class SelfActionbarCommand implements SimpleCommand{
    public SelfActionbarCommand() {}
    @Override
    public void execute(Invocation invocation) {
        CommandSource sender = invocation.source();
        String[] args = invocation.arguments();
        ConfigUtils config = new ConfigUtils();
        MiniMessageUtil mUtils = new MiniMessageUtil();

        if(!(sender instanceof Player player)){
            config.onlyPlayerExecute(sender);
            return;
        }
        if(args.length == 0) {
            config.noActionbarArgumentProvided(sender);
            return;
        }
        // Concatenate the arguments provided by the command sent.
        String actionbartext = new GeneralUtils().getCommandString(args);

        sender.sendActionBar(mUtils.parse(
            mUtils.replaceLegacy(actionbartext),
            PlaceholderUtil.replaceProxyPlaceholders(player)));
        new SoundUtils().playProxySound(player, ComponentType.ACTIONBAR);
        config.sendConfirmation(ComponentType.ACTIONBAR, sender);
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        return List.of("[message]");
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("titleannouncer.actionbar.self");
    }
}
