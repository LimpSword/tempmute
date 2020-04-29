package fr.limpsword.tempmute.commands;

import fr.limpsword.tempmute.sanction.MuteHandler;
import fr.limpsword.tempmute.sanction.TimeParser;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class TempmuteCommand implements CommandExecutor {

    private final MuteHandler muteHandler;

    public TempmuteCommand(MuteHandler muteHandler) {
        this.muteHandler = muteHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("tempmute.execute")) {
            sender.sendMessage("§cYou do not have enough permissions to execute this command.");
            return true;
        }

        if (args.length == 2) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            String timeStr = args[1];

            long time;
            if (timeStr.equals("life")) {
                time = (long) 3.154e11; // 10 years
            } else {
                time = TimeParser.getTime(timeStr);
            }

            if (time < 1) {
                sender.sendMessage("§cMute duration must be at least equal to 1.");
                return true;
            }

            if (muteHandler.mute(target, time)) {
                sender.sendMessage("§eThe player §f" + target.getName() + " §ehas successfully been muted for §f"
                        + TimeParser.getDurationString(time) + "§e.");

                if (target.isOnline()) {
                    target.getPlayer().sendMessage("§eYou have been muted for §f" + TimeParser.getDurationString(time) + "§e.");
                }
                return true;
            }
            sender.sendMessage("§eThe player §f" + target.getName() + " §eis already muted, you must unmute him first.");
            return true;
        }
        return sendHelp(sender);
    }

    public boolean sendHelp(CommandSender sender) {
        sender.sendMessage("§cUsage: /tempmute <player> <time>");
        return true;
    }
}