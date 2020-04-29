package fr.limpsword.tempmute.commands;

import fr.limpsword.tempmute.sanction.MuteHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class UnmuteCommand implements CommandExecutor {

    private final MuteHandler muteHandler;

    public UnmuteCommand(MuteHandler muteHandler) {
        this.muteHandler = muteHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("tempmute.execute")) {
            sender.sendMessage("§cYou do not have enough permissions to execute this command.");
            return true;
        }

        if (args.length == 1) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

            if (muteHandler.unmute(target)) {
                sender.sendMessage("§eThe player §f" + target.getName() + " §ehas successfully been unmuted.");

                if (target.isOnline()) {
                    target.getPlayer().sendMessage("§eYou have been unmuted.");
                }
                return true;
            }
            sender.sendMessage("§eThe player §f" + target.getName() + " §eis not muted.");
            return true;
        }
        return sendHelp(sender);
    }

    public boolean sendHelp(CommandSender sender) {
        sender.sendMessage("§cUsage: /unmute <player>");
        return true;
    }
}